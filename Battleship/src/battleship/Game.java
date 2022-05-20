package battleship;

class Game {
    
    private final Player PLAYER_1;
    private final Player PLAYER_2;

    /**
    This is the Game class constructor
     */

    Game (Player player1, Player player2) {
        this.PLAYER_1 = player1;
        this.PLAYER_2 = player2;
    }

    /**
     This method calculate all PLAYER_1's and PLAYER_2's not sunken ships' cells
     */

    private int getAllNotSunkenShipsCells() {
        return PLAYER_1.getNotSunkenOpponentShipsCells() + PLAYER_2.getNotSunkenOpponentShipsCells();
    }

    /**
     This method starts and stops the game
     */

    private void playGame() {

        PLAYER_1.setAllNotSunkenOpponentShipsCells();
        PLAYER_2.setAllNotSunkenOpponentShipsCells();

        while (getAllNotSunkenShipsCells() != 0) {
            PLAYER_1.shootTheEnemy(PLAYER_2.getBATTLESHIP(), 1);
            PLAYER_2.shootTheEnemy(PLAYER_1.getBATTLESHIP(),2);
        }

    }

    public static void main(String[] args) {
        // Write your code here
        GameField battleField1 = new GameField();
        Player player1 = new Player(battleField1);
        Player.printOpeningSentence(1);
        player1.placeShips();
        Player.passMoveToAnotherPlayer();
        GameField battleField2 = new GameField();
        Player player2 = new Player(battleField2);
        Player.printOpeningSentence(2);
        player2.placeShips();
        Game game = new Game(player1, player2);
        game.playGame();
    }
}