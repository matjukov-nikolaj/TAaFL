package com.taafl;

public class App
{
    public static void main( String[] args )
    {
        try {
            String test = "123 a1 1a 0x64f ";
            Lexer lexer = new Lexer();
            for (Character ch : test.toCharArray()) {
                lexer.goToState(ch);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}