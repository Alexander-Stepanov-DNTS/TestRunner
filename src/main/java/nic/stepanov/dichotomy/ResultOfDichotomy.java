package nic.stepanov.dichotomy;

public class ResultOfDichotomy {
    private double a;
    private double b;
    private double x1;
    private double x2;
    private final double eps;

    public ResultOfDichotomy(double a, double b, double x1, double x2, double eps) {
        this.a = a;
        this.b = b;
        this.x1 = x1;
        this.x2 = x2;
        this.eps = eps;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getEps() {
        return eps;
    }
    @Override
    public String toString() {
        return "Результат:\n" +
                "a=" + a + "\n" +
                "b=" + b + "\n" +
                "x1=" + x1 + "\n" +
                "x2=" + x2 + "\n" +
                "eps=" + eps +"\n" ;
    }

}
