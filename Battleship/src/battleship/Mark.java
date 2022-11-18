package battleship;

enum Mark {

    FOG_OF_WAR('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char MARK;

    Mark(char mark) {
        this.MARK = mark;
    }

    char getMARK() {
        return MARK;
    }
}