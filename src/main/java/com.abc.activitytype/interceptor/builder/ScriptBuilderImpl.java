package com.abc.activitytype.interceptor.builder;

import com.abc.activitytype.interceptor.TaskAttributes;
import com.abc.activitytype.interceptor.TaskHistory;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessInstance;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by cloudine on 2015. 2. 26..
 */
@Service
public class ScriptBuilderImpl implements ScriptBuilder {
    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(TaskAttributes.class);

    @Autowired
    @Qualifier("config")
    private Properties config;

    @Override
    public String getHive(String doAs, List<String> args) throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("agent", config.getProperty("hadoop.agent.path"));
        if (StringUtils.isEmpty(doAs)) {
            doAs = config.getProperty("hadoop.super.user");
        }
        params.put("user", doAs);
        params.put("hivehome", config.getProperty("hadoop.hive.home"));

        return this.getScriptFile("hive", params, args);
    }

    @Override
    public String getSpark(String doAs, String className, String jar, List<String> args) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("agent", config.getProperty("hadoop.agent.path"));
        if (StringUtils.isEmpty(doAs)) {
            doAs = config.getProperty("hadoop.super.user");
        }
        params.put("user", doAs);
        params.put("sparkhome", config.getProperty("hadoop.spark.home"));
        params.put("className", className);
        params.put("jar", jar);

        return this.getScriptFile("spark", params, args);
        //--class org.apache.spark.examples.SparkPi --master yarn --deploy-mode cluster /usr/hdp/2.4.2.0-258/spark/lib/spark-examples*.jar 10
    }

    @Override
    public String getMapreduce(String doAs, String jar, List<String> args) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("agent", config.getProperty("hadoop.agent.path"));
        if (StringUtils.isEmpty(doAs)) {
            doAs = config.getProperty("hadoop.super.user");
        }
        params.put("user", doAs);
        params.put("hadoophome", config.getProperty("hadoop.hadoop.home"));
        params.put("jar", jar);

        return this.getScriptFile("mapreduce", params, args);
    }

    private String getScriptFile(String type, Map<String, String> params, List<String> args) throws IOException {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:/");
        String templatePath = resource.getFile().getAbsolutePath() + "/script/" + type + ".vm";
        String script = new String(Files.readAllBytes(Paths.get(templatePath)));

        if (args != null) {
            params.put("args", Joiner.on(" ").join(args));
        }

        Set<String> keySet = params.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            script = script.replace("__" + key, params.get(key));
        }
        return script;
    }
}
