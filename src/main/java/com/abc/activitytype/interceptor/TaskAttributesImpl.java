package com.abc.activitytype.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.uengine.kernel.ProcessInstance;

import java.io.Serializable;
import java.util.*;

/**
 * Created by cloudine on 2015. 2. 26..
 */
@Service
public class TaskAttributesImpl implements TaskAttributes {
    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(TaskAttributes.class);

    @Autowired
    @Qualifier("config")
    private Properties config;

    private Map httpRepo = new HashMap();

    @Override
    public void registJobResultMap(ProcessInstance instance, String tag) throws Exception {
        instance.setProperty(tag, "jobMap", new HashMap());
    }

    @Override
    public Map getJobMap(ProcessInstance instance, String tag) throws Exception {
        if (instance.getProperty(tag, "jobMap") != null) {
            return (Map) instance.getProperty(tag, "jobMap");
        } else {
            this.registJobResultMap(instance, tag);
            return new HashMap();
        }
    }

    @Override
    public void setJobMap(ProcessInstance instance, String tag, Map jobMap) throws Exception {
        instance.setProperty(tag, "jobMap", (Serializable) jobMap);
    }

    private void setTaskParam(ProcessInstance instance, String tag, String key, Object param) throws Exception {
        Map jobMap = this.getJobMap(instance, tag);
        jobMap.put(key, param);
        this.setJobMap(instance, tag, jobMap);
    }

    private Object getTaskParam(ProcessInstance instance, String tag, String key, Object defaultValue) throws Exception {
        Map jobMap = this.getJobMap(instance,tag);

        if (!jobMap.containsKey(key)) {
            jobMap.put(key, defaultValue);
            return defaultValue;
        } else {
            return jobMap.get(key);
        }
    }

    @Override
    public String getTaskStatus(ProcessInstance instance, String tag) throws Exception {
        return (String) this.getTaskParam(instance, tag, "status", "STANDBY");
    }

    @Override
    public void setTaskStatus(ProcessInstance instance, String tag, String status) throws Exception {
        this.setTaskParam(instance, tag, "status", status);
    }

    @Override
    public void setTaskHistory(ProcessInstance instance, String tag, TaskHistory history) throws Exception {
        this.setTaskParam(instance, tag, "history", history);
    }

    @Override
    public TaskHistory getTaskHistory(ProcessInstance instance, String tag) throws Exception {
        return (TaskHistory) this.getTaskParam(instance, tag, "history", null);
    }
}
