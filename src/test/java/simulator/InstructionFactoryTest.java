package simulator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static simulator.Direction.NORTH;

@RunWith(JUnitParamsRunner.class)
public class InstructionFactoryTest {

    private Robot robot = Mockito.mock(Robot.class);

    @Test
    public void returns_command_for_place_instruction() {
        Optional<InstructionCommand> maybeCommand = InstructionFactory.of("PLACE 0,0,NORTH");

        assertThat(maybeCommand).hasValueSatisfying(command -> {
            Robot result = command.execute(robot);
            assertThat(result).isNotNull();
            verify(robot).place(new Point(0, 0), NORTH);
        });
    }

    public static Object[][] getParameters() {
        return new Object[][] {
                new Object[] {"PLACE|PLACE 0, |PLACE00NORTH|PLACE 0  ,0, SOUTH| PLACE 0,0,S|MOVEV|RIGH|LEFTT|MOV|REPORTT"}
        };
    }

    @Test
    @Parameters(method = "getParameters")
    public void does_not_return_command_for_invalid_instruction(String instruction) {
        Optional<InstructionCommand> maybeCommand = InstructionFactory.of(instruction);

        assertThat(maybeCommand).isNotPresent();
    }

    @Test
    public void returns_command_for_move_instruction() {
        Optional<InstructionCommand> maybeCommand = InstructionFactory.of("MOVE");

        assertThat(maybeCommand).hasValueSatisfying(command -> {
            Robot result = command.execute(robot);
            assertThat(result).isNotNull();
            verify(robot).move();
        });
    }

    @Test
    public void returns_command_for_right_instruction() {
        Optional<InstructionCommand> maybeCommand = InstructionFactory.of("RIGHT");

        assertThat(maybeCommand).hasValueSatisfying(command -> {
            Robot result = command.execute(robot);
            assertThat(result).isNotNull();
            verify(robot).rotateRight();
        });
    }

    @Test
    public void returns_command_for_left_instruction() {
        Optional<InstructionCommand> maybeCommand = InstructionFactory.of("LEFT");

        assertThat(maybeCommand).hasValueSatisfying(command -> {
            Robot result = command.execute(robot);
            assertThat(result).isNotNull();
            verify(robot).rotateLeft();
        });
    }

    @Test
    public void returns_command_for_report_instruction() {
        Optional<InstructionCommand> maybeCommand = InstructionFactory.of("REPORT");

        assertThat(maybeCommand).hasValueSatisfying(command -> {
            Robot result = command.execute(robot);
            assertThat(result).isNotNull();
            verify(robot).report();
        });
    }

}