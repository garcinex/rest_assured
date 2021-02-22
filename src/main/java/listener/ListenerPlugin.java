package listener;


import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import utils.TestInitialization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

//public class ListenerPlugin implements ActionListener {
public class ListenerPlugin implements ConcurrentEventListener {

    public void onTestRunStarted(TestRunStarted testRunStarted) {
        TestInitialization.init();
        System.out.println("TEST RUN STARTED AT: " + LocalDateTime.now().toString());
    }

    public void onTestCaseStarted(TestCaseStarted testCaseStarted) {
        System.out.println("TEST CASE STARTED: " + testCaseStarted.getTestCase().getName());
    }

    public void onTestRunFinished(TestRunFinished testRunFinished) {
        System.out.println("TEST RUN FINISHED AT: " + LocalDateTime.now().toString());
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunStarted.class, this::onTestRunStarted);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        eventPublisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}
