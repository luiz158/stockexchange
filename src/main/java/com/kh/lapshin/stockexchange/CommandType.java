package com.kh.lapshin.stockexchange;

public enum CommandType {
    BUY("BUY"),
    SELL("SELL"),
    LIST("LIST"),
    CLEAN("CLEAN"),
    EXIT("EXIT");

    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public String command() {
        return command;
    }

}
