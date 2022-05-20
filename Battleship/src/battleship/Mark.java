package battleship;

enum Mark {
    FOG_OF_WAR('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char MARK;

    /**
     * This is the Mark enumeration constructor
     */

    Mark(char mark) {
        this.MARK = mark;
    }

    /**
     * This is the mark getter
     */

    char getMARK() {
        return MARK;
    }
}