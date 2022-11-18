package battleship;

import java.io.IOException;
import java.util.Scanner;

class Player {

    private final static Scanner SCANNER = new Scanner(System.in);
    private final GameField BATTLESHIP;
    private int notSunkenOpponentShipsCells;

    Player(GameField battleship) {
        this.BATTLESHIP = battleship;
    }

    GameField getBATTLESHIP() {
        return BATTLESHIP;
    }

    int getNotSunkenOpponentShipsCells() {
        return notSunkenOpponentShipsCells;
    }

    void setAllNotSunkenOpponentShipsCells() {
        for (Ship s : Ship.values()) {
            this.notSunkenOpponentShipsCells += s.getLENGTH();
        }
    }

    /**
     * This method reduces the number of opponent's cells denoted as "O" on the battlefield
     */

    void reduceNotSunkenOpponentShipsCellsNumber(int row, int column, char[][] battlefield) {
        if (battlefield[row][column] == Mark.SHIP.getMARK()) {
            --this.notSunkenOpponentShipsCells;
        }
    }

    /**
     * This method prints message if it is a player's turn to place the ships
     */

    static void printOpeningSentence(int playerNumber) {
        System.out.printf("Player %d, place your ships on the game field\n\n", playerNumber);
    }

    /**
     * This method passes the move to another player
     */

    static void passMoveToAnotherPlayer() {
        String message = "Press Enter and pass the move to another player";
        System.out.println(message);
        try {
            while (System.in.read() != '\n') {
                System.out.println(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method prints message if it is a player's turn to denote the opponent's cell
     */

    static void printYourTurnMessage(int playerNumber) {
        System.out.printf("Player %d, it's your turn:\n\n", playerNumber);
    }

    /**
     * This method places the player's ships on the field
     */

    void placeShips() {
        BATTLESHIP.denoteCell(Mark.FOG_OF_WAR.getMARK());
        BATTLESHIP.printCoveredField();
        for (Ship ship : Ship.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", ship.getNAME(), ship.getLENGTH());
            ShipCoordinate firstCoordinate = new ShipCoordinate();
            ShipCoordinate lastCoordinate = new ShipCoordinate();
            ShipPosition shipPosition = new ShipPosition(ship);
            boolean isCorrectShipPlace = false;
            while (!isCorrectShipPlace) {
                String coordinateStringInput = SCANNER.nextLine();
                ShipCoordinate.processUserInputToArrayOfCoordinates(coordinateStringInput);
                firstCoordinate.setColumn(ShipCoordinate.getArrayOfCoordinates()[0]);
                firstCoordinate.setRow(ShipCoordinate.getArrayOfCoordinates()[0]);
                lastCoordinate.setColumn(ShipCoordinate.getArrayOfCoordinates()[1]);
                lastCoordinate.setRow(ShipCoordinate.getArrayOfCoordinates()[1]);
                try {
                    shipPosition.checkShipLocationCorrectness(firstCoordinate, lastCoordinate);
                    shipPosition.checkShipLengthCorrectness(firstCoordinate, lastCoordinate);
                    shipPosition.checkIfAnyShipNear(firstCoordinate, lastCoordinate, BATTLESHIP.getBATTLEFIELD());
                    isCorrectShipPlace = true;
                } catch (InvalidCoordinateException e) {
                    System.out.println();
                    System.out.println(e.getMessage());
                }
            }
            System.out.println();
            BATTLESHIP.denoteCell(Mark.SHIP.getMARK(), firstCoordinate, lastCoordinate);
            BATTLESHIP.printCoveredField();
        }
    }

    /**
     * This method shoots the enemy cell
     */

    void shootTheEnemy(GameField opponentGameField, int playerNumber) {
        if (getNotSunkenOpponentShipsCells() != 0) {
            passMoveToAnotherPlayer();
            opponentGameField.printUncoveredField();
            BATTLESHIP.printCoveredField();
            printYourTurnMessage(playerNumber);
            ShipCoordinate coordinate = new ShipCoordinate();
            ShipPosition shipPosition = new ShipPosition();
            String coordinateStringInput = SCANNER.next();
            coordinate.setColumn(coordinateStringInput);
            coordinate.setRow(coordinateStringInput);
            try {
                shipPosition.checkCoordinateCorrectness(coordinate);
                reduceNotSunkenOpponentShipsCellsNumber(coordinate.getRow(), coordinate.getColumn(), opponentGameField.getBATTLEFIELD());
                getBATTLESHIP().makeShot(coordinate.getRow(), coordinate.getColumn(), opponentGameField.getBATTLEFIELD());
                getBATTLESHIP().printShootingMessage(getNotSunkenOpponentShipsCells(), coordinate.getRow(), coordinate.getColumn(), opponentGameField.getBATTLEFIELD());
            } catch (InvalidCoordinateException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }
}