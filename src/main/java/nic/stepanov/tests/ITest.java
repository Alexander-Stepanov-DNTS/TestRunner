package nic.stepanov.tests;

import nic.stepanov.exceptions.TestFailedException;

public interface ITest {
    void runTest(boolean manyTests) throws TestFailedException;
}
