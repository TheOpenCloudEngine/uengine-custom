package com.abc.activitytype.interceptor;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteSsh {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(RemoteSsh.class);

    private String hostname;
    private String username;
    private String identity = null;
    private String password = null;
    private int port = 22;

    private boolean isDebugMode = false;

    private boolean pty = false;

    public void enableDebug() {
        isDebugMode = true;
    }

    public void disableDebug() {
        isDebugMode = false;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
        this.password = null;
    }

    public String getPassword() {
        return password;
    }

    public void setPty(boolean pty) {
        this.pty = pty;
    }

    public void setPassword(String password) {
        this.password = password;
        this.identity = null;
    }

    public RemoteSsh(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public RemoteSsh() {
    }

    public RemoteSsh(String username, String hostname) {
        this.username = username;
        this.hostname = hostname;
    }


    public Session getSession() throws JSchException {
        JSch jsch = new JSch();
        if (identity != null) {
            jsch.addIdentity(identity);
        }

        Session session = jsch.getSession(username, hostname, port);
        session.setConfig("StrictHostKeyChecking", "no");
        if (password != null) session.setPassword(password);
        return session;
    }

    public String exec(String command) {
        return exec(new String[]{command}).get(0);
    }

    public List<String> exec(List<String> commands) {
        return exec(commands.toArray(new String[]{}));
    }


    public List<String> exec(String[] commands) {
        List<String> ret = new ArrayList<String>();
        try {
            Session session = getSession();
            session.connect();
            for (String command : commands) {
                ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
                channelExec.setPty(pty);
                if (isDebugMode) System.out.println("command : " + command);
                channelExec.setCommand(command);
                InputStream inputStream = channelExec.getInputStream();
                InputStream err = channelExec.getErrStream();
                channelExec.connect(3000);

                if (isDebugMode) System.out.print("stdout : ");
                String output = "";
                byte[] buf = new byte[1024];
                int length;
                while ((length = inputStream.read(buf)) != -1) {
                    output += new String(buf, 0, length);
                    if (isDebugMode) System.out.print(new String(buf, 0, length));
                }
                if (isDebugMode) System.out.println("\nerr : " + IOUtils.toString(err));
                ret.add(StringUtils.chop(output));
                channelExec.disconnect();
            }
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


	/* ---- generate ssh keypair  --------- */

    public Map<String, String> keyGen(String algorithm, String passphrase, String comment) {
        String privateKeyString = "";
        String publicKeyString = "";

        int type = 0;
        if (algorithm.toLowerCase().equals("rsa")) type = KeyPair.RSA;
        else if (algorithm.toLowerCase().equals("dsa")) type = KeyPair.DSA;
        else {
            System.err.println("does not support " + algorithm + " algorithm");
            return null;
        }

        JSch jsch = new JSch();
        try {
            KeyPair kpair = KeyPair.genKeyPair(jsch, type);
            kpair.setPassphrase(passphrase);
            ByteArrayOutputStream priout = new ByteArrayOutputStream();
            ByteArrayOutputStream pubout = new ByteArrayOutputStream();
            kpair.writePrivateKey(priout);
            kpair.writePublicKey(pubout, comment);

            privateKeyString = priout.toString();
            publicKeyString = pubout.toString();
            if (isDebugMode) {
                System.out.println("Private Key : \n" + privateKeyString);
                System.out.println("Public Key : \n" + publicKeyString);
                System.out.println("Finger print: " + kpair.getFingerPrint());
            }
            kpair.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e);
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("privateKey", privateKeyString);
        map.put("publicKey", publicKeyString);
        return map;
    }

	/* Scp ----------------------- */

    public String getFileFromRemoteUsingScp(String remoteFile) throws IOException {
        String remoteFileContent = "";
        try {
            File tempFile = File.createTempFile("temp", ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(tempFile));
            getFileFromRemoteUsingScp(remoteFile, tempFile);
            String line;
            while ((line = br.readLine()) != null) remoteFileContent += line + "\n";
            br.close();
            tempFile.delete();
        } catch (IOException e) {
            logger.error(" temp 파일에 내용을 기록할 수 없습니다 ");
            throw new IOException(e);
        }
        return remoteFileContent;
    }

    public void getFileFromRemoteUsingScp(String remoteFile, File localFile) throws IOException {
        //usage: java ScpFrom user@remotehost:file1 file2
        FileOutputStream fileOutputStream = null;
        try {

            Session session = getSession();
            // username and password will be given via UserInfo interface.
            session.connect();

            // exec 'scp -f rfile' remotely
            String command = "scp -f " + remoteFile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] buf = new byte[1024];

            // invoke '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ') break;
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file = null;
                for (int i = 0; ; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }

                //System.out.println("filesize="+filesize+", file="+file);

                // invoke '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();

                // read a content of lfile

                fileOutputStream = new FileOutputStream(localFile);
                int foo;
                while (true) {
                    if (buf.length < filesize) foo = buf.length;
                    else foo = (int) filesize;
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        break;
                    }
                    fileOutputStream.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L) break;
                }
                fileOutputStream.close();
                fileOutputStream = null;

                if (checkAck(in) != 0) {
                    return;
                }

                // invoke '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }

            session.disconnect();
        } catch (Exception e) {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (Exception ee) {
                logger.error(" FileOutputStream 을 종료할 수 없습니다. ");
                throw new IOException(ee);
            }
            logger.error(" 원격에서 파일을 가져올 수 없습니다. ");
            throw new RuntimeException(e);
        }
    }

    public void scpTo(String content, String rfile) {
        try {
            File tfile = File.createTempFile("prefix", ".tmp");
            FileWriter fw = new FileWriter(tfile);
            fw.write(content);
            fw.close();
            scpTo(tfile, rfile);
            tfile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void scpTo(File lfile, String rfile) {
        // ScpTo file1 user@remotehost:file2

        FileInputStream fis = null;
        try {
            Session session = getSession();
            session.connect();

            boolean ptimestamp = true;
            // exec 'scp -t rfile' remotely
            String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
            System.out.println(command);
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();
            if (checkAck(in) != 0) {
                //System.exit(0);
                return;
            }

            String filename = lfile.getName();
            if (ptimestamp) {
                command = "T" + (lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    //System.exit(0);
                    return;
                }
            }

            // invoke "C0644 filesize filename", where filename should not include '/'
            long filesize = lfile.length();
            command = "C0644 " + filesize + " ";
            if (filename.lastIndexOf('/') > 0) {
                command += filename.substring(filename.lastIndexOf('/') + 1);
            } else {
                command += filename;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                //System.exit(0);
                return;
            }

            // invoke a content of lfile
            fis = new FileInputStream(lfile);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // invoke '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                return;
            }
            out.close();

            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            System.out.println(e);
            try {
                if (fis != null) fis.close();
            } catch (Exception ee) {
                System.out.println(ee);
            }
        }
    }

    private int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }
}