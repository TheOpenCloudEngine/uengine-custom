package com.abc.monitor;

/**
 * Created by jangjinyoung on 2016. 7. 21..
 */
public class Console {

    public Console(){}

    public Console(String log) {
        setMessage(log);
    }


    String message;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }



    String consoleId;

        public String getConsoleId() {
            return consoleId;
        }

        public void setConsoleId(String consoleId) {
            this.consoleId = consoleId;
        }


    String color;
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }


}
