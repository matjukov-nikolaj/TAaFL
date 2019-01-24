package com.taafl.service;

import java.util.*;

public class DeterminationService {

    private List<Integer> finalStates;
    private List<List<Integer>> newFinalStates = new ArrayList<>();
    private Map<List<Integer>, List<List<List<Integer>>>> deterministicTable = new HashMap<>();
    private List<List<List<Integer>>> conversionTable;
    private List<List<Integer>> visitedStates = new ArrayList<>();
    private Queue<List<Integer>> queue = new LinkedList<>();

    public DeterminationService(List<List<List<Integer>>> conversionTable, List<Integer> finalStates) {
        this.conversionTable = conversionTable;
        this.finalStates = finalStates;
        ArrayList<Integer> head = new ArrayList<>();
        head.add(0);
        queue.add(head);
    }

    public void determinate() {
        boolean first = true;
        while (!queue.isEmpty()) {
            List<Integer> states = queue.peek();
            queue.remove();
            visitedStates.add(states);

            if (first) {
                first = false;
                Integer state = states.get(0);
                List<List<Integer>> row = this.conversionTable.get(state);
                List<List<Integer>> newRow = new ArrayList<>();
                List<List<List<Integer>>> newState = new ArrayList<>();

                newRow.addAll(row);
                newState.add(newRow);
                this.deterministicTable.put(states, newState);
                this.stateDetermination(row);
            } else {
                List<List<Integer>> row = this.getCopyOfRow(this.deterministicTable.get(states).get(0));
                this.stateDetermination(row);
            }
        }
        this.createNewFinalStates();
    }

    public List<List<Integer>> getNewFinalStates() {
        return this.newFinalStates;
    }

    public Map<List<Integer>, List<List<List<Integer>>>> getDeterministicTable() {
        return deterministicTable;
    }

    private void createNewFinalStates() {
        Set<List<Integer>> keySet = this.deterministicTable.keySet();

        for (List<Integer> key: keySet) {
            for (Integer value: this.finalStates) {
                if(key.contains(value) && !this.newFinalStates.contains(key)) {
                    this.newFinalStates.add(key);
                }
            }
        }

    }

    private void stateDetermination(List<List<Integer>> row) {
        for (List<Integer> cell : row) {
            List<List<Integer>> newRow = new ArrayList<>();
            List<List<List<Integer>>> newState = new ArrayList<>();
            if (cell.size() == 1) {
                Integer stateToGo = cell.get(0);
                ArrayList<Integer> newHead = new ArrayList<>();
                newHead.add(stateToGo);
                if (!visitedStates.contains(newHead) && !queue.contains(newHead)) {
                    queue.add(newHead);
                }
                newRow.add(newHead);
                newState.add(this.conversionTable.get(cell.get(0)));
                this.deterministicTable.put(cell, newState);
                continue;
            } else if (cell.isEmpty()) {
                continue;
            }

            if (!visitedStates.contains(cell)) {
                List<List<List<Integer>>> rowsForGlue = new ArrayList<>();
                for (Integer s : cell) {
                    rowsForGlue.add(this.conversionTable.get(s));
                }
                this.glueRows(rowsForGlue, cell);
            }
        }
    }

    private List<List<Integer>> getCopyOfRow(List<List<Integer>> row) {
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> item: row) {
            List<Integer> copyCell = new ArrayList<>();
            copyCell.addAll(item);
            copy.add(copyCell);
        }
        return copy;
    }

    private List<Integer> getCopyOfCell(List<Integer> cell) {
        ArrayList<Integer> copy = new ArrayList<>();
        copy.addAll(cell);
        return copy;
    }

    private List<List<List<Integer>>> getCopyOfState(List<List<List<Integer>>>  state) {
        List<List<List<Integer>>> copy = new ArrayList<>();
        for (int i = 0; i < state.size(); ++i) {
            copy.add(this.getCopyOfRow(state.get(i)));
        }
        return copy;
    }

    private void glueRows(List<List<List<Integer>>> glueRows, List<Integer> key) {
        List<Integer> copyOfKey = this.getCopyOfCell(key);
        List<List<List<Integer>>> rowsForGlue = this.getCopyOfState(glueRows);
        List<List<List<Integer>>> newState = new ArrayList<>();
        List<List<Integer>> row = rowsForGlue.get(0);
        List<List<Integer>> newRow = new ArrayList<>();
        for (int j = 0; j < row.size(); ++j) {
            List<Integer> cell = row.get(j);
            for (int k = 1; k < rowsForGlue.size(); ++k) {
                cell.addAll(rowsForGlue.get(k).get(j));
            }
            Set<Integer> hashSet = new HashSet<>(cell);
            hashSet.addAll(cell);
            cell.clear();
            cell.addAll(hashSet);
            newRow.add(cell);
        }
        newState.add(newRow);
        this.deterministicTable.put(copyOfKey, newState);
        if (!visitedStates.contains(copyOfKey) && !queue.contains(copyOfKey)) {
            queue.add(copyOfKey);
        }
    }


}
