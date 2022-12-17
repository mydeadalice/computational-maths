import java.util.Arrays;
import java.util.LinkedList;

import resources.Operationable;

public class Task_32 {
    
    private static final int L = 2;
    private static final int[] SEQUENCE_NUMBERS_OF_NORMS = {1, 2, 3};
    private static final int[] N = {2, 3, 4, 5, 10, 12, 15};
    private static Operationable ACCURACY = (n) -> Math.pow(10, -n);
    private static final String SIMPLE_ITERATION_NAME = "simple iteration";
    private static final String SEIDEL_NAME = "seidel";

    public static void main(String[] args) {
        LinkedList<double[][]> equations = new LinkedList<double[][]>();

        equations.add(new double[][] { { 12, -3, -1, 3, -31 }, { 5, 20, 9, 1, 90 }, { 6, -3, -21, -7, 119 }, { 8, -7, 3, -27, 71 } });
        equations.add(new double[][] { { 28, 9, -3, -7, -159 }, { -5, 21, -5, -3, 63 }, { -8, 1, -16, 5, -45 }, { 0, -2, 5, 8, 24 } });
        equations.add(new double[][] { { 21, 1, -8, 4, -119 }, { -9, -23, -2, 4, 79 }, { 7, -1, -17, 6, -24 }, { 8, 8, -4, -26, -52 } });
        equations.add(new double[][] { { 14, -4, -2, 3, 38 }, { -3, 23, -6, -9, -195 }, { -7, -8, 21, -5, -27 }, { -2, -2, 8, 18, 142 } });
    
        int i = 1;
        System.out.printf("задание 3.2.\n\n");
        for (double[][] equation : equations) {
            System.out.printf("решение слау #%d:\n\n", i);
            // Matr(equation);
            for (int n : N) {
                System.out.printf("для точности e^(-%d):\n\n", n);
                for (int normSequenceNumber : SEQUENCE_NUMBERS_OF_NORMS) {
                    System.out.printf("норма %d:\n\n", normSequenceNumber);
                    System.out.printf("метод простой итерации.\n\n");
                    doSimpleIterations(equation, n, normSequenceNumber);
                    // doCalculations(SIMPLE_ITERATION_NAME, equation, n, normSequenceNumber);
                    System.out.printf("метод зейделя.\n\n");
                    doCalculations(SEIDEL_NAME, equation, n, normSequenceNumber);
                }
            }
            ++i;
        }
    }

    public static double Matr(double[][] A, int k)
        {
            int n = A.length;
        double[] w0 = new double[n];
        double[] w = new double[n];
        double[] w0norm = new double[n];
        double summ = 0;
        double e, d, d0;
        int i, j;

        for (i = 0; i < n; i++) {
            w0[i] = 0;
        }
        w0[0] = 1;

        do
        {
            for (i = 0; i < n; i++) {
                summ = summ + w0[i] * w0[i];
            }
            d0 = Math.sqrt(summ);
            for (i=0;i<n;i++) {
                w0norm[i]=w0[i]/d0;
            }
            for (i=0;i<n;i++)
            {
                w[i]=0;
                for (j=0;j<n;j++) {
                    w[i]=w[i]+A[i][j]*w0norm[j];   
                }
            }
            summ=0;
            for (i=0;i<n;i++) {
                summ=summ+w[i]*w[i];
            }
            d=Math.sqrt(summ);
            e=Math.abs(d-d0);
            for (i=0;i<n;i++) {
                w0[i]=w[i];
            }
            summ=0;
        } while(e>ACCURACY.calculate(n));
        
        return 1. / d;

        // System.out.printf("%f\n",d);
        // for (i=0;i<n;i++) {
        //     System.out.printf("%f\n",w0norm[i]);   
        // }
        }
    
    public static void doSimpleIterations(double[][] matrix, int n, int normSequenceNumber) {
        double[] result = new double[matrix.length];
        double[] xn = new double[matrix.length];
        double tau = Matr(matrix, n);
        double norm;
        int k = 1;

        for (int i = 0; i < result.length; ++i)
        {
            result[i] = 1 - tau * matrix[i][i];
        }

        do {
            for (int i = 0; i < xn.length; ++i) {
                xn[i] = result[i];
                for (int j = 0; j < xn.length; ++j) {
                    xn[i] -= tau * (matrix[i][j] * result[j] - matrix[i][xn.length]);
                }
            }

            norm = getNorm(normSequenceNumber, result, xn);

            for (int i = 0; i < result.length; ++i) {
                result[i] = xn[i];
            }
            
            ++k;
        } while (norm > ACCURACY.calculate(n));

        System.out.printf("количество итераций: %d\n", k);
        System.out.printf("результат: [ ");
        for (int i = 0; i < result.length; ++i) {
            System.out.printf("%f ", result[i]);
        }
        System.out.printf("]\n\n");
    }

    public static void doCalculations(String methodName, double[][] matrix, int n, int normSequenceNumber) {
        double[] result = new double[matrix.length];
        double[] xn = new double[result.length];
        double norm;
        int k = 1;

        for (int i = 0; i < result.length; ++i)
        {
            result[i] = matrix[i][result.length] / matrix[i][i];
        }

        do {
            for (int i = 0; i < xn.length; ++i) {
                xn[i] = matrix[i][xn.length] / matrix[i][i];
                for (int j = 0; j < xn.length; ++j) {
                    switch(methodName) {
                        case "simple iteration":
                            if (i != j) {
                                xn[i] -= matrix[i][j] / matrix[i][i] * result[j];
                            }
                            break;
                        case "seidel":
                            if (j < i) {
                                xn[i] -= matrix[i][j] / matrix[i][i] * xn[j];
                            } else if (j > i) {
                                xn[i] -= matrix[i][j] / matrix[i][i] * result[j];
                            }
                            break;
                    }
                }
            }

            norm = getNorm(normSequenceNumber, result, xn);

            for (int i = 0; i < result.length; ++i) {
                result[i] = xn[i];
            }
            
            ++k;
        } while (norm > ACCURACY.calculate(n));

        System.out.printf("количество итераций: %d\n", k);
        System.out.printf("результат: [ ");
        for (int i = 0; i < result.length; ++i) {
            System.out.printf("%f ", result[i]);
        }
        System.out.printf("]\n\n");
    }

    public static double getNorm(int normSequenceNumber, double[] previousXs, double[] currentXs) {
        double norm = 0;

        switch (normSequenceNumber) {
            case 1:
                double[] absoluteDifference = new double[previousXs.length];
                for (int i = 0; i < absoluteDifference.length; ++i) {
                    absoluteDifference[i] = Math.abs(currentXs[i] - previousXs[i]);
                }
                norm = Arrays.stream(absoluteDifference).max().getAsDouble();
                break;
            case 2:
                for (int i = 0; i < previousXs.length; ++i) {
                    norm += Math.abs(currentXs[i] - previousXs[i]);
                }
                break;
            case 3:
                for (int i = 0; i < previousXs.length; ++i) {
                    norm += Math.pow(currentXs[i] - previousXs[i], 2 * L);
                }
                norm = Math.pow(norm, 1. / (2 * L));
                break;
        }

        return norm;
    }
}