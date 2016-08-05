package com.abc.activitytype;

import com.abc.activitytype.interceptor.InterceptorScriptBaseTask;
import com.abc.monitor.Console;
import com.abc.monitor.ConsolePanel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.metaworks.ToAppend;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Group;
import org.metaworks.dwr.MetaworksRemoteService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;
import org.uengine.kernel.*;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class HiveActivity extends InterceptorScriptBaseTask {


    public HiveActivity() {
        setName("Hive");
        setQuery("");
    }

    @Override
    public boolean isQueuingEnabled() {
        return true;
    }

    String query;

    @Group(name = "Query")
    @Face(
            ejsPath = "dwr/metaworks/genericfaces/richText.ejs",
            options = {"rows", "cols"},
            values = {"7", "130"}
    )
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    String props;

    @Group(name = "Properties")
    @Face(
            ejsPath = "dwr/metaworks/genericfaces/richText.ejs",
            options = {"rows", "cols"},
            values = {"7", "130"}
    )
    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }


    @Override
    public void runTask() throws Exception {
        //props 를 쿼리문에 치환한다.
        Map propsMap = new HashMap();
        if (!StringUtils.isEmpty(props)) {
            String[] split = props.split("\n");
            for (String propertySet : split) {
                if (propertySet.indexOf("=") != -1) {
                    String key = propertySet.substring(0, propertySet.indexOf("="));
                    String value = propertySet.substring(propertySet.indexOf("=") + 1, propertySet.length());
                    propsMap.put(key, value);
                }
            }
        }
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/");
        String templateName = UUID.randomUUID().toString() + ".txt";
        String templatePath = resource.getFile().getAbsolutePath() + "/" + templateName;

        byte data[] = getQuery().getBytes();
        File file = new File(templatePath);
        file.createNewFile();
        Path path = Paths.get(file.getPath());
        Files.write(path, data);
        setQuery(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", propsMap));


        Properties properties = GlobalContext.getProperties();

        //하이브 홈, 하이브 유저
        String hiveHome = properties.getProperty("hadoop.hive.home");
        String hiveuser = properties.getProperty("hadoop.hive.user");

        //하이브 쿼리문이 저장될 패스
        String sqlFilePath = tempDir + "/hive.sql";

        /**
         * 스크립트 파일은 hive 유저 권한으로 실행될 커맨드를 저장한다.
         * 샘플) sudo su - hive -c "/usr/hdp/2.4.2.0-258/hive/bin/hive -f /tmp/sk/20231230019223/hive.sql"
         */
        script = "sudo su - " + hiveuser + " -c \"" + hiveHome + "/bin/hive -f " + sqlFilePath + "\"";

        //스크립트 파일이 저장될 패스
        String scriptFilePath = tempDir + "/script.sh";

        //원격지에 스크립트 저장 디렉토리를 생성한다.
        remoteManager.exec("mkdir -p " + tempDir);

        //쿼리문을 원격지에 저장한다.
        remoteManager.copyFileContent(getQuery(), sqlFilePath);

        //실행 스크립트를 원격지에 저장한다.
        remoteManager.copyFileContent(script, scriptFilePath);

        /**
         * ssh 커맨드는 원격지에 저장된 스크립트 파일을 sudo 권한이 있는 계정으로 실행시키기 위한 명령어이다.
         * 샘플) sudo /bin/sh /tmp/sk/20231230019223/script.sh
         */
        sshCommand = "sudo /bin/sh " + scriptFilePath;

        //Jsch 세션의 인풋스트림을 제어해야 함으로 세션을 직접 컨트롤한다.
        Session session = remoteManager.getSession();
        session.connect();
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setPty(true);
        channelExec.setCommand(sshCommand);

        //stdout 스트림
        final InputStream inputStream = channelExec.getInputStream();
        //stderr 스트림
        final InputStream err = channelExec.getErrStream();

        //stdout 을 저장한다.
        channelExec.connect(3000);
        byte[] buf = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                stdout += str;

                instance.setProperty(getTracingTag(), "log", stdout);
                MetaworksRemoteService.pushClientObjects(new Object[]{new ToAppend(new ConsolePanel(), new Console(str))});
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //stderr 을 저장한다.
        stderr = IOUtils.toString(err);

        //커넥션 종료
        channelExec.disconnect();
        session.disconnect();

        //원격지의 스크립트 폴더 삭제
        remoteManager.deleteFile(tempDir);
    }
}
