package battleship;

import java.util.Objects;
import java.util.Scanner;

enum Ship {

    AIRCRAFT_CARRIER_SIZE("Aircraft Carrier", 5),
    BATTLESHIP_SIZE("Battleship", 4),
    SUBMARINE_SIZE("Submarine", 3),
    CRUISER_SIZE("Cruiser", 3),
    DESTROYER_SIZE("Destroyer", 2);

    final String name;
    final int length;

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}

public class Main {
    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        Battlefield shipsField = new Battlefield();
        shipsField.fillField();
        shipsField.printCoveredField();

        for (Ship s : Ship.values()) {
            ShipCoordinate coordinate = new ShipCoordinate(shipsField, s);
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n\n", s.getName(), s.getLength());
            boolean isCorrectShipPlace = false;
            while (!isCorrectShipPlace) {
                String coordinateStringInput = scanner.nextLine();
                try {
                    coordinate.setCoordinates(coordinateStringInput);
                    coordinate.checkShipLengthCorrectness();
                    coordinate.checkShipLocationCorrectness();
                    coordinate.checkAllCellsAroundShip();
                    isCorrectShipPlace = true;
                } catch (InvalidCoordinateException e) {
                    System.out.println();
                    System.out.println(e.getMessage());
                }
            }
            System.out.println();
            coordinate.denoteCellAsO();
            shipsField.printCoveredField();
        }

        System.out.println();
        System.out.println("The game starts!");
        System.out.println();
        shipsField.printUncoveredField();
        System.out.println();
        System.out.println("Take a shot!");
        System.out.println();

        shipsField.setAll0CellsNumber();

        while (shipsField.getAllOCells() != 0) {
            String coordinateStringInput = scanner.next();
            System.out.println();
            try {
                shipsField.setRow(coordinateStringInput);
                shipsField.setColumn(coordinateStringInput);
                shipsField.checkCoordinateCorrectness();
                shipsField.reduceNumberOfDenotedXCell(shipsField.getRow(), shipsField.getColumn());
                shipsField.makeShot();
                shipsField.printUncoveredField();
                System.out.println(shipsField.getAllOCells());
                shipsField.printShootingMessage();
            } catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
        System.out.println("\nYou sank the last ship. You won. Congratulations!");
    }
}

class ShipCoordinate {
    Battlefield battleship;
    Ship ship;
    int firstColumnNumber;
    int firstRowNumber;
    int lastColumnNumber;
    int lastRowNumber;
    int row;
    int column;

    public ShipCoordinate(Battlefield battleship, Ship ship) {
        this.battleship = battleship;
        this.ship = ship;
    }

    public ShipCoordinate() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(String input) {
        this.row = (char) (input.charAt(0) - 65);
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(String input) {
        this.column = Integer.parseInt(input.substring(1)) - 1;
    }

    void setCoordinates(String input) {
        String[] splitInput = input.split("\\s+");
        this.firstColumnNumber = (char) (splitInput[0].charAt(0) - 65);
        this.firstRowNumber = Integer.parseInt(splitInput[0].substring(1)) - 1;
        this.lastColumnNumber = (char) (splitInput[1].charAt(0) - 65);
        this.lastRowNumber = Integer.parseInt(splitInput[1].substring(1)) - 1;
    }

    boolean isShipHorizontal() {
        return firstColumnNumber == lastColumnNumber;
    }

    void checkShipLengthCorrectness() throws InvalidCoordinateException {
        int counter;
        if (isShipHorizontal()) {
            counter = Math.abs(firstRowNumber - lastRowNumber) + 1;
        } else {
            counter = Math.abs(firstColumnNumber - lastColumnNumber) + 1;
        }
        if (counter != ship.getLength()) {
            throw new InvalidCoordinateException("Error! Wrong length of the " + ship.getName() + "! Try again:\n");
        }
    }

    void checkShipLocationCorrectness() throws InvalidCoordinateException {
        if (firstRowNumber != lastRowNumber && firstColumnNumber != lastColumnNumber) {
            throw new InvalidCoordinateException("Error! Wrong ship location! Try again:\n");
        }
    }

    void checkCoordinateCorrectness() throws InvalidCoordinateException {
        if (row < 0 || row > 9 || column < 0 || column > 9) {
            throw new InvalidCoordinateException("Error! You entered the wrong coordinates! Try again:");
        }
    }

    void denoteCellAsO() {
        if (isShipHorizontal()) {
            for (int i = Math.min(firstRowNumber, lastRowNumber); i <= Math.max(firstRowNumber, lastRowNumber); i++) {
                battleship.getBattleship()[firstColumnNumber][i] = "O";
            }
        } else {
            for (int i = Math.min(firstColumnNumber, lastColumnNumber); i <= Math.max(firstColumnNumber, lastColumnNumber); i++) {
                battleship.getBattleship()[i][firstRowNumber] = "O";
            }
        }
    }

    void printIfShipIsTooCloseToAnotherOne() throws InvalidCoordinateException {
        throw new InvalidCoordinateException("Error! You placed it too close to another one. Try again:\n");
    }

    void checkAllCellsAroundShip() throws InvalidCoordinateException {
        if (isShipHorizontal()) {
            for (int i = Math.min(firstRowNumber, lastRowNumber); i <= Math.max(firstRowNumber, lastRowNumber); i++) {
                if (isFogOfWarNextToCell(firstColumnNumber, i, battleship.getBattleship())) {
                    printIfShipIsTooCloseToAnotherOne();
                }
            }
        } else {
            for (int i = Math.min(firstColumnNumber, lastColumnNumber); i <= Math.max(firstColumnNumber, lastColumnNumber); i++) {
                if (isFogOfWarNextToCell(i, firstRowNumber, battleship.getBattleship())) {
                    printIfShipIsTooCloseToAnotherOne();
                }
            }
        }
    }

    boolean isFogOfWarNextToCell(int row, int column, String[][] battleship) {

        if (row >= 0 && row <= 8 && column >= 1 && column <= 9) {
            if (Objects.equals(battleship[row + 1][column - 1], "~")) {
                return false;
            }
        } else if (row >= 1 && row <= 9 && column >= 0 && column <= 8) {
            if (Objects.equals(battleship[row - 1][column + 1], "~")) {
                return false;
            }
        } else if (row >= 0 && row <= 8 && column >= 0 && column <= 8) {
            if (Objects.equals(battleship[row + 1][column + 1], "~")) {
                return false;
            }
        } else if (row >= 1 && row <= 9 && column >= 1 && column <= 9) {
            if (Objects.equals(battleship[row - 1][column - 1], "~")) {
                return false;
            }
        } if (row >= 0 && row <= 8) {
            return !Objects.equals(battleship[row + 1][column], "~");
        } else if (row >= 1 && row <= 9) {
            return !Objects.equals(battleship[row - 1][column], "~");
        } else if (column >= 1 && column <= 9) {
            return !Objects.equals(battleship[row][column - 1], "~");
        } else if (column >= 0 && column <= 8) {
            return !Objects.equals(battleship[row][column + 1], "~");
        }
        return true;
    }
}

class Battlefield extends ShipCoordinate {
    final static int ROWS = 10;
    final static int COLUMNS = 10;
    int allOCells;

    String[][] battleship = new String[ROWS][COLUMNS];

    public Battlefield() {
    }

    public String[][] getBattleship() {
        return battleship;
    }

    void setAll0CellsNumber() {
        for (Ship s : Ship.values()) {
            this.allOCells += s.getLength();
        }
    }

    void reduceNumberOfDenotedXCell(int row, int column) {
        if (battleship[row][column].equals("O")) {
            this.allOCells = allOCells - 1;
        }
    }

    public int getAllOCells() {
        return allOCells;
    }

    boolean checkCellsInFourDirections(int row, int column) {
        for (int i = 1; column - i > 0 && !Objects.equals(battleship[row][column - i], "M") && !Objects.equals(battleship[row][column - i], "~"); i++) {
            if (this.battleship[row][column - i].equals("O")) {
                return false;
            }
        }

        for (int i = 1; column + i < 9 && !Objects.equals(battleship[row][column + i], "M") && !Objects.equals(battleship[row][column + i], "~"); i++) {
            if (this.battleship[row][column + i].equals("O")) {
                return false;
            }
        }

        for (int i = 1; row - i > 0 && !Objects.equals(battleship[row - i][column], "M") && !Objects.equals(battleship[row - i][column], "~"); i++) {
            if (this.battleship[row - i][column].equals("O")) {
                return false;
            }
        }

        for (int i = 1; row + i < 9 && !Objects.equals(battleship[row + i][column], "M") && !Objects.equals(battleship[row + i][column], "~"); i++) {
            if (this.battleship[row + i][column].equals("O")) {
                return false;
            }
        }
        return true;
    }

    void makeShot() {
        switch (this.battleship[row][column]) {
            case "O" -> this.battleship[row][column] = "X";
            case "~" -> this.battleship[row][column] = "M";
        }
    }

    public void fillField() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                this.battleship[i][j] = "~";
            }
        }
    }

    void printShootingMessage() {
        if (battleship[row][column].equals("X")) {
            if (checkCellsInFourDirections(getRow(), getColumn())) {
                System.out.println();
                System.out.println("You sank a ship! Specify a new target:");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("You hit a ship! Try again:");
                System.out.println();
            }
        } else {
            System.out.println();
            System.out.println("You missed. Try again:");
            System.out.println();
        }
    }

    void printUncoveredField() {
        for (int firstRow = 0; firstRow <= ROWS; firstRow++) {
            if (firstRow == 0) {
                System.out.print("  ");
            } else {
                System.out.print(firstRow + " ");
            }
        }

        System.out.println();

        int row = 0;

        for (char firstColumn = 'A'; firstColumn <= 'J'; firstColumn++) {
            System.out.print(firstColumn + " ");
            for (int column = 0; column < COLUMNS; column++) {
                if (this.battleship[row][column].equals("O")) {
                    System.out.print("~" + " ");
                } else {
                    System.out.print(this.battleship[row][column] + " ");
                }
            }
            ++row;
            System.out.println();
        }
    }

    void printCoveredField() {
        for (int firstRow = 0; firstRow <= ROWS; firstRow++) {
            if (firstRow == 0) {
                System.out.print("  ");
            } else {
                System.out.print(firstRow + " ");
            }
        }

        System.out.println();

        int row = 0;

        for (char firstColumn = 'A'; firstColumn <= 'J'; firstColumn++) {
            System.out.print(firstColumn + " ");
            for (int column = 0; column < COLUMNS; column++) {
                System.out.print(this.battleship[row][column] + " ");
            }
            ++row;
            System.out.println();
        }
    }
}

class InvalidCoordinateException extends Exception {
    public InvalidCoordinateException(String message) {
        super(message);
    }
}