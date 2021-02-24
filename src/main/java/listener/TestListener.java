package listener;


import org.apache.log4j.Logger;
import org.testng.*;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger LOG = Logger.getLogger(TestListener.class);
    private final List<String> passed = new ArrayList<>();
    private final List<String> failed = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        System.out.println(String.format(">Execute test %s", testName));
    }

    public void OnFinish(ITestContext iTestContext) {
        LOG.info("ON FINISH");
        LOG.info(String.format("PASSED: %s, FAILED: %s", passed.size(), failed.size()));
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

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
