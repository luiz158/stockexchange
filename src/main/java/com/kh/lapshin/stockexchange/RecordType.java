package com.kh.lapshin.stockexchange;

public enum RecordType {
    BUY("BUY"),
    SELL("SELL"),
    TRADE("TRADE");

    private String type;

    RecordType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
