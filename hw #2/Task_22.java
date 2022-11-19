import java.util.LinkedList;

import resources.Operationable_21;
import resources.Operationable_22;
import resources.Variable;

public class Task_22 {
    
    private static final double K = 3;
    private static final double ACCURACY = Math.pow(10, -K);

    public static void main(String[] args) {
        LinkedList<Operationable_21> equations_1 = new LinkedList<>();
        equations_1.add((x, y, A, alpha_2, beta_2) -> Math.tan(x * y + A) - Math.pow(x, 2));
        equations_1.add((x, y, A, alpha_2, beta_2) -> Math.pow(x, 2) / alpha_2 + Math.pow(y, 2) / beta_2 - 1);

        LinkedList<Operationable_21> partialDevariative_1 = new LinkedList<>();
        partialDevariative_1.add((x, y, A, alpha_2, beta_2) -> y * (Math.pow(Math.tan(x * y + A), 2) + 1) - 2 * x);
        partialDevariative_1.add((x, y, A, alpha_2, beta_2) -> x * (Math.pow(Math.tan(x * y + A), 2)) + 1);
        partialDevariative_1.add((x, y, A, alpha_2, beta_2) -> 2 * alpha_2 * x);
        partialDevariative_1.add((x, y, A, alpha_2, beta_2) -> 2 * beta_2 * y);

        LinkedList<Variable> values_1 = new LinkedList<>();
        values_1.add(new Variable(0.8, 0.5, 0.2, 0.6, 2));
        values_1.add(new Variable(-0.3, 0.6, 0.4, 0.8, 2));
        values_1.add(new Variable(1, 0.5, 0.3, 0.2, 3));
        values_1.add(new Variable(0.6, 0.6, 0, 0.6, 2));

        LinkedList<Operationable_22> equations_2 = new LinkedList<>();
        equations_2.add((x1, x2) -> Math.pow(x1, 2) + Math.pow(x2, 2) - 2);
        equations_2.add((x1, x2) -> Math.exp(x1 - 1) + Math.pow(x2, 3) - 2);

        LinkedList<Operationable_22> partialDevariative_2 = new LinkedList<>();
        partialDevariative_2.add((x1, x2) -> 2 * x1);
        partialDevariative_2.add((x1, x2) -> 2 * x2);
        partialDevariative_2.add((x1, x2) -> Math.exp(x1 - 1));
        partialDevariative_2.add((x1, x2) -> 3 * Math.pow(x2, 2));

        Variable values_2 = new Variable(-0.5, 1);

        int i = 1;
        System.out.printf("задание 2.1.\n\n");
        for (Variable value : values_1) {
            System.out.printf("расчет для значений парамеров 2.1.%d:\n\n", i);
            doNewton_1(equations_1, partialDevariative_1, value);
            System.out.printf("\n");
            ++i;
        }

        System.out.printf("задание 2.2.\n\n");
        doNewton_2(equations_2, partialDevariative_2, values_2);
    }

    public static void doNewton_1(LinkedList<Operationable_21> f, LinkedList<Operationable_21> df, Variable values) {
        int k = 1;
        double x, y, sumOfSquares;

        double x1 = values.getX1();
        double y1 = values.getX2();
        double A = values.getA();
        double alpha_2 = values.getAlpha();
        double beta_2 = values.getBeta();

        do {
            x = x1;
            y = y1;

            double f1 = f.get(0).calculate(x, y, A, alpha_2, beta_2);
            double f2 = f.get(1).calculate(x, y, A, alpha_2, beta_2);
            double df1x = df.get(0).calculate(x, y, A, alpha_2, beta_2);
            double df1y = df.get(1).calculate(x, y, A, alpha_2, beta_2);
            double df2x = df.get(2).calculate(x, y, A, alpha_2, beta_2);
            double df2y = df.get(3).calculate(x, y, A, alpha_2, beta_2);

            double detA1 = (f1 * df1y) - (f2 * df2y);
            double detA2 = (f2 * df1x) - (f1 * df2x);
            double detJ = (df1x * df2y) - (df1y * df2x);

            x1 = x - (detA1 / detJ);
            y1 = y - (detA2 / detJ);

            sumOfSquares = 0;
            for (Operationable_21 equation : f) {
                sumOfSquares += Math.pow(equation.calculate(x1, y1, A, alpha_2, beta_2), 2);
            }

            System.out.printf("шаг %d. x = %f; y = %f\n", k, x1, y1);
            ++k;
        } while (Math.sqrt(sumOfSquares) > ACCURACY && Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) > ACCURACY);
    }

    public static void doNewton_2(LinkedList<Operationable_22> f, LinkedList<Operationable_22> df, Variable values) {
        int k = 1;
        double x1, x2, sumOfSquares;

        double x1_1 = values.getX1();
        double x2_1 = values.getX2();

        do {
            x1 = x1_1;
            x2 = x2_1;

            double f1 = f.get(0).calculate(x1, x2);
            double f2 = f.get(1).calculate(x1, x2);
            double df1x = df.get(0).calculate(x1, x2);
            double df1y = df.get(1).calculate(x1, x2);
            double df2x = df.get(2).calculate(x1, x2);
            double df2y = df.get(3).calculate(x1, x2);

            double detA1 = (f1 * df1y) - (f2 * df2y);
            double detA2 = (f2 * df1x) - (f1 * df2x);
            double detJ = (df1x * df2y) - (df1y * df2x);

            x1_1 = x1 - (detA1 / detJ);
            x2_1 = x2 - (detA2 / detJ);

            sumOfSquares = 0;
            for (Operationable_22 equation : f) {
                sumOfSquares += Math.pow(equation.calculate(x1_1, x2_1), 2);
            }

            System.out.printf("шаг %d. x1 = %f; x2 = %f\n", k, x1_1, x2_1);
            ++k;
        } while (Math.sqrt(sumOfSquares) > ACCURACY && Math.sqrt(Math.pow(x1_1 - x1, 2) + Math.pow(x2_1 - x2, 2)) > ACCURACY);
    }
}