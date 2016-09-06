package com.abc.activitytype;

import com.abc.activitytype.interceptor.InterceptorScriptBaseTask;
import com.abc.face.ParameterValueListFace;
import com.abc.face.ParameterVariable;
import com.abc.monitor.Console;
import com.abc.monitor.ConsolePanel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import org.metaworks.ToAppend;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Order;
import org.metaworks.dwr.MetaworksRemoteService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjy on 2016. 7. 18..
 */
public class MapreduceActivity extends InterceptorScriptBaseTask {


    public MapreduceActivity() {
        setName("Mapreduce");
    }

    @Override
    public boolean isQueuingEnabled() {
        return true;
    }

    String doAs;
    public String getDoAs() {
        return doAs;
    }

    public void setDoAs(String doAs) {
        this.doAs = doAs;
    }

    String jar;

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    List<ParameterVariable> parameterValueList = new ArrayList();

    @Face(
            faceClass = ParameterValueListFace.class,
            displayName = "Args"
    )
    @Order(2)
    public List<ParameterVariable> getParameterValueList() {
        return parameterValueList;
    }

    public void setParameterValueList(List<ParameterVariable> parameterValueList) {
        this.parameterValueList = parameterValueList;
    }

    @Override
    public void runTask() throws Exception {

        /**
         * 스크립트 파일은 hive 유저 권한으로 실행될 커맨드를 저장한다.
         * 샘플) sudo su - hive -c "/usr/hdp/2.4.2.0-258/hive/bin/hive -f /tmp/sk/20231230019223/hive.sql"
         */

        List<String> args = new ArrayList<>();
        for (ParameterVariable variable : parameterValueList) {
            args.add(variable.getParameter());
        }
        script = scriptBuilder.getMapreduce(doAs, jar, args);

        //스크립트 파일이 저장될 패스
        String scriptFilePath = tempDir + "/script.sh";

        //원격지에 스크립트 저장 디렉토리를 생성한다.
        remoteManager.exec("mkdir -p " + tempDir);

        //실행 스크립트를 원격지에 저장한다.
        remoteManager.copyFileContent(script, scriptFilePath);

        /**
         * ssh 커맨드는 원격지에 저장된 스크립트 파일을 sudo 권한이 있는 계정으로 실행시키기 위한 명령어이다.
         * 샘플) sudo /bin/sh /tmp/sk/20231230019223/script.sh
         */
        sshCommand = "cd " + tempDir + " && sudo /bin/sh " + scriptFilePath;

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

        //프로세스 아이디
        String processId = remoteManager.getFileContent(tempDir + "/.PID")[0];

        //종료 스크립트 (line split)
        String[] killscript = remoteManager.getFileContent(tempDir + "/kill.sh");

        //종료 코드(0 이면 정상종료 , 1 이면 비정상 종료)
        String exitcode = remoteManager.getFileContent(tempDir + "/.EXITCODE")[0];

        //커넥션 종료
        channelExec.disconnect();
        session.disconnect();

        //원격지의 스크립트 폴더 삭제
        remoteManager.deleteFile(tempDir);
    }
}
