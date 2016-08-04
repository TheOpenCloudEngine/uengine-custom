package com.abc.activitytype.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.uengine.kernel.FaultContext;
import org.uengine.kernel.ProcessInstance;
import org.uengine.web.util.ExceptionUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InterceptorScriptBaseTask extends ScriptBaseAbstractTask {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(InterceptorScriptBaseTask.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Logger exceptionLogger = LoggerFactory.getLogger("sk.exception");


    @Override
    public void doExecute(final ProcessInstance instance) throws Exception {
        this.instance = instance;
        preRun();

        new Thread() {
            @Override
            public void run() {
                try {
                    runTask(instance);
                    updateTaskHistoryData();
                    if (StringUtils.isEmpty(taskHistory.getStderr())) {
                        updateTaskHistoryAsFinished();
                        fireComplete(instance);
                    } else {
                        updateTaskHistoryAsFailed();
                        fireFault(instance, new Exception(taskHistory.getStderr()));
                    }

                } catch (Exception ex) {
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
        }.start();
    }

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

    public void runTask(ProcessInstance instance) throws Exception {
    }

    protected void preRun() throws Exception {

        /**
         * 타스크 히스토리를 등록한다.
         */
        this.initTaskHistory();

        /**
         * 타스크의 시작 시그널을 남긴다.
         */
        taskAttributes.setTaskStatus(instance, getTracingTag(), "RUNNING");
    }

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

    public void updateTaskHistoryAsFailed() throws Exception {
        long time = new Date().getTime();
        taskHistory.setEndDate(time);
        taskHistory.setDuration(time - taskHistory.getStartDate());
        taskHistory.setStatus("FAILED");
        this.convertHumanReadable(taskHistory);

        //instance 에 타스크 히스토리를 등록한다.
        taskAttributes.setTaskHistory(instance, getTracingTag(), taskHistory);

        //타스크의 성공 시그널을 남긴다.
        taskAttributes.setTaskStatus(instance, getTracingTag(), "FAILED");
    }

    private void updateTaskHistoryData() {
        taskHistory.setStdout(stdout);
        taskHistory.setStderr(stderr);
    }
}
