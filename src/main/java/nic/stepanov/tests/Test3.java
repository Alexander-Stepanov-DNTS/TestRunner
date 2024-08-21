package nic.stepanov.tests;

import nic.stepanov.models.Result;

public class Test3 implements ITest {
    @Override
    public Result runTest() {
        Result result = new Result("Test3");

        result.addDataPoint(1, 1);
        result.addDataPoint(2, 4);
        result.addDataPoint(3, 9);
        result.addDataPoint(4, 16);
        result.addDataPoint(5, 25);

        return result;
    }
}