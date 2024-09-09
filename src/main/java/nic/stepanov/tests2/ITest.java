package nic.stepanov.tests2;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.probl.DichotomyCalculator;

public interface ITest {
    int runTest(boolean manyTests, DichotomyCalculator calculator) throws TestFailedException;
}
