package com.abc.activitytype.interceptor;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class RemoteManager {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(RemoteManager.class);

    private String sshhost;
    private int port;
    private String user;
    private String password;
    private String mappingIpaddress;
    private Set<String> domainSet;
    private Set<String> excludeSet;
    private RemoteSsh sshConnection;
    private String identity;

    public RemoteManager(String sshhost, int port, String user, String password) {
        this.sshhost = sshhost;
        this.port = port;
        this.user = user;
        this.password = password;
        buildSshConnection();
    }

    public RemoteManager(String sshhost, int port, String user, File pemfile) {
        this.sshhost = sshhost;
        this.port = port;
        this.user = user;
        this.identity = pemfile.getAbsolutePath();
        buildSshConnection();
    }

    public RemoteManager(String sshhost, int port, String user, String password, File pemfile) {
        this.sshhost = sshhost;
        this.port = port;
        this.user = user;
        if (pemfile.exists()) {
            {
                if (pemfile.isFile()) {
                    this.identity = pemfile.getAbsolutePath();
                } else {
                    this.password = password;
                }
            }
        } else {
            this.password = password;
        }
        buildSshConnection();
    }

    private RemoteSsh buildSshConnection() {
        if (user == null) user = "root";
        if (port > 0) port = port | 22;

        if (password == null && identity != null) {
            System.out.println("identity");
            System.out.println(user);
            System.out.println(sshhost);
            System.out.println(identity);
            System.out.println(port);
            sshConnection = new RemoteSsh(user, sshhost);
            sshConnection.setIdentity(identity);
            sshConnection.setPort(port);
        } else if (identity == null && password != null) {
            sshConnection = new RemoteSsh(user, sshhost);
            sshConnection.setPassword(password);
            sshConnection.setPort(port);
        }
        return sshConnection;
    }

    public Session getSession() throws JSchException {
        return sshConnection.getSession();
    }

    public String exec(String cmd) {
        sshConnection.setPty(true);
        String result = sshConnection.exec(cmd);
        sshConnection.setPty(false);
        return result;
    }

    //호스트이름에서 아이피를 추출해낸다.
    public String getIPfromHostname(String hostname) throws UnknownHostException {
        String byname = InetAddress.getByName(hostname).toString();
        String[] split = byname.split("/");
        return split[split.length - 1];
    }

    // etc/hosts 의 파일에 아이피 매핑을 추가한다.
    // exludeHost 가 포함된 라인은 삭제하고 새로 추가한다.
    public String addEtcHostsMapping(String mappingIpaddress, Set<String> domainSet, Set<String> excludeSet, String hostfilePath) {
        this.mappingIpaddress = mappingIpaddress;
        this.domainSet = domainSet;
        this.excludeSet = excludeSet;

        if (mappingIpaddress == null || domainSet.size() < 1) {
            logger.warn(" mappingIpaddress , domainSet 파라미터를 재 확인바랍니다.");
            throw new IllegalArgumentException("Invalid AddEtcHostsMapping Parameters.");
        }

        String[] fileContent = getFileContent(hostfilePath);

        logger.info("============================");
        logger.info("======/etc/hosts/===========");
        logger.info("============================");

        printArray(fileContent);

        Set<String> newfileContent = new HashSet<>();

        for (String line : fileContent) {
            Iterator iterator = excludeSet.iterator();
            boolean isLineExist = false;
            while (iterator.hasNext()) {
                String excludeString = (String) iterator.next();
                if (line.indexOf(excludeString) != -1) {
                    isLineExist = true;
                }
            }
            if (!isLineExist) newfileContent.add(line);
        }

        String appendableLine = mappingIpaddress;
        Iterator domainSetIter = domainSet.iterator();
        while (domainSetIter.hasNext()) {
            String host = (String) domainSetIter.next();
            appendableLine += "\t" + host;
        }

        String newString = "";
        newfileContent.add(appendableLine);
        logger.info("============================");
        logger.info("======new /etc/hosts/===========");
        logger.info("============================");
        printArray(newfileContent);

        Iterator newfileContentIter = newfileContent.iterator();
        while (newfileContentIter.hasNext()) {
            newString += newfileContentIter.next() + "\n";
        }
        copyFileContent(newString, hostfilePath);
        return newString;
    }

    public void copyPrivateKeyContentsToVM(String privateKeyContents) throws IOException {
        copyFileContent(privateKeyContents, "/etc/chef/client.pem");
    }

    public void deleteFile(String filepath) {
        sshConnection.setPty(true);
        String result = sshConnection.exec("sudo rm -rf " + filepath);
        sshConnection.setPty(false);
        if (isFileExist(filepath))
            throw new RuntimeException("파일 " + filepath + "가 삭제가 되지 않았습니다.");
    }

    public void copyFileContent(String content, String filepath) {

        if (user.equals("root"))
            sshConnection.scpTo(content, filepath);
        else {
            //root 가 아닐경우 임시 파일을 생성 후 sudo 로 카피로 우회한다.

            String tempPath = "/home/" + user + "/" + UUID.randomUUID().toString();
            sshConnection.scpTo(content, tempPath);
            sshConnection.setPty(true);
            String exec = sshConnection.exec("sudo mv " + tempPath + " " + filepath);
            System.out.println("exec : " + exec);
            sshConnection.setPty(false);
        }
    }

    public boolean isFileExist(String filepath) {
        String result = sshConnection.exec("[ -f " + filepath + " ] && echo \"Found\" || echo \"Not found\"");
        return result.equals("Found");
    }

    public String[] getFileContent(String filepath) {
        if (!isFileExist(filepath))
            throw new RuntimeException("파일 " + filepath + " 가 VM 에 존재하지 않습니다.");

        String result = sshConnection.exec("cat " + filepath);
        return result.split("\n");
    }

    public void printArray(String[] list) {
        for (String entry : list) {
            logger.info("line: {}", entry);
        }
    }

    public void printArray(Set<String> list) {
        for (Object entry : list) {
            logger.info("line: {}", entry);
        }
    }
}
