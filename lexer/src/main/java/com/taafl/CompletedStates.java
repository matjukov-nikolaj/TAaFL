package com.taafl;

import java.util.HashMap;
import java.util.Map;

public enum CompletedStates {
    EMPTY(-1),
    ERROR(0),
    NUMBER(1),
    NUMBER16(2),
    NUMBER8(3),
    NUMBER2(4),
    DOUBLE(5),
    EXP(6),
    ID(7),
    SINGLE_COMMENT(8),
    MULTIPLE_COMMENT(9);


    private final Integer value;

    CompletedStates(final Integer value) {
        this.value = value;
    }

    private static Map<Integer, CompletedStates> getCompletedState() {
        HashMap<Integer, CompletedStates> map = new HashMap<>();
        map.put(EMPTY.value, EMPTY);
        map.put(ERROR.value, ERROR);
        map.put(NUMBER.value, NUMBER);
        map.put(NUMBER16.value, NUMBER16);
        map.put(NUMBER8.value, NUMBER8);
        map.put(NUMBER2.value, NUMBER2);
        map.put(DOUBLE.value, DOUBLE);
        map.put(EXP.value, EXP);
        map.put(ID.value, ID);
        map.put(SINGLE_COMMENT.value, SINGLE_COMMENT);
        map.put(MULTIPLE_COMMENT.value, MULTIPLE_COMMENT);
        return map;
    }

    private static Map<CompletedStates, String> enumToString() {
        HashMap<CompletedStates, String> map = new HashMap<>();
        map.put(EMPTY, "EMPTY");
        map.put(ERROR, "ERROR");
        map.put(NUMBER, "NUMBER");
        map.put(NUMBER16, "NUMBER16");
        map.put(NUMBER8, "NUMBER8");
        map.put(NUMBER2, "NUMBER2");
        map.put(DOUBLE, "DOUBLE");
        map.put(EXP, "EXP");
        map.put(ID, "ID");
        map.put(SINGLE_COMMENT, "SINGLE_COMMENT");
        map.put(MULTIPLE_COMMENT, "MULTIPLE_COMMENT");
        return map;
    }

    public static String getStr(CompletedStates state) {

        return enumToString().get(state);
    }

    public static CompletedStates createFromInteger(Integer mode) {
        return getCompletedState().get(mode);
    }
}
