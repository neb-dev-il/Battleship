package battleship;

abstract class Cell {

    private int row;
    private int column;

    int getColumn() {
        return column;
    }

    void setColumn(String input) {
        this.column = Integer.parseInt(input.substring(1)) - 1;
    }

    int getRow() {
        return row;
    }

    void setRow(String input) {
        this.row = (char) (input.charAt(0) - 65);
    }

    abstract boolean isMarkNearCell(int row, int column, char symbol, char[][] battlefield);
}