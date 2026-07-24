package com.qaframework.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries a failed test automatically before marking it as a final failure.
 *
 * Why this exists: this framework runs against a live public demo site
 * (opensource-demo.orangehrmlive.com). Public demo instances can be slow or
 * rate-limited for automated/cloud-datacenter traffic (e.g. GitHub Actions
 * runners), causing occasional transient timeouts that have nothing to do
 * with the test logic itself. Retrying is the standard, honest way to
 * absorb that third-party flakiness without weakening the actual waits.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            System.out.println("Retrying test '" + result.getName() + "' — attempt " + (retryCount + 1));
            return true;
        }
        return false;
    }
}
