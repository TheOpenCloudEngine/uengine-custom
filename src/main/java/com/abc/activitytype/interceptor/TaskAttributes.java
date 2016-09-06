package com.abc.activitytype.interceptor;

import org.uengine.kernel.ProcessInstance;

import java.util.Map;

public interface TaskAttributes {

    public static final String NAMESPACE = TaskAttributes.class.getName();

    void registJobResultMap(ProcessInstance instance, String tag) throws Exception;

    Map getJobMap(ProcessInstance instance, String tag) throws Exception;

    void setJobMap(ProcessInstance instance, String tag, Map jobMap) throws Exception;

    String getTaskStatus(ProcessInstance instance, String tag) throws Exception;

    void setTaskStatus(ProcessInstance instance, String tag, String status) throws Exception;

    void setTaskHistory(ProcessInstance instance, String tag, TaskHistory history) throws Exception;

    TaskHistory getTaskHistory(ProcessInstance instance, String tag) throws Exception;
}
