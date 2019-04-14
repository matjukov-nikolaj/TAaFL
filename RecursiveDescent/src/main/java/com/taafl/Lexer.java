package com.taafl;

import java.util.*;

public class Lexer {

    private List<List<Integer>> table;

    private Map<Character, Integer> idByChar;

    private Map<Integer, Character> charById;

    private Map<Integer, CompletedStates> finalStates;

    private Integer currState;

    private String currStr;

    private Set<String> keyWords;

    private List<String> lexemes;

    private Character delimiter;

    private List<String> foundKeyWords;

    private List<String> foundIdentifiers;

    public Lexer() {
        this.table = new ArrayList<>();
        this.idByChar = new TreeMap<>();
        this.charById = new TreeMap<>();
        this.finalStates = new TreeMap<>();
        this.keyWords = new TreeSet<>();
        this.currState = 0;
        this.currStr = "";
        this.fillFields();
        this.lexemes = new ArrayList<>();
        this.foundKeyWords = new ArrayList<>();
        this.foundIdentifiers = new ArrayList<>();
        this.delimiter = ' ';
    }

    private void fillFields() {
        this.fillTable();
        this.fillCharById();
        this.fillIdByChar();
        this.fillFinalStates();
        this.fillKeyWords();
    }

    private void fillTable() {
        List<Integer> row0 = Arrays.asList(1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 20, -1, 22, -1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, -1, -1, 28, 29, 30);
        List<Integer> row1 = Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, -1, 10, -1, -1, 16, -1, 2, 6, 6, 6, 7, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row2 = Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row3 = Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, -1, 4, 4, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row4 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row5 = Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, -1, -1, -1, -1, 16, -1, -1, 6, 6, 6, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row6 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row7 = Arrays.asList(8, 8, 8, 8, 8, 8, 8, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row8 = Arrays.asList(8, 8, 8, 8, 8, 8, 8, 8, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row9 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row10 = Arrays.asList(11, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row11 = Arrays.asList(11, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 12, 12, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row12 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row13 = Arrays.asList(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row14 = Arrays.asList(14, 14, 14, 14, 14, 14, 14, 14, 14, 14, -1, -1, -1, -1, 16, -1, -1, 15, 15, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row15 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row16 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, 17, -1, -1, -1);
        List<Integer> row17 = Arrays.asList(18, 18, 18, 18, 18, 18, 18, 18, 18, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row18 = Arrays.asList(18, 18, 18, 18, 18, 18, 18, 18, 18, 18, -1, -1, -1, -1, -1, -1, -1, 19, 19, 19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row19 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row20 = Arrays.asList(20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 21, 21, 20, -1, -1, -1, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, -1, -1, -1, -1, -1);
        List<Integer> row21 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row22 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row23 = Arrays.asList(23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 24, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, -1, -1, -1);
        List<Integer> row24 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row25 = Arrays.asList(25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 26, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, -1, -1, -1);
        List<Integer> row26 = Arrays.asList(25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 27, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, -1, -1, -1);
        List<Integer> row27 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row28 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row29 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        List<Integer> row30 = Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
        this.table.add(row0);
        this.table.add(row1);
        this.table.add(row2);
        this.table.add(row3);
        this.table.add(row4);
        this.table.add(row5);
        this.table.add(row6);
        this.table.add(row7);
        this.table.add(row8);
        this.table.add(row9);
        this.table.add(row10);
        this.table.add(row11);
        this.table.add(row12);
        this.table.add(row13);
        this.table.add(row14);
        this.table.add(row15);
        this.table.add(row16);
        this.table.add(row17);
        this.table.add(row18);
        this.table.add(row19);
        this.table.add(row20);
        this.table.add(row21);
        this.table.add(row22);
        this.table.add(row23);
        this.table.add(row24);
        this.table.add(row25);
        this.table.add(row26);
        this.table.add(row27);
        this.table.add(row28);
        this.table.add(row29);
        this.table.add(row30);


    }

    private void fillCharById() {
        this.charById.put(0, '0');
        this.charById.put(1, '1');
        this.charById.put(2, '2');
        this.charById.put(3, '3');
        this.charById.put(4, '4');
        this.charById.put(5, '5');
        this.charById.put(6, '6');
        this.charById.put(7, '7');
        this.charById.put(8, '8');
        this.charById.put(9, '9');
        this.charById.put(10, 'a');
        this.charById.put(11, 'b');
        this.charById.put(12, 'c');
        this.charById.put(13, 'd');
        this.charById.put(14, 'e');
        this.charById.put(15, 'f');
        this.charById.put(16, 'x');
        this.charById.put(17, ' ');
        this.charById.put(18, '\n');
        this.charById.put(19, '\t');
        this.charById.put(20, 'o');
        this.charById.put(21, '.');
        this.charById.put(22, '/');
        this.charById.put(23, '*');
        this.charById.put(24, 'g');
        this.charById.put(25, 'h');
        this.charById.put(26, 'i');
        this.charById.put(27, 'j');
        this.charById.put(28, 'k');
        this.charById.put(29, 'l');
        this.charById.put(30, 'm');
        this.charById.put(31, 'n');
        this.charById.put(32, 'p');
        this.charById.put(33, 'q');
        this.charById.put(34, 'r');
        this.charById.put(35, 's');
        this.charById.put(36, 't');
        this.charById.put(37, 'u');
        this.charById.put(38, 'v');
        this.charById.put(39, 'w');
        this.charById.put(40, 'y');
        this.charById.put(41, 'z');
        this.charById.put(42, '+');
        this.charById.put(43, '-');
        this.charById.put(44, ',');
        this.charById.put(45, ':');
        this.charById.put(46, ';');
    }

    private void fillIdByChar() {
        this.idByChar.put('0', 0);
        this.idByChar.put('1', 1);
        this.idByChar.put('2', 2);
        this.idByChar.put('3', 3);
        this.idByChar.put('4', 4);
        this.idByChar.put('5', 5);
        this.idByChar.put('6', 6);
        this.idByChar.put('7', 7);
        this.idByChar.put('8', 8);
        this.idByChar.put('9', 9);
        this.idByChar.put('a', 10);
        this.idByChar.put('b', 11);
        this.idByChar.put('c', 12);
        this.idByChar.put('d', 13);
        this.idByChar.put('e', 14);
        this.idByChar.put('f', 15);
        this.idByChar.put('x', 16);
        this.idByChar.put(' ', 17);
        this.idByChar.put('\n', 18);
        this.idByChar.put('\t', 19);
        this.idByChar.put('o', 20);
        this.idByChar.put('.', 21);
        this.idByChar.put('/', 22);
        this.idByChar.put('*', 23);
        this.idByChar.put('g', 24);
        this.idByChar.put('h', 25);
        this.idByChar.put('i', 26);
        this.idByChar.put('j', 27);
        this.idByChar.put('k', 28);
        this.idByChar.put('l', 29);
        this.idByChar.put('m', 30);
        this.idByChar.put('n', 31);
        this.idByChar.put('p', 32);
        this.idByChar.put('q', 33);
        this.idByChar.put('r', 34);
        this.idByChar.put('s', 35);
        this.idByChar.put('t', 36);
        this.idByChar.put('u', 37);
        this.idByChar.put('v', 38);
        this.idByChar.put('w', 39);
        this.idByChar.put('y', 40);
        this.idByChar.put('z', 41);
        this.idByChar.put('+', 42);
        this.idByChar.put('-', 43);
        this.idByChar.put(',', 44);
        this.idByChar.put(':', 45);
        this.idByChar.put(';', 46);
    }

    private void fillFinalStates() {
        this.finalStates.put(4, CompletedStates.NUMBER16);
        this.finalStates.put(6, CompletedStates.NUMBER);
        this.finalStates.put(8, CompletedStates.NUMBER8);
        this.finalStates.put(12, CompletedStates.NUMBER2);
        this.finalStates.put(15, CompletedStates.DOUBLE);
        this.finalStates.put(19, CompletedStates.EXP);
        this.finalStates.put(21, CompletedStates.ID);
        this.finalStates.put(24, CompletedStates.SINGLE_COMMENT);
        this.finalStates.put(27, CompletedStates.MULTIPLE_COMMENT);
        this.finalStates.put(28, CompletedStates.COMMA);
        this.finalStates.put(29, CompletedStates.COLON);
        this.finalStates.put(30, CompletedStates.SEMICOLON);
    }

    private void fillKeyWords() {
        this.keyWords.add("var");
        this.keyWords.add("abstract");
        this.keyWords.add("assert");
        this.keyWords.add("boolean");
        this.keyWords.add("break");
        this.keyWords.add("byte");
        this.keyWords.add("case");
        this.keyWords.add("catch");
        this.keyWords.add("char");
        this.keyWords.add("class");
        this.keyWords.add("const");
        this.keyWords.add("continue");
        this.keyWords.add("default");
        this.keyWords.add("do");
        this.keyWords.add("double");
        this.keyWords.add("else");
        this.keyWords.add("enum");
        this.keyWords.add("extends");
        this.keyWords.add("final");
        this.keyWords.add("finally");
        this.keyWords.add("float");
        this.keyWords.add("for");
        this.keyWords.add("goto");
        this.keyWords.add("if");
        this.keyWords.add("implements");
        this.keyWords.add("import");
        this.keyWords.add("instanceof");
        this.keyWords.add("int");
        this.keyWords.add("interface");
        this.keyWords.add("long");
        this.keyWords.add("native");
        this.keyWords.add("new");
        this.keyWords.add("package");
        this.keyWords.add("private");
        this.keyWords.add("protected");
        this.keyWords.add("public");
        this.keyWords.add("return");
        this.keyWords.add("short");
        this.keyWords.add("static");
        this.keyWords.add("strictfp");
        this.keyWords.add("super");
        this.keyWords.add("switch");
        this.keyWords.add("synchronized");
        this.keyWords.add("this");
        this.keyWords.add("throw");
        this.keyWords.add("throws");
        this.keyWords.add("transient");
        this.keyWords.add("try");
        this.keyWords.add("void");
        this.keyWords.add("volatile");
        this.keyWords.add("while");
    }

    public void goToState(Character ch) {
        this.currStr += ch.toString();
        Integer id = idByChar.get(ch);
        this.currState = this.table.get(currState).get(id);
        if (this.currState == -1) {
            System.out.println("Error: " + this.currStr);
            this.initCurrentFields();
            return;
        }
        this.currentStateHandler();
    }

    private void currentStateHandler() {
        if (!this.finalStates.containsKey(this.currState)) {
            return;
        }
        this.currStr = this.currStr.replaceAll(" ", "");
        CompletedStates state = this.finalStates.get(this.currState);
        this.currStr = this.currStr.replaceAll("\n", "");
        if (state == CompletedStates.ID && this.keyWords.contains(this.currStr)) {
            System.out.println("Key word: " + this.currStr);
            this.lexemes.add(this.currStr);
            this.foundKeyWords.add(this.currStr);

        } else if (state != CompletedStates.SINGLE_COMMENT && state != CompletedStates.MULTIPLE_COMMENT) {
            System.out.println("Id: " + CompletedStates.getStr(this.finalStates.get(this.currState)) + " Value: " + this.currStr);
            this.foundIdentifiers.add(this.currStr);
            this.lexemes.add(this.currStr);
        } else if (state == CompletedStates.COMMA || state == CompletedStates.COLON || state == CompletedStates.SEMICOLON) {
            this.lexemes.add(this.currStr);
        }
        this.initCurrentFields();
    }

    private void initCurrentFields() {
        this.currStr = "";
        this.currState = 0;
    }

    public List<String> getLexemes() {
        return this.lexemes;
    }

    public List<String> getFoundIdentifiers() {
        return foundIdentifiers;
    }

    public List<String> getFoundKeyWords() {
        return foundKeyWords;
    }
}
