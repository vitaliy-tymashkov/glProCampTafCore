import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.annotations.Test;

public class TestExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(TestExecutor.class.getName());
    private static final String SUITE_DIR = "src/main/resources/";

    @Test(testName = "TEST EXECUTOR")
    public void executeAllTestSuites() {
        try (Stream<Path> walk = Files.walk(Paths.get(SUITE_DIR))) {
            List<String> suites = walk
                    .filter(Files::isRegularFile)
                    .filter(name -> name.getFileName().toString().endsWith("Test.xml"))
                    .map(Path::toString)
                    .collect(Collectors.toList());

            TestNG testNg = new TestNG();
            testNg.setSuiteThreadPoolSize(suites.size());
            testNg.setTestSuites(suites);
            testNg.run();
        } catch (IOException e) {
            LOG.error("fail to load test suite conf", e);
        }
    }
}