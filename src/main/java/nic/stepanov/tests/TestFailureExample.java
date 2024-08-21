package nic.stepanov.tests;

import nic.stepanov.exceptions.TestFailedException;
import nic.stepanov.models.Result;

public class TestFailureExample implements ITest {
    public Result runTest() throws TestFailedException {
        throw new TestFailedException("Ошибка: тест завершился неудачно.");
    }
}