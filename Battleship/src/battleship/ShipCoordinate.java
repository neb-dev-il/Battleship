package battleship;

class ShipCoordinate extends ShipPosition {

    private static String[] arrayOfCoordinates;

    /**
     * This is arrayOfCoordinates getter
     */

    static String[] getArrayOfCoordinates() {
        return arrayOfCoordinates;
    }

    /**
     * This is arrayOfCoordinates setter
     */

    static void processUserInputToArrayOfCoordinates(String userInput) {
        ShipCoordinate.arrayOfCoordinates = userInput.split("\\s+");
    }

    @Override
    int getColumn() {
        return super.getColumn();
    }

    @Override
    void setColumn(String input) {
        super.setColumn(input);
    }

    @Override
    int getRow() {
        return super.getRow();
    }

    @Override
    void setRow(String input) {
        super.setRow(input);
    }
}