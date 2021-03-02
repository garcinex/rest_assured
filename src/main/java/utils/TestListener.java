package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import utils.TestInitialization;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestInitialization.class);
    private static final TestReport testReport = new TestReport();
    private final List<String> passed = new ArrayList<>();
    private final List<String> failed = new ArrayList<>();
    private final List<String> skipped = new ArrayList<>();

    @Override
    public void onStart(ITestContext iTestContext) {
        TestInitialization.init();
        LOG.info("TEST RUN STARTED AT: " + LocalDateTime.now().toString());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testClass = iTestResult.getTestClass().getName();
        String testName = iTestResult.getName();
        LOG.info(String.format(">>> Execute test: %s <<<", testName));
        testReport.createTest(testClass, testName);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOG.info("TEST RUN FINISHED AT: " + LocalDateTime.now().toString());
        LOG.info(String.format("PASSED: %s, FAILED: %s, SKIPPED: %s", passed.size(), failed.size(), skipped.size()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        passed.add(testName);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        failed.add(testName);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        skipped.add(testName);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

}
