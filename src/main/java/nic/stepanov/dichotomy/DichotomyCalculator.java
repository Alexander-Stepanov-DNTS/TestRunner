package nic.stepanov.dichotomy;

public class DichotomyCalculator {
    private ResultOfDichotomy result;
    private UpdateListener updateListener;

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public ResultOfDichotomy initialSetup(double initialA, double initialB, double eps) {
        if(initialA == 0) {return null; }
        result = new ResultOfDichotomy(initialA, initialB, 0.0, 0.0, eps);
        notifyUpdate();
        printIntermediateResults();
        return result;
    }

    public ResultOfDichotomy performDichotomy() {
        double x = (result.getA() + result.getB()) / 2;
        result.setX1(x - result.getEps() / 2);
        result.setX2(x + result.getEps() / 2);
        notifyUpdate();
        printIntermediateResults();
        return result;
    }

    public ResultOfDichotomy updateBoundaryA() {
        if (function(result.getX1()) > function(result.getX2())) {
            result.setA(result.getX1());
        }
        notifyUpdate();
        printIntermediateResults();
        return result;
    }

    public ResultOfDichotomy updateBoundaryB() {
        if (function(result.getX1()) < function(result.getX2())) {
            result.setB(result.getX2());
        }
        notifyUpdate();
        printIntermediateResults();
        return result;
    }

    public boolean checkConvergence() {
        return Math.abs(result.getA() - result.getB()) <= 2 * result.getEps() + result.getEps() / 100;
    }

    public double function(double x) {
        return Math.pow(x - 2.5, 2);
    }

    private void notifyUpdate() {
        if (updateListener != null) {
            updateListener.onUpdate();
        }
    }

    public void printIntermediateResults() {
        System.out.println("a = " + result.getA());
        System.out.println("x1 = " + result.getX1());
        System.out.println("x2 = " + result.getX2());
        System.out.println("b = " + result.getB());
        System.out.println("f(a) = " + function(result.getA()));
        System.out.println("f(x1) = " + function(result.getX1()));
        System.out.println("f(x2) = " + function(result.getX2()));
        System.out.println("f(b) = " + function(result.getB()));
    }

    public double getA() {
        return result.getA();
    }

    public double getB() {
        return result.getB();
    }

    public double getX1() {
        return result.getX1();
    }

    public double getX2() {
        return result.getX2();
    }

    public double getEps() {
        return result.getEps();
    }

    public ResultOfDichotomy getResult() {
        return result;
    }
}
