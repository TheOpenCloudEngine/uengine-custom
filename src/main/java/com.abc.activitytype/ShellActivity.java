package com.abc.activitytype;

import com.abc.monitor.Console;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.metaworks.ToAppend;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Group;
import org.metaworks.dwr.MetaworksRemoteService;
import org.uengine.codi.mw3.widget.SourceCodeFace;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.ProcessInstance;

import java.io.InputStream;

/**
 * Created by jjy on 2016. 7. 25..
 */
public class ShellActivity extends DefaultActivity{

    public ShellActivity() {
        super("Shell");
        setCommand("");
        setQueuingEnabled(true);
    }

    String command;
    @Group(name="Command")
    @Face(
            ejsPath = "dwr/metaworks/genericfaces/richText.ejs",
            options = {"rows", "cols"},
            values = {"7", "130"}
    )
//    @Face(faceClass= SourceCodeFace.class)
        public String getCommand() {
            return command;
        }
        public void setCommand(String command) {
            this.command = command;
        }

    String userId;
    @Group(name="Credentials")
        public String getUserId() {
            return userId;
        }
        public void setUserId(String userId) {
            this.userId = userId;
        }

    String password;
    @Group(name="Credentials")
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }

    String host;
    @Group(name="Host")
        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

    String port = "22";
    @Group(name="Host")
        public String getPort() {
            return port;
        }
        public void setPort(String port) {
            this.port = port;
        }

    String identityPemFilePath;
    @Group(name="PEM_File")
        public String getIdentityPemFilePath() {
            return identityPemFilePath;
        }
        public void setIdentityPemFilePath(String identityPemFilePath) {
            this.identityPemFilePath = identityPemFilePath;
        }

    boolean strictHostKeyChecking;
    @Group(name="Advanced")
        public boolean isStrictHostKeyChecking() {
            return strictHostKeyChecking;
        }
        public void setStrictHostKeyChecking(boolean strictHostKeyChecking) {
            this.strictHostKeyChecking = strictHostKeyChecking;
        }




    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {


        JSch jsch = new JSch();

        if(getIdentityPemFilePath()!=null && getIdentityPemFilePath().trim().length() > 0) {
            jsch.addIdentity(getIdentityPemFilePath());
            jsch.setConfig("StrictHostKeyChecking", "no");
        }


        String realUserId = evaluateContent(instance, getUserId()).toString();
        final String realPassword = evaluateContent(instance, getPassword()).toString();
        String realHost = evaluateContent(instance, getHost()).toString();
        String realPort = evaluateContent(instance, getPort()).toString();

        Session session = jsch.getSession(realUserId, realHost, new Integer(realPort));

        session.setUserInfo(new UserInfo() {
            @Override
            public String getPassphrase() {
                return null;
            }

            @Override
            public String getPassword() {
                return realPassword;
            }

            @Override
            public boolean promptPassword(String s) {
                return true;
            }

            @Override
            public boolean promptPassphrase(String s) {
                return false;
            }

            @Override
            public boolean promptYesNo(String s) {
                return true;
            }

            @Override
            public void showMessage(String s) {
                MetaworksRemoteService.pushClientObjects(new Object[]{new ToAppend(new Console(), new Console(s))});

            }
        });

        session.connect();
        ChannelExec chennelExec = (ChannelExec) session.openChannel("exec");

        String[] commandLines = evaluateContent(instance, getCommand()).toString().split("\n");

        for(String commandLine : commandLines){
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setPty(false);
      //      if (isDebugMode) System.out.println("command : " + command);
            channelExec.setCommand(commandLine);

            InputStream inputStream = channelExec.getInputStream();
            //InputStream ext = channelExec.getExtInputStream();
            InputStream err = channelExec.getErrStream();
            channelExec.connect(3000);

            StringBuffer output = new StringBuffer();
            byte[] buf = new byte[1024];
            int length;
            while ((length = inputStream.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                output.append(str);

                String existingLog = (String) instance.getProperty(getTracingTag(), "log");
                existingLog += output.toString();

                instance.setProperty(getTracingTag(), "log", existingLog);

                MetaworksRemoteService.pushClientObjects(new Object[]{new ToAppend(new Console(), new Console(str))});


                System.out.println(str);

            }

            while ((length = err.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                output.append(str);

                String existingLog = (String) instance.getProperty(getTracingTag(), "log");
                existingLog += output.toString();

                instance.setProperty(getTracingTag(), "log", existingLog);

                MetaworksRemoteService.pushClientObjects(new Object[]{new ToAppend(new Console(), new Console(str))});


                System.out.println(str);

            }

            channelExec.disconnect();
        }

        session.disconnect();

        super.executeActivity(instance);


    }



}
