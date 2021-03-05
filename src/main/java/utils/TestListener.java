package utils;


import annotiations.Xfail;
import javafx.beans.property.ObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import utils.TestInitialization;

import java.lang.annotation.Annotation;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        testReport.pass(testName);
    }

    private boolean isXfail(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Xfail.class) != null;
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        String reason = iTestResult.getThrowable().getMessage().replace("\n", " ");
//        failed.add(testName);
//        testReport.fail(testName, reason);
        Optional<Object> xfailOptional = getAnnotationFromTestResult(iTestResult, Xfail.class);

        if (isXfail(iTestResult)) {
            LOG.info("XFAIL: {}", testName);
            testReport.skip(testName, reason);
            iTestResult.setStatus(ITestResult.SKIP);
            iTestResult.setThrowable(null);

            if (xfailOptional.isPresent()) {
                Xfail xfail = (Xfail) xfailOptional.get();
                if (!"unassigned".equals(xfail.issue())) {
                    testReport.logXfailTestWithIssue(testName, xfail.issue());
                } else {
//                    testReport.logXfailTest(testName);
                }
            }
            iTestResult.getTestContext().getFailedTests().removeResult(iTestResult);
            iTestResult.getTestContext().getSkippedTests().addResult(iTestResult, iTestResult.getMethod());
        } else {
            LOG.info("FAILED: {}", testName);
            testReport.fail(testName, reason);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        String testName = iTestResult.getTestClass().getName() + "." + iTestResult.getName();
        String reason = iTestResult.getThrowable().getMessage().replace("\n", " ");
        skipped.add(testName);
        testReport.skip(testName, reason);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    private Optional<Object> getAnnotationFromTestResult(ITestResult iTestResult, Class<? extends Annotation> clazz) {
        return Optional.ofNullable(iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(clazz));
    }

}
