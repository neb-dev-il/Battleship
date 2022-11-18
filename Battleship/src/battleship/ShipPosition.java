package battleship;

class ShipPosition extends Cell {

    private Ship ship;

    ShipPosition(Ship ship) {
        this.ship = ship;
    }

    ShipPosition() {
    }

    /**
     * This method checks the opponent's coordinates correctness
     */

    void checkCoordinateCorrectness(ShipCoordinate coordinate) throws InvalidCoordinateException {
        if (coordinate.getRow() < 0 || coordinate.getRow() > 9 || coordinate.getColumn() < 0 || coordinate.getColumn() > 9) {
            throw new InvalidCoordinateException("Error! You entered the wrong coordinates! Try again:");
        }
    }

    /**
     * This method checks a position of a ship
     */

    boolean isShipHorizontal(ShipCoordinate firstCoordinate, ShipCoordinate secondCoordinate) {
        return firstCoordinate.getColumn() == secondCoordinate.getColumn();
    }

    /**
     * This method throws an exception when the ship's position is neither horizontal nor vertical
     */

    void checkShipLocationCorrectness(ShipCoordinate firstCoordinate, ShipCoordinate secondCoordinate) throws InvalidCoordinateException {
        if (firstCoordinate.getRow() != secondCoordinate.getRow() && firstCoordinate.getColumn() != secondCoordinate.getColumn()) {
            throw new InvalidCoordinateException("Error! Wrong ship location! Try again:\n");
        }
    }

    /**
     * This method throws an exception if the user wants
     * to mark a cell as "O" next to the another cell marked as "O"
     */

    private void printIfShipIsTooCloseToAnotherOneMessage() throws InvalidCoordinateException {
        throw new InvalidCoordinateException("Error! You placed it too close to another one. Try again:\n");
    }

    /**
     * This method throws an exception when the user tries to place a ship too close to another one
     */

    void checkIfAnyShipNear(ShipCoordinate firstCoordinate, ShipCoordinate secondCoordinate, char[][] battlefield) throws InvalidCoordinateException {
        if (isShipHorizontal(firstCoordinate, secondCoordinate)) {
            for (int i = Math.min(firstCoordinate.getColumn(), secondCoordinate.getColumn()); i <= Math.max(firstCoordinate.getColumn(), secondCoordinate.getColumn()); i++) {
                if (isMarkNearCell(firstCoordinate.getRow(), i, Mark.SHIP.getMARK(), battlefield)) {
                    printIfShipIsTooCloseToAnotherOneMessage();
                }
            }
        } else {
            for (int i = Math.min(firstCoordinate.getRow(), secondCoordinate.getRow()); i <= Math.max(firstCoordinate.getRow(), secondCoordinate.getRow()); i++) {
                if (isMarkNearCell(i, firstCoordinate.getColumn(), Mark.SHIP.getMARK(), battlefield)) {
                    printIfShipIsTooCloseToAnotherOneMessage();
                }
            }
        }
    }

    /**
     * This method checks all cells around from the cell denoted with the symbol
     */

    @Override
    boolean isMarkNearCell(int row, int column, char symbol, char[][] battlefield) {
        if (row >= 0 && row <= 8 && column >= 1 && column <= 9) {
            return battlefield[row + 1][column - 1] == symbol;
        } else if (row >= 1 && row <= 9 && column >= 0 && column <= 8) {
            return battlefield[row - 1][column + 1] == symbol;
        } else if (row >= 0 && row <= 8 && column >= 0 && column <= 8) {
            return battlefield[row + 1][column + 1] == symbol;
        } else if (row >= 1 && row <= 9 && column >= 1 && column <= 9) {
            return battlefield[row - 1][column - 1] == symbol;
        } else if (row >= 0 && row <= 8) {
            return battlefield[row + 1][column] == symbol;
        } else if (row >= 1 && row <= 9) {
            return battlefield[row - 1][column] == symbol;
        } else if (column >= 1 && column <= 9) {
            return battlefield[row][column - 1] == symbol;
        } else if (column >= 0 && column <= 8) {
            return battlefield[row][column + 1] == symbol;
        }
        return false;
    }

    /**
     * This method returns the length of the ship
     */

    private int checkShipLength(ShipCoordinate firstCoordinate, ShipCoordinate secondCoordinate) {
        if (isShipHorizontal(firstCoordinate, secondCoordinate)) {
            return Math.abs(firstCoordinate.getRow() - secondCoordinate.getRow()) + 1;
        } else {
            return Math.abs(firstCoordinate.getColumn() - secondCoordinate.getColumn()) + 1;
        }
    }

    /**
     * This method throws an exception when the user tries to place a ship of the wrong length
     */

    void checkShipLengthCorrectness(ShipCoordinate firstCoordinate, ShipCoordinate secondCoordinate) throws InvalidCoordinateException {
        if (checkShipLength(firstCoordinate, secondCoordinate) != ship.getLENGTH()) {
            throw new InvalidCoordinateException("Error! Wrong length of the " + ship.getNAME() + "! Try again:\n");
        }
    }
}