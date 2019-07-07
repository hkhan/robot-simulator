package simulator

import spock.lang.Specification
import spock.lang.Unroll

import static simulator.Direction.NORTH
import static simulator.Direction.EAST
import static simulator.Direction.SOUTH
import static simulator.Direction.WEST

class RobotSpec extends Specification {

    @Unroll
    def 'robot is placed on the board with position: x: #x, y: #y and direction: #direction'() {
        when: 'the robot is placed'
        def robot = new Robot(new Board())
        robot.place(new Point(x, y), direction)

        then: 'robot is present on the board'
        robot.position == new Point(x, y)
        robot.direction == direction

        where:
        direction | x  | y
        NORTH     | 0  | 0
        EAST      | 0  | 4
        SOUTH     | 1  | 1
        WEST      | 4  | 3
    }

    @Unroll
    def 'robot is not placed on the board with invalid position: x: #x, y: #y'() {
        given: 'a robot'
        def robot = new Robot(new Board())

        when: 'the robot is placed at an invalid position'
        robot.place(new Point(x, y), NORTH)

        then: 'robot is not present on the board'
        robot.position == null
        robot.direction == null

        where:
        x  | y
        5  | 0
        0  | 5
        -1 | 0
        1  | -1
    }

    @Unroll
    def 'robot moves in the correct direction'() {
        given: 'a robot placed on the board'
        def robot = new Robot(new Board())
        robot.place(initial, direction)

        when: 'the robot is moved'
        robot.move()

        then: 'robot position is updated in the same direction'
        robot.position == expected
        robot.direction == direction

        where:
        direction | initial               | expected
        NORTH     | new Point(1, 1) | new Point(1, 2)
        EAST      | new Point(1, 1) | new Point(2, 1)
        SOUTH     | new Point(1, 1) | new Point(1, 0)
        WEST      | new Point(1, 1) | new Point(0, 1)
    }

    @Unroll
    def 'robot rotates to the left: #initial -> #expected'() {
        given: 'a robot placed on the board'
        def robot = new Robot(new Board())
        robot.place(new Point(0, 0), initial)

        when: 'the robot is rotated in the Left direction'
        robot.rotateLeft()

        then: 'robot is facing correct direction'
        robot.direction == expected

        and: 'location remains the same'
        robot.position == new Point(0, 0)

        where:
        initial | expected
        NORTH   | WEST
        WEST    | SOUTH
        SOUTH   | EAST
        EAST    | NORTH
    }

    @Unroll
    def 'robot rotates to the right: #initial -> #expected'() {
        given: 'a robot placed on the board'
        def robot = new Robot(new Board())
        robot.place(new Point(0, 0), initial)

        when: 'the robot is rotated in the Right direction'
        robot.rotateRight()

        then: 'robot is facing correct direction'
        robot.direction == expected

        and: 'location remains the same'
        robot.position == new Point(0, 0)

        where:
        initial | expected
        NORTH   | EAST
        EAST    | SOUTH
        SOUTH   | WEST
        WEST    | NORTH
    }

    def 'does not rotate Right when robot is not placed'() {
        when: 'a robot which is not on the table is rotated'
        def robot = new Robot(new Board())
        robot.rotateRight()

        then: 'robot has no position and direction'
        robot.position == null
        robot.direction == null
    }

    def 'does not rotate Left when robot is not placed'() {
        when: 'a robot which is not on the table is rotated'
        def robot = new Robot(new Board())
        robot.rotateLeft()

        then: 'robot has no position and direction'
        robot.position == null
        robot.direction == null
    }

    def 'robot does not move when not placed'() {
        when: 'a robot which is not on the table is rotated'
        def robot = new Robot(new Board())
        robot.move()

        then: 'robot has no position and direction'
        robot.position == null
        robot.direction == null
    }

    def 'robot generates report'() {
        when: 'a report command is executed'
        def robot = new Robot(new Board())
        robot.place(new Point(0, 0), NORTH)
        robot.report()

        then: 'robot returns a collection of report outputs'
        robot.getOutputs() == ['0,0,NORTH']
    }

    def 'robot does not generates report when not placed'() {
        when: 'a report command is executed'
        def robot = new Robot(new Board())
        robot.report()

        then: 'robot returns no outputs'
        robot.getOutputs().empty
    }


    def 'robot does not move off the board'() {
        given: 'a robot is placed on the board'
        def robot = new Robot(new Board())
        robot.place(new Point(0,0), WEST)

        when: 'the robot moves off the board'
        robot.move()

        then: 'robot ignores the invalid command'
        robot.position == new Point(0, 0)
        robot.direction == WEST
    }

}
