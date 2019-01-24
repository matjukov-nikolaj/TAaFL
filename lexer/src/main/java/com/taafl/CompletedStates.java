package com.taafl;

import java.util.HashMap;
import java.util.Map;

public enum CompletedStates {
    EMPTY(-1),
    ERROR(0),
    NUMBER(1),
    DIGIT16(2);

    private final Integer value;

    CompletedStates(final Integer value) {
        this.value = value;
    }

    private static Map<Integer, CompletedStates> getCompletedState() {
        HashMap<Integer, CompletedStates> intToMap = new HashMap<>();
        intToMap.put(EMPTY.value, EMPTY);
        intToMap.put(ERROR.value, ERROR);
        intToMap.put(NUMBER.value, NUMBER);
        intToMap.put(DIGIT16.value, DIGIT16);
        return intToMap;
    }

    private static Map<CompletedStates, String> enumToString() {
        HashMap<CompletedStates, String> intToMap = new HashMap<>();
        intToMap.put(EMPTY, "EMPTY");
        intToMap.put(ERROR, "ERROR");
        intToMap.put(NUMBER, "NUMBER");
        intToMap.put(DIGIT16, "DIGIT16");
        return intToMap;
    }

    public static String getStr(CompletedStates state) {

        return enumToString().get(state);
    }

    public static CompletedStates createFromInteger(Integer mode) {
        return getCompletedState().get(mode);
    }
}
