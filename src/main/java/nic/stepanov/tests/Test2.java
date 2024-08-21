package nic.stepanov.tests;

import nic.stepanov.models.Result;

public class Test2 implements ITest {
    @Override
    public Result runTest() {
        Result result = new Result("Test2");

        result.addDataPoint(0, 0);
        result.addDataPoint(1, 2);
        result.addDataPoint(2, 4);
        result.addDataPoint(3, 6);
        result.addDataPoint(4, 8);
        result.addDataPoint(5, 10);

        return result;
    }
}