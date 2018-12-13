package com.kh.lapshin.stockexchange;

import java.math.BigDecimal;

public class Record {

    private static long RECORD_NUMBER = 0;

    private long recordId = RECORD_NUMBER++;
    private RecordType recordType;
    private BigDecimal stockPrice;
    private int stockNumber;

    public Record(RecordType recordType, BigDecimal stockPrice, int stockNumber) {
        this.recordType = recordType;
        this.stockPrice = stockPrice;
        this.stockNumber = stockNumber;
    }

    public boolean isBuyOrder() {
        return recordType == RecordType.BUY;
    }

    public boolean isSellOrder() {
        return recordType == RecordType.SELL;
    }

    public void decreaseStockNumber(int number) {
        stockNumber -= number;
    }

    public void resetStockNumber() {
        stockNumber = 0;
    }

    public boolean haveStocks() {
        return stockNumber > 0;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public long getRecordId() {
        return recordId;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(recordType.getType()).append(" ");
        result.append(stockPrice).append(" ");
        result.append(stockNumber);

        return result.toString();
    }
}
