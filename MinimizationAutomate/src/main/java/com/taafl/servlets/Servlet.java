package com.taafl.servlets;

import com.taafl.model.Cell;
import com.taafl.service.DeterminationService;
import com.taafl.service.MinimizationService;
import com.taafl.vizualization.Vizualizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("")
public class Servlet extends HttpServlet {

    private Integer minimization_Q = 0;
    private Integer minimization_x = 0;
    private Integer minimization_y = 0;

    private Integer determine_x = 0;
    private Integer determine_z = 0;
    private Integer determine_k = 0;

    private void handleFormFromMinimizationPage(HttpServletRequest request, HttpServletResponse response) {
        String milly = request.getParameter("Milly");
        String mur = request.getParameter("Mur");
        this.minimization_Q = Integer.parseInt(request.getParameter("Q"));
        this.minimization_x = Integer.parseInt(request.getParameter("x"));
        this.minimization_y = Integer.parseInt(request.getParameter("y"));
        request.setAttribute("Q", this.minimization_Q);
        request.setAttribute("x", this.minimization_x);
        if (milly != null) {
            forwardDispatcher(request, response, "/millyTable.jsp");
        } else if (mur != null) {
            forwardDispatcher(request, response, "/murTable.jsp");
        }
    }

    private ArrayList<ArrayList<Cell>> getMillyTableFromForm(HttpServletRequest request) {
        ArrayList<ArrayList<Cell>> conversionTable = new ArrayList<ArrayList<Cell>>();
        for (int i = 1; i <= minimization_Q; ++i) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 1; j <= minimization_x; ++j) {

                String value = request.getParameter("Q" + (i - 1) + "x" + (j - 1));
                Integer spaceIndex = value.indexOf(" ");
                Integer qValue = Integer.parseInt(value.substring(0, spaceIndex));
                Integer xValue = Integer.parseInt(value.substring(spaceIndex + 1, value.length()));
                Cell cell = new Cell(qValue, xValue);
                row.add(cell);
            }
            conversionTable.add(row);
        }
        return conversionTable;
    }

    private ArrayList<ArrayList<Cell>> getMurTableFromForm(HttpServletRequest request) {
        ArrayList<Integer> rowWithY = new ArrayList<Integer>();
        ArrayList<ArrayList<Cell>> conversionTable = new ArrayList<>();
        for (int i = 1; i <= minimization_Q; ++i) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j <= minimization_x; ++j) {
                if (j == 0) {
                    String name = "y" + (i - 1);
                    String value = request.getParameter(name);
                    rowWithY.add(Integer.parseInt(value));
                } else {
                    String value = request.getParameter("Q" + (i - 1) + "x" + (j - 1));
                    Integer qValue = Integer.parseInt(value);
                    Integer yValue = rowWithY.get(i - 1);
                    Cell cell = new Cell(qValue, yValue);
                    row.add(cell);
                }
            }
            conversionTable.add(row);
        }
        return conversionTable;
    }

    private void vizualizateMinimizedAutomaton(ArrayList<ArrayList<Cell>> service) {
        Vizualizer vizualizer = new Vizualizer(service);
        vizualizer.vizualizateMinimizedAutomaton();
        vizualizer.createDotFile();
        vizualizer.convertDotFileToPNG();
    }

    private void vizualizateDeterministicAutomaton(Map<List<Integer>, List<List<List<Integer>>>> service) {
        Vizualizer vizualizer = new Vizualizer(service);
        vizualizer.vizualizateDeterminateAutomaton();
        vizualizer.createDotFile();
        vizualizer.convertDotFileToPNG();
    }

    private void handleFormFromDeterminatePage(HttpServletRequest request, HttpServletResponse response) {
        this.determine_x = Integer.parseInt(request.getParameter("x"));
        this.determine_k = Integer.parseInt(request.getParameter("k"));
        this.determine_z = Integer.parseInt(request.getParameter("z"));
        request.setAttribute("x", this.determine_x);
        request.setAttribute("k", this.determine_k);
        request.setAttribute("z", this.determine_z);
        forwardDispatcher(request, response, "/determineTable.jsp");
    }

    private ArrayList<Integer> getFinalStates(HttpServletRequest request) {
        ArrayList<Integer> finalStates = new ArrayList<>();
        for (int i = 0; i < determine_k; ++i) {
            String value = request.getParameter("K" + i);
            finalStates.add(Integer.parseInt(value));
        }
        return finalStates;
    }

    private ArrayList<Integer> getTransitionCell(String[] values) {
        ArrayList<Integer> cell = new ArrayList<>();
        for (int i = 0; i < values.length; ++i) {
            Integer value = Integer.parseInt(values[i]);
            cell.add(value);
        }
        return cell;
    }

    private List<List<List<Integer>>> getTableForDeterminateFromForm(HttpServletRequest request) {
        List<List<List<Integer>>> conversionTable = new ArrayList<>();
        for (int i = 0; i < determine_z; ++i) {
            List<List<Integer>> row = new ArrayList<>();
            for (int j = 0; j < determine_x; ++j) {
                String name = "x" + j + "s" + i;
                String value = request.getParameter(name);
                if (value.isEmpty()) {
                    row.add(new ArrayList<>());
                    continue;
                }
                String[] values = value.split(" ");
                row.add(this.getTransitionCell(values));

            }
            conversionTable.add(row);
        }
        return conversionTable;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("selectForm") != null) {
                if (request.getParameter("Minimize") != null) {
                    forwardDispatcher(request, response, "/minimization.jsp");
                } else if (request.getParameter("Determine") != null) {
                    forwardDispatcher(request, response, "/determine.jsp");
                }
            }
            if (request.getParameter("determineForm") != null) {
                this.handleFormFromDeterminatePage(request, response);
            } else if (request.getParameter("determineTable") != null) {
                List<Integer> finalStates = this.getFinalStates(request);
                List<List<List<Integer>>> conversionTable = this.getTableForDeterminateFromForm(request);
                DeterminationService determinationService = new DeterminationService(conversionTable, finalStates);
                determinationService.determinate();
                Map<List<Integer>, List<List<List<Integer>>>> deterministicTable = determinationService.getDeterministicTable();
                this.vizualizateDeterministicAutomaton(deterministicTable);
                request.setAttribute("result", deterministicTable);
                request.setAttribute("finalStates", determinationService.getNewFinalStates());
                forwardDispatcher(request, response, "/resultDetermination.jsp");
            }
            if (request.getParameter("minimizeForm") != null) {
                this.handleFormFromMinimizationPage(request, response);
            } else if (request.getParameter("millyTable") != null) {
                ArrayList<ArrayList<Cell>> conversionTable = this.getMillyTableFromForm(request);
                MinimizationService millyService = new MinimizationService(conversionTable, this.minimization_Q, this.minimization_x);
                millyService.handleMinimization();
                ArrayList<ArrayList<Cell>> result = millyService.getResultMinimization();
                this.vizualizateMinimizedAutomaton(result);
                request.setAttribute("result", result);
                request.setAttribute("Q", minimization_Q);
                request.setAttribute("x", minimization_x);
                forwardDispatcher(request, response, "/resultMilly.jsp");
            } else if (request.getParameter("murTable") != null) {
                ArrayList<ArrayList<Cell>> conversionTable = this.getMurTableFromForm(request);
                MinimizationService murService = new MinimizationService(conversionTable, this.minimization_Q, this.minimization_x);
                murService.handleMinimization();
                ArrayList<ArrayList<Cell>> result = murService.getResultMinimization();
                this.vizualizateMinimizedAutomaton(result);
                request.setAttribute("result", result);
                request.setAttribute("Q", minimization_Q);
                request.setAttribute("x", minimization_x);
                forwardDispatcher(request, response, "/resultMur.jsp");
            } else {
                forwardDispatcher(request, response, "/index.jsp");
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void forwardDispatcher(HttpServletRequest request, HttpServletResponse response, String template) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(template);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            forwardDispatcher(request, response, "/index.jsp");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
