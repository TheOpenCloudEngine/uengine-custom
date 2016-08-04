package com.abc.activitytype;

//import com.abc.activitytype.interceptor.GlobalAttributes;

import com.abc.activitytype.interceptor.InterceptorScriptBaseTask;
import com.abc.activitytype.interceptor.RemoteManager;
import com.abc.activitytype.interceptor.TaskAttributes;
import com.abc.monitor.Console;
import com.abc.monitor.ConsolePanel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.metaworks.ToAppend;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Group;
import org.metaworks.annotation.Order;
import org.metaworks.annotation.Range;
import org.metaworks.dwr.MetaworksRemoteService;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.uengine.codi.mw3.admin.WebEditorFace;
import org.uengine.kernel.*;
import org.uengine.kernel.bpmn.face.ProcessVariableSelectorFace;
import org.uengine.web.util.ApplicationContextRegistry;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
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

    @Override
    public void runTask(ProcessInstance instance) throws Exception {
        Properties properties = GlobalContext.getProperties();
        String hiveHome = properties.getProperty("hadoop.hive.home");
        String hiveuser = properties.getProperty("hadoop.hive.user");

        String scriptTemp = tempDir + "/" + new Date().getTime();
        String sqlFilePath = scriptTemp + "/hive.sql";

        String script = "sudo su - " + hiveuser + " -c \"" + hiveHome + "/bin/hive -f " + sqlFilePath + "\"";
        String scriptFilePath = scriptTemp + "/hive.sh";

        remoteManager.exec("mkdir -p " + scriptTemp);
        remoteManager.copyFileContent(getQuery(), sqlFilePath);
        remoteManager.copyFileContent(script, scriptFilePath);


        String command = "sudo /bin/sh " + scriptFilePath;
        Session session = remoteManager.getSession();
        session.connect();
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setPty(true);
        channelExec.setCommand(command);

        final InputStream inputStream = channelExec.getInputStream();
        final InputStream err = channelExec.getErrStream();
        channelExec.connect(3000);

        StringBuffer output = new StringBuffer();
        byte[] buf = new byte[1024];
        int length;

        try {
            while ((length = inputStream.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                output.append(str);

                String existingLog = (String) instance.getProperty(getTracingTag(), "log");
                existingLog += output.toString();

                instance.setProperty(getTracingTag(), "log", existingLog);

                MetaworksRemoteService.pushClientObjects(new Object[]{new ToAppend(new ConsolePanel(), new Console(str))});
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        channelExec.disconnect();
        session.disconnect();

        //원격지의 스크립트 폴더 삭제
        remoteManager.deleteFile(scriptTemp);
    }
}
