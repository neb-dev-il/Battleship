package battleship;

abstract class Cell {

    private int row;
    private int column;

    /**
     * This is the column getter
     */

    int getColumn() {
        return column;
    }

    /**
     * This is the column setter
     */

    void setColumn(String input) {
        this.column = Integer.parseInt(input.substring(1)) - 1;
    }

    /**
     * This is the row getter
     */

    int getRow() {
        return row;
    }

    /**
     * This is the row setter
     */

    void setRow(String input) {
        this.row = (char) (input.charAt(0) - 65);
    }

    abstract boolean isMarkNearCell(int row, int column, char symbol, char[][] battlefield);
}