package com.abc.monitor;

/**
 * Created by jangjinyoung on 2016. 7. 21..
 */
public class Console {

    public Console(){}

    public Console(String log) {
        setMessage(log);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    String message;


    public String getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(String consoleId) {
        this.consoleId = consoleId;
    }

    String consoleId;


}
