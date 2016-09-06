package com.abc.activitytype.interceptor.builder;

import com.abc.activitytype.interceptor.TaskHistory;
import org.uengine.kernel.ProcessInstance;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ScriptBuilder {

    public static final String NAMESPACE = ScriptBuilder.class.getName();

    String getHive(String doAs, List<String> args) throws IOException;

    String getSpark(String doAs, String className, String jar, List<String> args) throws IOException;

    String getMapreduce(String doAs, String jar, List<String> args) throws IOException;
}
