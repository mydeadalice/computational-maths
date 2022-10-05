import java.util.LinkedHashMap;
import java.util.Map;

public class Task_11 {

    private static final double ACCURACY = Math.pow(10, -4);
    private static final double A = 3;
    private static final double N = 2;

    public static void main(String[] args) {
        Map<Operationable,  Variable> equations = new LinkedHashMap<>();

        equations.put(x -> Math.sin(x) - 2 * Math.pow(x, 2) + 0.5, new Variable(0, 1, 1));
        equations.put(x -> Math.pow(x, N) - A, new Variable(1, 2, 2));
        equations.put(x -> Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x) + 0.1, new Variable(0, 1, 0));
        equations.put(x -> Math.pow(x, 6) - 5 * Math.pow(x, 3) - 2, new Variable(1, 2, 2));
        equations.put(x -> Math.log(x) - (1 / (1 + Math.pow(x, 2))), new Variable(1, 2, 1));
        equations.put(x -> Math.pow(3, x) - 5 * Math.pow(x, 2) + 1, new Variable(0, 1, 1));
        
        char letter = 'a';
        for (Map.Entry<Operationable,  Variable> equation : equations.entrySet()) {
            System.out.println("уравнение " + letter + ".\n");
            doDychotomy(equation.getKey(), equation.getValue().getA(), equation.getValue().getB());
            doNewton(equation.getKey(), equation.getValue().getX0());
            ++letter;
        }
    }
    
    private static void doDychotomy (Operationable equation, double a, double b) {
        System.out.println("метод дихотомии:\n");
        int i = 1;
        double c = (a + b) / 2;
        if (equation.calculate(a) * equation.calculate(b) > 0) {
            System.out.println("на данном отрезке нет корней.\n");
        } else {
            while ((b - a) > ACCURACY) {
                if (equation.calculate(a) * equation.calculate(c) < 0) {
                    b = c;
                } else {
                    a = c;
                }
                System.out.println("шаг " + i + ". x = " + c);
                c = (a + b) / 2;
                ++i;
            }
            System.out.println();
        }
    }

    private static void doNewton(Operationable equation, double xn) {
        System.out.println("метод ньютона:\n");
        int i = 1;
        double x0 = xn;
        double x1, increment, fx, dfx, approximation;
        do {
            increment = Math.pow(ACCURACY, 2);
            fx = equation.calculate(x0);
            dfx = (equation.calculate(x0 + increment) - fx) / increment;
            x1  = x0 - fx / dfx;
            approximation = Math.abs(x0 - x1);
            x0 = x1;
            System.out.println("шаг " + i + ". x = " + x1);
            ++i;
        } while(approximation > ACCURACY);
        System.out.println();
    }
}

class Variable {
    private double a;
    private double b;
    private double x0;

    public Variable(double a, double b, double x0) {
        this.a = a;
        this.b = b;
        this.x0 = x0;
    }

    public double getA() {
        return this.a;
    }

    public double getB() {
        return this.b;
    }

    public double getX0() {
        return this.x0;
    }
}

interface Operationable {
    double calculate(double x);
}