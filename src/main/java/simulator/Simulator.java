package simulator;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Simulator {

    public List<String> execute(String filename) {

        final Robot robot = new Robot(new Board());

        instructionsFrom(filename)
                .map(InstructionFactory::of)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(command -> command.execute(robot));

        return robot.getOutputs();
    }

    private Stream<String> instructionsFrom(String filename) {

        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            Path path = Paths.get(Objects.requireNonNull(url).toURI());
            return Files.readAllLines(path, StandardCharsets.UTF_8).stream();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to load file: " + filename);
        }

    }
}
