package com.abc.activitytype;

import org.metaworks.annotation.Hidden;

/**
 * Created by jjy on 2016. 7. 25..
 */
public class HadoopActivity extends ShellActivity{

    public HadoopActivity() {
        setName("Hadoop");
        setCommand("/bin/pig {=var1} {=var2} " + getOption1());
    }

    String option1;
        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

    @Override
    @Hidden
    public String getCommand() {
        return super.getCommand();
    }
}
