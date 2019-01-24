package com.taafl;

import java.util.*;

public class Lexer {

    private List<List<Integer>> table;

    private Map<Character, Integer> idByChar;

    private Map<Integer, Character> charById;

    private Map<Integer, CompletedStates> finalStates;

    private Integer currState;

    private String currStr;

    public Lexer() {
        this.table = new ArrayList<>();
        this.idByChar = new TreeMap<>();
        this.charById = new TreeMap<>();
        this.finalStates = new TreeMap<>();
        this.currState = 0;
        this.currStr = "";
        this.fillFields();
    }

    private void fillFields() {
        this.fillTable();
        this.fillCharById();
        this.fillIdByChar();
        this.fillFinalStates();
    }

    private void fillTable() {
        List<Integer> row0 = Arrays.asList(   1,   5,   5,   5,   5,   5,   5,   5,   5,   5,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   0,   0,   0);
        List<Integer> row1 = Arrays.asList(   1,   5,   5,   5,   5,   5,   5,   5,   5,   5,  -1,  -1,  -1,  -1,  -1,  -1,   2,   6,   6,   6);
        List<Integer> row2 = Arrays.asList(   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,  -1,  -1,  -1,  -1);
        List<Integer> row3 = Arrays.asList(   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,  -1,   4,   4,   4);
        List<Integer> row4 = Arrays.asList(  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1);
        List<Integer> row5 = Arrays.asList(   5,   5,   5,   5,   5,   5,   5,   5,   5,   5,  -1,  -1,  -1,  -1,  -1,  -1,  -1,   6,   6,   6);
        List<Integer> row6 = Arrays.asList(  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1);
        this.table.add(row0);
        this.table.add(row1);
        this.table.add(row2);
        this.table.add(row3);
        this.table.add(row4);
        this.table.add(row5);
        this.table.add(row6);
    }

    private void fillCharById() {
        charById.put(0, '0');
        charById.put(1, '1');
        charById.put(2, '2');
        charById.put(3, '3');
        charById.put(4, '4');
        charById.put(5, '5');
        charById.put(6, '6');
        charById.put(7, '7');
        charById.put(8, '8');
        charById.put(9, '9');
        charById.put(10, 'a');
        charById.put(11, 'b');
        charById.put(12, 'c');
        charById.put(13, 'd');
        charById.put(14, 'e');
        charById.put(15, 'f');
        charById.put(16, 'x');
        charById.put(17, ' ');
        charById.put(18, '\n');
        charById.put(19, '\t');
    }

    private void fillIdByChar() {
        idByChar.put('0', 0);
        idByChar.put('1', 1);
        idByChar.put('2', 2);
        idByChar.put('3', 3);
        idByChar.put('4', 4);
        idByChar.put('5', 5);
        idByChar.put('6', 6);
        idByChar.put('7', 7);
        idByChar.put('8', 8);
        idByChar.put('9', 9);
        idByChar.put('a', 10);
        idByChar.put('b', 11);
        idByChar.put('c', 12);
        idByChar.put('d', 13);
        idByChar.put('e', 14);
        idByChar.put('f', 15);
        idByChar.put('x', 16);
        idByChar.put(' ', 17);
        idByChar.put('\n', 18);
        idByChar.put('\t', 19);
    }

    private void fillFinalStates() {
        finalStates.put(4, CompletedStates.DIGIT16);
        finalStates.put(6, CompletedStates.NUMBER);
    }

    public void goToState(Character ch) {
        this.currStr += ch.toString();
        Integer id = idByChar.get(ch);
        this.currState = this.table.get(currState).get(id);
        if (this.currState == -1) {
            System.out.println("Error: " + this.currStr);
            this.currStr = "";
            this.currState = 0;
            return;
        }
        if (this.finalStates.containsKey(this.currState)) {
            System.out.println("Id: " + CompletedStates.getStr(this.finalStates.get(this.currState)) + " Value: " + this.currStr);
            this.currStr = "";
            this.currState = 0;
            return;
        }
    }

}
