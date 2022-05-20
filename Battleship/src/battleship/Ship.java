package battleship;

enum Ship {
    AIRCRAFT_CARRIER_SIZE("Aircraft Carrier", 5),
    BATTLESHIP_SIZE("Battleship", 4),
    SUBMARINE_SIZE("Submarine", 3),
    CRUISER_SIZE("Cruiser", 3),
    DESTROYER_SIZE("Destroyer", 2);

    private final String NAME;
    private final int LENGTH;

    /**
     * This is the Ship constructor
     */

    Ship(String name, int length) {
        this.NAME = name;
        this.LENGTH = length;
    }

    /**
     * This is the LENGTH getter
     */

    int getLENGTH() {
        return LENGTH;
    }

    /**
     * This is the NAME getter
     */

    String getNAME() {
        return NAME;
    }
}