package com.abc.activitytype.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.uengine.kernel.DefaultActivity;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.web.util.ApplicationContextRegistry;

import java.io.File;
import java.util.Date;
import java.util.Properties;

public abstract class ScriptBaseAbstractTask extends DefaultActivity {

    /**
     * 실행중인 워크플로우 객체
     */
    public ProcessInstance instance;

    /**
     * 타스크 변수 매니지 서비스
     */
    public TaskAttributes taskAttributes;

    /**
     * 프로퍼티 저장소
     */
    public Properties properties;

    /**
     * 원격지 스크립트 임시 저장 디렉토리
     */
    public String tempDir;

    /**
     * 원격지 연결을 위한 ssh 리모트매니저
     */
    public RemoteManager remoteManager;

    /**
     * 액티비티 히스토리 객체
     */
    public TaskHistory taskHistory;

    /**
     * 스크립트 실행 로그
     */
    public String stdout;

    /**
     * 스크립트 에러 로그
     */
    public String stderr;

    /**
     * 원격지에서 실행될 스크립트 내용
     */
    public String script;

    /**
     * 원격지에서 실행할 스크립트 실행 커맨드
     */
    public String sshCommand;

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(ScriptBaseAbstractTask.class);

    @Override
    protected void executeActivity(final ProcessInstance instance) throws Exception {

        this.instance = instance;

        ApplicationContext context = ApplicationContextRegistry.getApplicationContext();
        taskAttributes = context.getBean(TaskAttributes.class);
        properties = GlobalContext.getProperties();

        String temp = properties.getProperty("hadoop.temp.dir");
        String sshUser = properties.getProperty("hadoop.namenode.ssh.user");
        String sshPassword = properties.getProperty("hadoop.namenode.ssh.password");
        String sshPem = properties.getProperty("hadoop.namenode.ssh.pem");
        String sshHost = properties.getProperty("hadoop.namenode.ssh.host");
        String sshPort = properties.getProperty("hadoop.namenode.ssh.port");

        tempDir = temp + "/" + new Date().getTime();

        if (StringUtils.isEmpty(sshPem)) {
            remoteManager = new RemoteManager(sshHost, Integer.parseInt(sshPort), sshUser, sshPassword);
        } else {
            remoteManager = new RemoteManager(sshHost, Integer.parseInt(sshPort), sshUser, new File(sshPem));
        }
        this.doExecute();
    }

    public abstract void doExecute() throws Exception;
}