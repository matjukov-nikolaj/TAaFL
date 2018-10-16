package com.taafl.servlets;

import com.taafl.model.Cell;
import com.taafl.service.MinimizationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/s")
public class MyServlet extends HttpServlet {

    private Integer Q = 0;
    private Integer x = 0;
    private Integer y = 0;

    private void handleFormFromHomePage(HttpServletRequest request, HttpServletResponse response) {
        String milly = request.getParameter("Milly");
        String mur = request.getParameter("Mur");
        this.Q = Integer.parseInt(request.getParameter("Q"));
        this.x = Integer.parseInt(request.getParameter("x"));
        this.y = Integer.parseInt(request.getParameter("y"));
        request.setAttribute("Q", this.Q);
        request.setAttribute("x", this.x);
        if (milly != null) {
            forwardDispatcher(request, response, "/millyTable.jsp");
        } else if (mur != null) {
            forwardDispatcher(request, response, "/murTable.jsp");
        }
    }

    private ArrayList<ArrayList<Cell>> getMillyTableFromForm(HttpServletRequest request) {
        ArrayList<ArrayList<Cell>> conversionTable = new ArrayList<ArrayList<Cell>>();
        for (int i = 1; i <= Q; ++i) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 1; j <= x; ++j) {

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
        for (int i = 1; i <= Q; ++i) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j <= x; ++j) {
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getParameter("valuesForm") != null) {
                this.handleFormFromHomePage(request, response);
            } else if (request.getParameter("millyTable") != null) {
                ArrayList<ArrayList<Cell>> conversionTable = this.getMillyTableFromForm(request);
                MinimizationService millyService = new MinimizationService(conversionTable, this.Q, this.x);
                millyService.handleMinimization();
                request.setAttribute("result", millyService.getResultMinimization());
                request.setAttribute("Q", Q);
                request.setAttribute("x", x);
                forwardDispatcher(request, response, "/resultMilly.jsp");
            } else if (request.getParameter("murTable") != null) {
                ArrayList<ArrayList<Cell>> conversionTable = this.getMurTableFromForm(request);
                MinimizationService murService = new MinimizationService(conversionTable, this.Q, this.x);
                murService.handleMinimization();
                request.setAttribute("result", murService.getResultMinimization());
                request.setAttribute("Q", Q);
                request.setAttribute("x", x);
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
