package com.kh.lapshin.stockexchange;

import static com.kh.lapshin.stockexchange.CommandType.EXIT;
import static com.kh.lapshin.stockexchange.CommandType.LIST;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TradeMarket {
    private List<Record> buyRecords = new ArrayList<>();
    private List<Record> sellRecords = new ArrayList<>();
    private List<Record> tradeRecords = new ArrayList<>();

    public void start() {
        Scanner in = new Scanner(System.in);
        String command = readCommandLine(in);

        // for test -------------------------
        sellRecords.add(new Record(RecordType.SELL, new BigDecimal(90), 120));
        sellRecords.add(new Record(RecordType.SELL, new BigDecimal(80), 180));
        // ----------------------------------

        while (!EXIT.command().equals(command)) {

            if (LIST.command().equals(command)) {
                performListCommand();
            } else {
                getOrder(command).ifPresent(inputOrder -> {
                    if (inputOrder.isBuyOrder()) {

                        List<Record> matchingSellRecords = sellRecords.stream()
                                .filter(Record::haveStocks)
                                .filter(sellRecord -> isBuyPriceMoreSellPrice(inputOrder, sellRecord))
                                .sorted(Comparator.comparing(Record::getStockPrice).thenComparing(Record::getRecordId))
                                .collect(Collectors.toList());

                        for (Record sellRecord : matchingSellRecords) {
                            // 1. Add TRADE record
                            int tradeNumber = Math.min(inputOrder.getStockNumber(), sellRecord.getStockNumber());
                            tradeRecords.add(createTrade(sellRecord.getStockPrice(), tradeNumber));

                            // 2. Decrease BUY and SELL stocks number
                            int diffNumber = inputOrder.getStockNumber() - sellRecord.getStockNumber();
                            Record recordWithBiggerNumber = diffNumber > 0 ? inputOrder : sellRecord;
                            inputOrder.resetStockNumber();
                            sellRecord.resetStockNumber();
                            recordWithBiggerNumber.setStockNumber(Math.abs(diffNumber));

                            // 3. Stop processing when there are no stocks left
                            if (!inputOrder.haveStocks()) {
                                break;
                            }
                        }

                        // 4. if there are some stocks left then add order to corresponding list
                        if (inputOrder.haveStocks()) {
                            buyRecords.add(inputOrder);
                        }

                    }
                });
            }

            command = readCommandLine(in);
        }
    }

    private void performListCommand() {
        List<Record> allRecords = new ArrayList<>();
        allRecords.addAll(buyRecords);
        allRecords.addAll(sellRecords);
        allRecords.addAll(tradeRecords);

        allRecords.stream()
                .filter(Record::haveStocks)
                .sorted(Comparator.comparing(Record::getRecordId))
                .forEach(System.out::println);
    }

    private boolean isBuyPriceMoreSellPrice(Record order, Record sellRecord) {
        return order.getStockPrice().compareTo(sellRecord.getStockPrice()) >= 0;
    }

    private Record createTrade(BigDecimal price, int number) {
        return new Record(RecordType.TRADE, price, number);
    }

    private Optional<Record> getOrder(String command) {
        try {
            RecordUtils recordUtils = new RecordUtils();
            return Optional.of(recordUtils.create(command));
        } catch (Exception e) {
            System.out.println("ERR");
        }
        return Optional.empty();
    }

    private String readCommandLine(Scanner in) {
        System.out.print("> ");
        return in.nextLine().trim();
    }
}
