package simulator;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static simulator.Direction.EAST;
import static simulator.Direction.NORTH;
import static simulator.Direction.SOUTH;
import static simulator.Direction.WEST;

@Data
public class Robot {

    private static final List<Direction> DIRECTIONS = Arrays.asList(NORTH, EAST, SOUTH, WEST);

    private final Board board;
    private Point position;
    private Direction direction;
    private List<String> outputs = new ArrayList<>();

    void place(Point point, Direction direction) {
        if (board.contains(point)) {
            this.position = point;
            this.direction = direction;
        }
    }

    void rotateLeft() {
        if (position != null) {
            int index = DIRECTIONS.indexOf(direction);
            Collections.rotate(DIRECTIONS, 1);
            direction = DIRECTIONS.get(index);
        }
    }

    void rotateRight() {
        if (position != null) {
            int index = DIRECTIONS.indexOf(direction);
            Collections.rotate(DIRECTIONS, -1);
            direction = DIRECTIONS.get(index);
        }

    }

    void move() {
        if (position != null) {
            Point newPosition = getNewPosition();
            if (board.contains(newPosition)) {
                position = newPosition;
            }
        }
    }

    void report() {
        if (position != null) {
            outputs.add(position.getX() + "," + position.getY() + "," + direction);
        }
    }

    List<String> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }

    private Point getNewPosition() {
        switch (direction) {
            case NORTH:
                return new Point(position.getX(), position.getY() + 1);
            case EAST:
                return new Point(position.getX() + 1, position.getY());
            case SOUTH:
                return new Point(position.getX(), position.getY() - 1);
            case WEST:
                return new Point(position.getX() - 1, position.getY());
            default:
                return position;
        }
    }

}
