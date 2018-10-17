package com.taafl.vizualization;

import com.taafl.model.Cell;
import com.taafl.model.Constants;
import com.taafl.model.Graph;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Vizualizer {

    private ArrayList<ArrayList<Cell>> result;
    private Graph graph;

    public Vizualizer(ArrayList<ArrayList<Cell>> result) {
        this.result = result;
        this.graph = new Graph();
    }

    public void vizualizateGraph() {
        for (int i = 0; i < result.size(); ++i) {
            ArrayList<Cell> col = result.get(i);
            for (int j = 0; j < col.size(); ++j) {
                Cell element = col.get(j);
                String from = Integer.toString(i);
                String to = element.Q.toString();
                String label = "x" + j + "/" + element.y;
                this.graph.addToGraph(from, to, label);
            }
        }
    }

    public void createDotFile() {
        try {
            File file = new File(Constants.DOT_FILE_FOLDER, Constants.DOT_FILE_NAME);
            file.getParentFile().mkdirs();
            PrintWriter writer = new PrintWriter(file);
            writer.println(this.graph.getGraph());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void convertDotFileToPNG() {
        try {
            System.out.println("Started GraphViz");
            List<String> params = java.util.Arrays.asList(Constants.DOT_EXE, "-Tpng"
                    , Constants.DOT_FILE_FOLDER + "\\" + Constants.DOT_FILE_NAME, "-o",
                    Constants.PNG_FILE_PATH);
            ProcessBuilder p = new ProcessBuilder(params);
            p.start();
            System.out.println("PNG file was created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
