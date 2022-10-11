import java.util.LinkedList;

public class Task_221 {
    
    private static final double K = 3;
    private static final double ACCURACY = Math.pow(10, -K);

    public static void main(String[] args) {
        LinkedList<Variable> values = new LinkedList<>();

        values.add(new Variable(0.2, 1 / 0.6, 1 / 2));
        values.add(new Variable(0.4, 1 / 0.8, 1 / 2));
        values.add(new Variable(0.3, 1 / 0.2, 1 / 3));
        values.add(new Variable(0, 1 / 0.6, 1 / 2));
    }
}

class Variable {
    private double A;
    private double alfa_2;
    private double beta_2;

    public Variable(double A, double alfa_2, double beta_2) {
        this.A = A;
        this.alfa_2 = alfa_2;
        this.beta_2 = beta_2;
    }

    public double getA() {
        return this.A;
    }

    public double getAlfa_2() {
        return this.alfa_2;
    }

    public double getBeta_2() {
        return this.beta_2;
    }
}

interface Operationable {
    double calculate(double x);
}