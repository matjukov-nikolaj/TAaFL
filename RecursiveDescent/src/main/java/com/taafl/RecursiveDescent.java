package com.taafl;

import java.util.List;
// <var> :: 'var' + <type> + ':' + <id_list>
// <type> :: 'int' | 'float' | ...
// <id_list> :: <id> <A>
// <A> :: ';' | '," + <id> + <A>

public class RecursiveDescent
{
    private List<String> keyWords;

    private List<String> identifiers;


    public RecursiveDescent(List<String> keyWords, List<String> identifiers) {
        this.keyWords = keyWords;
        this.identifiers = identifiers;
    }

    public boolean isVar(List<String> lexemes, int index) {
        return (lexemes.get(index).equals("var") && keyWords.contains(lexemes.get(index + 1)) && lexemes.get(index + 2).equals(":") && isIdList(lexemes,index + 3));
    }

    public boolean isIdList(List<String> lexemes,int index) {
        return identifiers.contains(lexemes.get(index)) && isA(lexemes, index + 1);
    }

    public boolean isA(List<String> lexemes, int index) {
        if (lexemes.get(index).equals(";")) {
            return true;
        } else if (lexemes.get(index).equals(",")) {
            return identifiers.contains(lexemes.get(index + 1)) && isA(lexemes, index + 2);
        } else {
            return false;
        }
    }



}
