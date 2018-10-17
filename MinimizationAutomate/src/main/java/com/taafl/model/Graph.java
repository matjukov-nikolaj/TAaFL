package com.taafl.model;

public class Graph {
    private String graph = "";

    public void addToGraph(String from, String to, String label) {
        this.graph += "\"" + from + "\"->\"" + to + "\"[label=\"" + label + "\"];";
    }

    public String getGraph() {
        return "digraph G{" + this.graph + "}";
    }

}
