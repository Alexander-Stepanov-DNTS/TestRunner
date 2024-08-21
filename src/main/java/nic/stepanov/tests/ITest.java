package nic.stepanov.tests;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.models.Result;

public interface ITest {
    Result runTest() throws TestFailedException;
}
