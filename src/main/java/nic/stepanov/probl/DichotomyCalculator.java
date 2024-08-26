package nic.stepanov.probl;

public class DichotomyCalculator {
    private double a;
    private double b;
    private double x1;
    private double x2;
    private final double eps = 0.1;

    public void initialSetup(double initialA, double initialB) {
        a = initialA;
        b = initialB;
        x1 = 0.0;
        x2 = 0.0;
        printIntermediateResults();
    }

    public void performDichotomy() {
        double x = (a + b) / 2;
        x1 = x - eps / 2;
        x2 = x + eps / 2;
        printIntermediateResults();
    }

    public void updateBoundaryA() {
        if (function(x1) > function(x2)) {
            a = x1;
        }
        printIntermediateResults();
    }

    public void updateBoundaryB() {
        if (function(x1) < function(x2)) {
            b = x2;
        }
        printIntermediateResults();
    }

    public boolean checkConvergence() {
        return Math.abs(a - b) <= 2 * eps + eps / 100;
    }

    private double function(double x) {
        return Math.pow(x - 2.5, 2);
    }

    public void printIntermediateResults() {
        System.out.println("a = " + a);
        System.out.println("x1 = " + x1);
        System.out.println("x2 = " + x2);
        System.out.println("b = " + b);
        System.out.println("f(a) = " + function(a));
        System.out.println("f(x1) = " + function(x1));
        System.out.println("f(x2) = " + function(x2));
        System.out.println("f(b) = " + function(b));
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getEps() {
        return eps;
    }
}