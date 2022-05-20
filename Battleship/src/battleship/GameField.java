package battleship;

class GameField extends ShipPosition {

    final private static int ROWS = 10;
    final private static int COLUMNS = 10;

    private final char[][] BATTLEFIELD = new char[ROWS][COLUMNS];

    /**
     * This is the GameField getter
     */

    char[][] getBATTLEFIELD() {
        return BATTLEFIELD;
    }

    @Override
    int getColumn() {
        return super.getColumn();
    }

    @Override
    int getRow() {
        return super.getRow();
    }

    /**
     * This is method marks all cells with the symbol
     */

    void denoteCell(char symbol) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                this.BATTLEFIELD[i][j] = symbol;
            }
        }
    }

    /**
     * This is method marks selected cells with the symbol
     */

    void denoteCell(char symbol, ShipCoordinate firstCoordinate, ShipCoordinate secondCoordinate) {
        if (isShipHorizontal(firstCoordinate, secondCoordinate)) {
            for (int i = Math.min(firstCoordinate.getRow(), secondCoordinate.getRow()); i <= Math.max(firstCoordinate.getRow(), secondCoordinate.getRow()); i++) {
                this.BATTLEFIELD[i][firstCoordinate.getColumn()] = symbol;
            }
        } else {
            for (int i = Math.min(firstCoordinate.getColumn(), secondCoordinate.getColumn()); i <= Math.max(firstCoordinate.getColumn(), secondCoordinate.getColumn()); i++) {
                this.BATTLEFIELD[firstCoordinate.getRow()][i] = symbol;
            }
        }
    }

    /**
     * This method prints a message after the shooting
     */

    void printShootingMessage(int notSunkenOpponentShipsCells, int row, int column, char[][] battlefield) {
        switch (battlefield[row][column]) {
            case 'X':
                if (isMarkNearCell(row, column, Mark.SHIP.getMARK(), battlefield)) {
                    System.out.print("\nYou hit a ship!\n");
                } else {
                    if (notSunkenOpponentShipsCells == 0) {
                        System.out.print("\nYou sank the last ship. You won. Congratulations!\n");
                    } else {
                        System.out.print("\nYou sank a ship!\n");
                    }
                }
                break;
            case 'M':
                System.out.print("\nYou missed!\n");
                break;
        }
    }

    /**
     * This method checks cells in 4 directions from the cell denoted with the symbol
     */

    @Override
    boolean isMarkNearCell(int row, int column, char symbol, char[][] battlefield) {
        for (int i = 1; (column - i > 0) && (battlefield[row][column - i] != Mark.MISS.getMARK()) && (battlefield[row][column - i] != Mark.FOG_OF_WAR.getMARK()); i++) {
            if (battlefield[row][column - i] == symbol) {
                return true;
            }
        }

        for (int i = 1; (column + i < 9) && (battlefield[row][column + i] != Mark.MISS.getMARK()) && (battlefield[row][column + i] != Mark.FOG_OF_WAR.getMARK()); i++) {
            if (battlefield[row][column + i] == symbol) {
                return true;
            }
        }

        for (int i = 1; (row - i > 0) && (battlefield[row - i][column] != Mark.MISS.getMARK()) && (battlefield[row - i][column] != Mark.FOG_OF_WAR.getMARK()); i++) {
            if (battlefield[row - i][column] == symbol) {
                return true;
            }
        }

        for (int i = 1; (row + i < 9) && (battlefield[row + i][column] != Mark.MISS.getMARK()) && (battlefield[row + i][column] != Mark.FOG_OF_WAR.getMARK()); i++) {
            if (battlefield[row + i][column] == symbol) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method marks a cell with the given symbol depending on the symbol
     */

    void makeShot(int row, int column, char[][] battlefield) {
        switch (battlefield[row][column]) {
            case 'O':
                battlefield[row][column] = Mark.HIT.getMARK();
                break;
            case '~':
                battlefield[row][column] = Mark.MISS.getMARK();
                break;
        }
    }

    /**
     * This method prints a first row of a field
     */

    void printFirstRow() {
        for (int firstRow = 0; firstRow <= ROWS; firstRow++) {
            if (firstRow == 0) {
                System.out.print("  ");
            } else {
                System.out.print(firstRow + " ");
            }
        }
        System.out.println();
    }

    /**
     * This method prints a covered field with the fog of war
     */

    void printCoveredField() {

        printFirstRow();

        int row = 0;

        for (char firstColumn = 'A'; firstColumn <= 'J'; firstColumn++) {
            System.out.print(firstColumn + " ");
            for (int column = 0; column < COLUMNS; column++) {
                System.out.print(this.BATTLEFIELD[row][column] + " ");
            }
            ++row;
            System.out.println();
        }
        System.out.println();
    }

    /**
     * This method prints an uncovered field
     */

    void printUncoveredField() {

        printFirstRow();

        int row = 0;

        for (char firstColumn = 'A'; firstColumn <= 'J'; firstColumn++) {
            System.out.print(firstColumn + " ");
            for (int column = 0; column < COLUMNS; column++) {
                if (this.BATTLEFIELD[row][column] == Mark.SHIP.getMARK()) {
                    System.out.print('~' + " ");
                } else {
                    System.out.print(this.BATTLEFIELD[row][column] + " ");
                }
            }
            ++row;
            System.out.println();
        }
        System.out.println("---------------------");
    }
}