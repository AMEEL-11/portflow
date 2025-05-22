package com.springprojets.portflow;

public class StoragePosition {
    private int row;
    private int column;
    private int level;

    public StoragePosition(int row, int column, int level) {
        this.row = row;
        this.column = column;
        this.level = level;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
} 