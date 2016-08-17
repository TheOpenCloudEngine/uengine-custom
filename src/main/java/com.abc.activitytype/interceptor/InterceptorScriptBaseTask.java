package com.abc.activitytype.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.uengine.kernel.ProcessInstance;
import org.uengine.web.util.ExceptionUtils;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class InterceptorScriptBaseTask extends ScriptBaseAbstractTask {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(InterceptorScriptBaseTask.class);

    @Override
    public void doExecute() throws Exception {
        preRun();

        try {
            runTask();
            updateTaskHistoryData();
            if (StringUtils.isEmpty(taskHistory.getStderr())) {
                updateTaskHistoryAsFinished();
                fireComplete(instance);
            } else {
                updateTaskHistoryAsFailed();
                fireFault(instance, new Exception(taskHistory.getStderr()));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            stderr = ExceptionUtils.getFullStackTrace(ex);
            try {
                updateTaskHistoryData();
                updateTaskHistoryAsFailed();
                fireFault(instance, new Exception(taskHistory.getStderr()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 타스크 히스토리의 Long 형식의 Date 를 읽기 가능한 형식으로 변경.
     */
    private void convertHumanReadable(TaskHistory taskHistory) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Long startDate = taskHistory.getStartDate();
        Long endDate = taskHistory.getEndDate();
        Long duration = taskHistory.getDuration();
        if (startDate != null) {
            taskHistory.setHumanStartDate(sdf.format(new Date(startDate)));
        } else {
            taskHistory.setHumanStartDate("");
        }
        if (endDate != null) {
            taskHistory.setHumanEndDate(sdf.format(new Date(endDate)));
        } else {
            taskHistory.setHumanEndDate("");
        }
        if (duration != null) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            long mis = taskHistory.getDuration() - (minutes * 60 * 1000);
            double seconds = mis / 1000.0;
            taskHistory.setHumanDuration(minutes + " min, " + seconds + " sec");
        } else {
            taskHistory.setHumanDuration("");
        }
    }

    public abstract void runTask() throws Exception;

    /**
     * 타스크 실행전 해야할 일
     */
    protected void preRun() throws Exception {

        //타스크 히스토리를 등록한다.
        this.initTaskHistory();

        //타스크의 시작 시그널을 남긴다.
        taskAttributes.setTaskStatus(instance, getTracingTag(), "RUNNING");
    }

    /**
     * 타스크 히스토리를 신규 등록한다.
     */
    private void initTaskHistory() throws Exception {
        taskHistory = new TaskHistory();
        taskHistory.setInstanceId(instance.getInstanceId());
        taskHistory.setProcessId(instance.getProcessDefinition().getId());
        taskHistory.setStartDate(new Date().getTime());
        taskHistory.setTaskId(getTracingTag());
        taskHistory.setTaskName(getName());
        this.convertHumanReadable(taskHistory);

        //타스크의 시작 시그널을 남긴다.
        taskAttributes.setTaskStatus(instance, getTracingTag(), "RUNNING");

        //instance 에 타스크 히스토리를 등록한다.
        taskAttributes.setTaskHistory(instance, getTracingTag(), taskHistory);
    }

    /**
     * 타스크 히스토리를 성공처리한다.
     */
    private void updateTaskHistoryAsFinished() throws Exception {
        long time = new Date().getTime();
        taskHistory.setEndDate(time);
        taskHistory.setDuration(time - taskHistory.getStartDate());
        taskHistory.setStatus("FINISHED");
        this.convertHumanReadable(taskHistory);

        //instance 에 타스크 히스토리를 등록한다.
        taskAttributes.setTaskHistory(instance, getTracingTag(), taskHistory);

        //타스크의 성공 시그널을 남긴다.
        taskAttributes.setTaskStatus(instance, getTracingTag(), "FINISHED");
    }

    /**
     * 타스크 히스토리를 실패처리한다.
     */
    public void updateTaskHistoryAsFailed() throws Exception {
        long time = new Date().getTime();
        taskHistory.setEndDate(time);
        taskHistory.setDuration(time - taskHistory.getStartDate());
        taskHistory.setStatus("FAILED");
        this.convertHumanReadable(taskHistory);

        //instance 에 타스크 히스토리를 등록한다.
        taskAttributes.setTaskHistory(instance, getTracingTag(), taskHistory);

        //타스크의 실패 시그널을 남긴다.
        taskAttributes.setTaskStatus(instance, getTracingTag(), "FAILED");
    }

    /**
     * 타스크에 스크립트 실행 결과를 등록한다.
     */
    private void updateTaskHistoryData() {
        taskHistory.setStdout(stdout);
        taskHistory.setStderr(stderr);
        taskHistory.setScript(script);
        taskHistory.setSshCommand(sshCommand);
    }
}
