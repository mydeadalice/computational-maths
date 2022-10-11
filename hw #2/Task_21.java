import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Task_21 {

    private static final double A = 2;
    private static final double K = 3;
    private static final double ACCURACY = Math.pow(10, -K);

    public static void main(String[] args) {
        Map<LinkedList<Operationable>,  Double> equations = new LinkedHashMap<>();

        equations.put(new LinkedList<Operationable>(List.of(x -> Math.pow(x, 3) + Math.pow(x, 2) - x + 0.5, x -> 3 * Math.pow(x, 2) + 2 * x - 1)), (double) -2);
        equations.put(new LinkedList<Operationable>(List.of(x -> Math.pow(Math.E, x) / A - x - 1, x -> Math.pow(Math.E, x) / A - 1)), (double) -1);   //A > 0
        equations.put(new LinkedList<Operationable>(List.of(x -> Math.pow(x, 3) - 20 * x + 1, x -> 3 * Math.pow(x, 2) - 20)), (double) 0);
        equations.put(new LinkedList<Operationable>(List.of(x -> Math.pow(2, x) + Math.pow(x, 2) - 2, x -> Math.pow(2, x) * Math.log(2) + 2 * x)), (double) -1);
        equations.put(new LinkedList<Operationable>(List.of(x -> x * Math.log(x + 2) - 1 + Math.pow(x, 2), x -> 2 * x + x / (x + 2) + Math.log(x + 2))), (double) 1);
        equations.put(new LinkedList<Operationable>(List.of(x -> Math.pow(x, 3) / A - A * Math.cos(x), x -> 3 * Math.pow(x, 2) / A + A * Math.sin(x))), (double) 1);   //A > 1

        int i = 1;
        for (Map.Entry<LinkedList<Operationable>,  Double> equation : equations.entrySet()) {
            System.out.printf("уравнение №%d.\n\n", i);
            doSimpleIterations(equation.getKey().get(0), equation.getKey().get(1), equation.getValue());
            ++i;
        }
    }

    public static void searchX (Operationable equation) {
        LinkedList<Double> startingXs = new LinkedList<>();
        int a = -5, b = 5;
        for (int i = a; i < b; ++i) {
            if (equation.calculate(i) * equation.calculate(i + 1) < 0) {
                startingXs.add((double) i);
            }
        }
    }

    public static void doSimpleIterations (Operationable fx, Operationable dfx, Double x) {
        System.out.printf("метод простых итераций:\n\n");
        double lambda = 1 / dfx.calculate(x);
        double x0;
        int i = 1;
        
        do {
            x0 = x;
            x = x0 - lambda * fx.calculate(x0);
            System.out.printf("шаг %d. x = %f\n", i, x);
            ++i;
        } while (Math.abs(x - x0) > ACCURACY);

        System.out.printf("\n");
    }
}

interface Operationable {
    double calculate(double x);
}