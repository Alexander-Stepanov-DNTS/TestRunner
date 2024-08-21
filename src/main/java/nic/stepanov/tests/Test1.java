package nic.stepanov.tests;

import nic.stepanov.models.Result;

public class Test1 implements ITest {
    @Override
    public Result runTest() {
        Result result = new Result("Test1");

        result.addDataPoint(1, 5);
        result.addDataPoint(2, 10);
        result.addDataPoint(3, 7);
        result.addDataPoint(4, 3);

        return result;
    }
}