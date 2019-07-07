package simulator

import spock.lang.Specification

class SimulatorSpec extends Specification {

    def 'instructions are executed and correct output is produced'() {
        when:
        def result = new Simulator().execute('valid_instructions.txt')

        then:
        result == ['0,1,NORTH', '0,0,WEST', '3,3,NORTH']
    }

    def 'invalid instructions are ignored and robot does not fall off the board'() {
        when:
        def result = new Simulator().execute('falling_off_instructions.txt')

        then:
        result == ['0,1,NORTH']
    }

    def 'exception is thrown when instructions file is not found'() {
        when:
        new Simulator().execute('invalid_file')

        then:
        thrown IllegalArgumentException
    }

}
