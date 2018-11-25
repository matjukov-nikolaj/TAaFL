package com.taafl.model;

import java.util.List;

public class DCell {
    public List<Integer> cell;
    public List<Integer> composedCell;

    public boolean isEmpty() {
        if ((this.cell.isEmpty()) && (this.composedCell.isEmpty())) {
            return true;
        }
        return false;
    }
}
