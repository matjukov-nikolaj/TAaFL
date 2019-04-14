package com.taafl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App
{
    private static final String FILE_PATH = "\\src\\main\\resources\\file.txt";

    public static void main( String[] args )
    {
        Path currentRelativePath = Paths.get("");
        String projectPath = currentRelativePath.toAbsolutePath().toString();
        String filePath = projectPath + FILE_PATH;
        String strToHandle = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String st;
            while ((st = br.readLine()) != null) {
                strToHandle += st + "\n";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {

            Lexer lexer = new Lexer();
            for (Character ch : strToHandle.toLowerCase().toCharArray()) {
                lexer.goToState(ch);
            }

            RecursiveDescent recursiveDescent = new RecursiveDescent(lexer.getFoundKeyWords(), lexer.getFoundIdentifiers());
            System.out.println("=============================" + recursiveDescent.isVar(lexer.getLexemes(), 0) + "=============================");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}