import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProblemTwo {
    private static final double ACCURACY = Math.pow(10, -8);

    public static void main(String[] args) {
        Map<LinkedList<Operationable>,  Double> equations = new LinkedHashMap<>();

        equations.put(new LinkedList<Operationable>(List.of(x -> Math.sin(x), x -> Math.cos(x))), (double) 3);
        equations.put(new LinkedList<Operationable>(List.of(x -> Math.log(x) - 1, x -> 1 / x)), (double) 3);

        char letter = 'a';
        for (Map.Entry<LinkedList<Operationable>,  Double> equation : equations.entrySet()) {
            System.out.println("уравнение " + letter + ".\n");
            doNewton(equation.getKey().get(0), equation.getKey().get(1), equation.getValue());
            System.out.println();
            ++letter;
        }
    }
    
    private static void doNewton(Operationable fx, Operationable dfx, double x0) {
        System.out.println("метод ньютона:\n");
        int i = 1;
        double x1 = x0 - fx.calculate(x0) / dfx.calculate(x0);
        while(Math.abs(x0 - x1) > ACCURACY) {
            System.out.println("шаг " + i + ". x = " + x1);
            x0 = x1;
            x1 = x0 - fx.calculate(x0) / dfx.calculate(x0);
            ++i;
        }
        System.out.println("шаг " + i + ". x = " + x1);
    }
}

interface Operationable {
    double calculate(double x);
}