package com.kh.lapshin.stockexchange;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RecordUtils {

    private static final int COMMAND_LINE_SIZE = 3;
    private static final int COMMAND_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int NUMBER_INDEX = 2;
    private static final String COMMAND_LINE_SPLITTER = " ";

    public Record create(String commandLine) {
        List<String> commandParts = Arrays.asList(commandLine.split(COMMAND_LINE_SPLITTER));
        checkCommandLineSize(commandParts);

        RecordType recordType = tryGetCommand(commandParts);
        BigDecimal price = tryGetPrice(commandParts);
        int number = tryGetNumber(commandParts);

        return new Record(recordType, price, number);
    }

    private int tryGetNumber(List<String> commandParts) {
        String number = commandParts.get(NUMBER_INDEX);
        return Integer.parseInt(number);
    }

    private BigDecimal tryGetPrice(List<String> commandParts) {
        String priceWithoutCommas = commandParts.get(PRICE_INDEX).replace(",", "");
        return new BigDecimal(priceWithoutCommas);
    }

    private void checkCommandLineSize(List<String> commandParts) {
        if (commandParts.size() != COMMAND_LINE_SIZE) {
            throw new IllegalArgumentException();
        }
    }

    private RecordType tryGetCommand(List<String> commandParts) {
        if (!isCommandTypeCorrect(commandParts.get(COMMAND_INDEX))) {
            throw new IllegalArgumentException();
        }
        return RecordType.valueOf(commandParts.get(COMMAND_INDEX));
    }

    private boolean isCommandTypeCorrect(String commandType) {
        return Stream.of(CommandType.values())
                .map(CommandType::command)
                .anyMatch(allowedCommand -> allowedCommand.equals(commandType));
    }
}
