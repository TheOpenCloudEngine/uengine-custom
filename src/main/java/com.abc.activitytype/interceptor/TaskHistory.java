package com.abc.activitytype.interceptor;

import java.io.Serializable;

public class TaskHistory implements Serializable{

    private String processId;
    private String instanceId;
    private String taskId;
    private String taskName;
    private Long startDate;
    private String humanStartDate;
    private Long endDate;
    private String humanEndDate;
    private Long duration;
    private String humanDuration;
    private String status;
    private String stdout;
    private String stderr;
    private String script;
    private String sshCommand;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getHumanStartDate() {
        return humanStartDate;
    }

    public void setHumanStartDate(String humanStartDate) {
        this.humanStartDate = humanStartDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getHumanEndDate() {
        return humanEndDate;
    }

    public void setHumanEndDate(String humanEndDate) {
        this.humanEndDate = humanEndDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getHumanDuration() {
        return humanDuration;
    }

    public void setHumanDuration(String humanDuration) {
        this.humanDuration = humanDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStdout() {
        return stdout;
    }

    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getSshCommand() {
        return sshCommand;
    }

    public void setSshCommand(String sshCommand) {
        this.sshCommand = sshCommand;
    }
}
