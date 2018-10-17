package com.taafl.service;

import com.taafl.model.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MinimizationService {
    private Integer Q = 0;
    private Integer x = 0;

    private ArrayList<ArrayList<Integer>> equivalenceClasses;
    private ArrayList<ArrayList<Cell>> result = new ArrayList<>();

    private ArrayList<ArrayList<Cell>> conversionTable;

    public MinimizationService(ArrayList<ArrayList<Cell>> conversionTable, Integer Q, Integer x) {
        this.conversionTable = conversionTable;
        this.Q = Q;
        this.x = x;
    }

    public void handleMinimization() {
        ArrayList<ArrayList<Cell>> conversionTableCopy = this.getCopyOfTheTable(conversionTable);
        this.equivalenceClasses = new ArrayList<>();
        this.findEquivalenceClassesInTheTable(this.conversionTable);
        this.runMinimization(conversionTableCopy);
    }

    public ArrayList<ArrayList<Cell>> getResultMinimization() {
        return this.result;
    }

    private ArrayList<ArrayList<Cell>> getCopyOfTheTable(ArrayList<ArrayList<Cell>> conversionTable) {
        ArrayList<ArrayList<Cell>> copy = new ArrayList<>();
        for (int i = 0 ; i < Q; ++i) {
            ArrayList<Cell> col1 = new ArrayList<>();
            for (int j = 0; j < x; ++j) {
                Cell cell = conversionTable.get(i).get(j);
                Cell newCell = new Cell(cell.Q, cell.y);
                col1.add(newCell);
            }
            copy.add(col1);
        }
        return copy;
    }

    private ArrayList<ArrayList<Cell>> getTableWithEquivalenceClasses(ArrayList<ArrayList<Cell>> conversionTable) {
        ArrayList<ArrayList<Cell>> table = new ArrayList<>();
        for (int i = 0; i < this.equivalenceClasses.size(); ++i) {
            ArrayList<Integer> eqClass = this.equivalenceClasses.get(i);
            table = this.setEquivalenceClassesInTheTable(eqClass, conversionTable);
        }
        return table;
    }

    private void runMinimization(ArrayList<ArrayList<Cell>> old) {
        ArrayList<ArrayList<Cell>> tempTable = getTableWithEquivalenceClasses(this.conversionTable);
        ArrayList<ArrayList<Integer>> oldEquivalenceClasses = new ArrayList<>();

        while (!oldEquivalenceClasses.equals(this.equivalenceClasses)) {
            oldEquivalenceClasses = new ArrayList<>(this.equivalenceClasses);
            this.equivalenceClasses.clear();

            for (int i = 0; i < oldEquivalenceClasses.size(); ++i) {
                ArrayList<Integer> eqClass = oldEquivalenceClasses.get(i);
                this.findEquivalenceClassesInTheEquivClass(tempTable, eqClass);
            }
            tempTable = this.getTableWithEquivalenceClasses(tempTable);
        }

        tempTable = this.getTableWithEquivalenceClasses(tempTable);
        this.createMinimizedTable(tempTable, old);
        this.createMinimizedAutomat(tempTable);
    }

    private void createMinimizedTable(ArrayList<ArrayList<Cell>> conversionTable, ArrayList<ArrayList<Cell>> oldTable) {
        for (int i = 0; i < Q; ++i) {
            ArrayList<Cell> col = conversionTable.get(i);
            for (int j =0; j < x; ++j) {
                Cell cell = col.get(j);
                Cell oldCell = oldTable.get(i).get(j);
                Integer newQ = cell.Q;
                cell.Q = getEquivalenceClass(newQ);
                cell.y = oldCell.y;
                col.set(j, cell);
            }
            conversionTable.set(i, col);
        }
    }

    private void createMinimizedAutomat(ArrayList<ArrayList<Cell>> conversionTable) {
        for (int k = 0; k < this.equivalenceClasses.size(); ++k) {
            Integer columnIndex = this.equivalenceClasses.get(k).get(0);
            this.result.add(conversionTable.get(columnIndex));
        }
    }

    private  ArrayList<ArrayList<Cell>> setEquivalenceClassesInTheTable(ArrayList<Integer> eqClass, ArrayList<ArrayList<Cell>>conversionTable) {
        for (int i = 0; i < Q; ++i) {
            ArrayList<Cell> col = conversionTable.get(i);
            if (eqClass.contains(i)) {
                this.setEquivalenceClassesInTheColumn(col, i);
            }
        }
        return conversionTable;
    }

    private void setEquivalenceClassesInTheColumn(ArrayList<Cell> column, Integer i) {
        for (int j =0; j < x; ++j) {
            Cell cell = column.get(j);
            Integer newQ = cell.Q;
            cell.y = getEquivalenceClass(newQ);
            column.set(j, cell);
        }
        this.conversionTable.set(i, column);
    }

    private Integer getEquivalenceClass(Integer newQ) {
        for (int i =0; i < this.equivalenceClasses.size(); ++i) {
            ArrayList<Integer> col = this.equivalenceClasses.get(i);
            for (int j = 0; j < col.size(); ++j) {
                if (col.get(j).equals(newQ)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void findEquivalenceClassesInTheEquivClass(ArrayList<ArrayList<Cell>> conversionTable, ArrayList<Integer> eqClass) {
        Map<ArrayList<Integer>, ArrayList<Integer>> equivalenceClassesMap = new HashMap<>();
        for (int i = 0; i < Q; ++i) {
            if (eqClass.contains(i)) {
                this.getEquivalenceClassFromTableColumn(i, equivalenceClassesMap, conversionTable);
            }
        }

        ArrayList<ArrayList<Integer>> tempList = new ArrayList<>();
        this.createEquivalenceClassesFromMap(equivalenceClassesMap, tempList);
        this.equivalenceClasses.addAll(tempList);
    }

    private void getEquivalenceClassFromTableColumn(Integer i, Map<ArrayList<Integer>, ArrayList<Integer>> equivalenceClassesMap, ArrayList<ArrayList<Cell>> conversionTable) {
        ArrayList<Integer> outputList = new ArrayList<>();
        for (int j = 0; j < x; ++j) {
            outputList.add(conversionTable.get(i).get(j).y);
        }
        if (!equivalenceClassesMap.containsKey(outputList)) {
            ArrayList<Integer> conditionList = new ArrayList<>();
            conditionList.add(i);
            equivalenceClassesMap.put(outputList, conditionList);
        } else {
            ArrayList<Integer> conditionList = equivalenceClassesMap.get(outputList);
            conditionList.add(i);
            equivalenceClassesMap.put(outputList, conditionList);
        }
    }

    private void findEquivalenceClassesInTheTable(ArrayList<ArrayList<Cell>> conversionTable) {
        Map<ArrayList<Integer>, ArrayList<Integer>> equivalenceClassesMap = new HashMap<>();
        for (int i = 0; i < Q; ++i) {
            this.getEquivalenceClassFromTableColumn(i, equivalenceClassesMap, conversionTable);
        }
        this.createEquivalenceClassesFromMap(equivalenceClassesMap, this.equivalenceClasses);
    }

    private void createEquivalenceClassesFromMap(Map<ArrayList<Integer>, ArrayList<Integer>> equivalenceClassesMap, ArrayList<ArrayList<Integer>> equivalenceClasses) {
        for (Map.Entry<ArrayList<Integer>, ArrayList<Integer>> entry : equivalenceClassesMap.entrySet())
        {
            equivalenceClasses.add(entry.getValue());
        }
        equivalenceClasses.forEach(Collections::sort);
        Collections.sort(equivalenceClasses, (a1, a2) -> a1.get(0).compareTo(a2.get(0)));
    }

}
