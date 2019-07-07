package simulator;

class Board {

    private static final int ROWS = 5;
    private static final int COLUMNS = 5;

    boolean contains(Point point) {
        int x = point.getX();
        int y = point.getY();
        return x >= 0 && x < ROWS && y >= 0 && y < COLUMNS;
    }

}
