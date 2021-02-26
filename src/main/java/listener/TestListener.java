package listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import utils.TestInitialization;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestInitialization.class);
    private final List<String> passed = new ArrayList<>();
    private final List<String> failed = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        LOG.info(String.format(">Execute test %s", testName));
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOG.info("ON FINISH");
        LOG.info(String.format("PASSED: %s, FAILED: %s", passed.size(), failed.size()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        passed.add(testName);
        LOG.debug("SUCCESS!");
//        System.out.println("SUCCESS! println");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        failed.add(testName);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }
}
