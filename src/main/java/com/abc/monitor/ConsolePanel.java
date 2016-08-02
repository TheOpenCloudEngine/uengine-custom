package com.abc.monitor;

/**
 * Created by jjy on 2016. 7. 30..
 */
public class ConsolePanel {

    public ConsolePanel(){
        setConsole(new Console("--- log ---"));
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    Console console;
}
