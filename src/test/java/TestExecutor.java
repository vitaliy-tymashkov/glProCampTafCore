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
    private static final String TEST_XML_NAME_TEMPLATE = "Test.xml";

    @Test(testName = "TEST EXECUTOR LAUNCHER")
    public void executeAllTestSuites() {
        try (Stream<Path> pathStream = Files.walk(Paths.get(SUITE_DIR))) {
            TestNG testNg = prepareTestNgRun(getAllTestSuites(pathStream));
            testNg.run();
        } catch (IOException e) {
            LOG.error("Fail to load test suites", e);
        }
    }

    private TestNG prepareTestNgRun(List<String> suites) {
        TestNG testNg = new TestNG();
        testNg.setSuiteThreadPoolSize(suites.size());
        testNg.setTestSuites(suites);
        return testNg;
    }

    private List<String> getAllTestSuites(Stream<Path> pathStream) {
        return pathStream
                .filter(Files::isRegularFile)
                .filter(name -> name.getFileName().toString().endsWith(TEST_XML_NAME_TEMPLATE))
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
