package nic.stepanov.tests2;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.dichotomy.DichotomyCalculator;

public interface ITest {
    int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException;
}
