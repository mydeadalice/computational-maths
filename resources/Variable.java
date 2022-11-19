package resources;

public class Variable {
    private double x1;
    private double x2;
    private double a;
    private double b;
    private double alpha;
    private double beta;

    public Variable(double a, double b, double x1) {
        this.a = a;
        this.b = b;
        this.x1 = x1;
    }

    public Variable(double x1, double x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    public Variable(double x1, double x2, double a, double alpha, double beta) {
        this.x1 = x1;
        this.x2 = x2;
        this.a = a;
        this.alpha = 1 / alpha;
        this.beta = 1 / beta;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getA() {
        return this.a;
    }

    public double getB() {
        return this.b;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public double getBeta() {
        return this.beta;
    }
}
