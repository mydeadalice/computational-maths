import java.util.Arrays;
import java.util.LinkedList;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import resources.Operationable;

public class Task_41 {

    private static final int L = 2;
    private static final int[] SEQUENCE_NUMBERS_OF_NORMS = {1, 2, 3};
    private static final int[] N = {2, 3, 4, 5, 10, 12, 15};
    private static Operationable ACCURACY = (n) -> Math.pow(10, -n);
    private static final String MINIMUM_RESIDUALS_NAME = "minimum residuals";
    private static final String STEEPEST_DESCENT_NAME = "steepest descent";

    public static void main(String[] args) {
        LinkedList<RealMatrix> as = new LinkedList<RealMatrix>();
        LinkedList<RealMatrix> asSymmetric = as;
        LinkedList<RealVector> bs = new LinkedList<RealVector>();
        LinkedList<RealVector> x0 = new LinkedList<RealVector>();

        as.add(MatrixUtils.createRealMatrix(new double[][]{{2, 2, -1, 1}, {4, 3, -1, 2}, {8, 5, -3, 4}, {3, 3, -2, 2}}));
        as.add(MatrixUtils.createRealMatrix(new double[][]{{4, 1, -1, 1}, {1, 4, -1, -1}, {-1, -1, 5, 1}, {1, -1, 1, 3}}));
        as.add(MatrixUtils.createRealMatrix(new double[][]{{2.8, 2.1, -1.3, 0.3}, {-1.4, 4.5, -7.7, 1.3}, {0.6, 2.1, -5.8, 2.4}, {3.5, -6.5, 3.2, -7.9}}));
        as.add(MatrixUtils.createRealMatrix(new double[][]{{4, 1, 1, 0, 1}, {1, 3, 1, 1, 0}, {1, 1, 5, -1, -1}, {0, 1, -1, 4, 0}, {1, 0, -1, 0, 4}}));
        as.add(MatrixUtils.createRealMatrix(new double[][]{{4, -1, 0, -1, 0, 0}, {-1, 4, -1, 0, -1, 0}, {0, -1, 4, 0, 0, -1}, {-1, 0, 0, 4, -1, 0}, {0, -1, 0, -1, 4, -1}, {0, 0, -1, 0, -1, 4}}));
        as.add(MatrixUtils.createRealMatrix(new double[][]{{4, -1, 0, 0, 0, 0}, {-1, 4, -1, 0, 0, 0}, {0, -1, 4, 0, 0, 0}, {0, 0, 0, 4, -1, 0}, {0, 0, 0, -1, 4, -1}, {0, 0, 0, 0, -1, 4}}));

        bs.add(MatrixUtils.createRealVector(new double[]{4, 6, 12, 6}));
        bs.add(MatrixUtils.createRealVector(new double[]{-2, -1, 0, 1}));
        bs.add(MatrixUtils.createRealVector(new double[]{1, 1, 1, 1}));
        bs.add(MatrixUtils.createRealVector(new double[]{6, 6, 6, 6, 6}));
        bs.add(MatrixUtils.createRealVector(new double[]{0, 5, 0, 6, -2, 6}));
        bs.add(MatrixUtils.createRealVector(new double[]{0, 5, 0, 6, -2, 6}));

        x0.add(MatrixUtils.createRealVector(new double[]{1.1, 1.1, -1.1, -1.1}));
        x0.add(MatrixUtils.createRealVector(new double[]{-0.8, 0, 0.3, 0.7}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.2, 0.02, -0.2, -0.1}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.5, 0.7, 1.7, 1.7, 1.8}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.9, 1.9, 0.9, 1.9, 0.9, 1.9}));
        x0.add(MatrixUtils.createRealVector(new double[]{0.4, 1.4, 0.4, 1.5, 0.2, 1.5}));

        doMatrixSymmetrization(asSymmetric, as, bs);

        System.out.printf("задание 4.1.\n\n");
        for (int i = 0; i < asSymmetric.size(); ++i) {
            System.out.printf("решение слау #%d:\n\n", (i + 1));
            for (int n : N) {
                System.out.printf("для точности e^(-%d):\n\n", n);
                for (int normSequenceNumber : SEQUENCE_NUMBERS_OF_NORMS) {
                    System.out.printf("норма %d:\n\n", normSequenceNumber);
                    System.out.printf("метод минимальных невязок.\n\n");
                    doCalculations(MINIMUM_RESIDUALS_NAME, asSymmetric.get(i), bs.get(i), x0.get(i), n, normSequenceNumber);
                    System.out.printf("метод наискорейшего спуска.\n\n");
                    doCalculations(STEEPEST_DESCENT_NAME, asSymmetric.get(i), bs.get(i), x0.get(i), n, normSequenceNumber);
                }
            }
        }
    }

    // проверка матрицы на симметричность
    private static boolean isSymmetric(RealMatrix matrix) {
        for (int i = 0; i < matrix.getRowDimension(); ++i) {
            for (int j = 0; j < matrix.getRowDimension(); ++j) {
                if (matrix.getEntry(i, j) != matrix.getEntry(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }

    // симметризация матрицы
    private static void doMatrixSymmetrization(LinkedList<RealMatrix> asSymmetric, LinkedList<RealMatrix> as, LinkedList<RealVector> bs) {
        RealMatrix transposedMatrix;
        for (int i = 0; i < asSymmetric.size(); ++i) {
            if (!isSymmetric(asSymmetric.get(i))) {
                transposedMatrix = asSymmetric.get(i).transpose();
                asSymmetric.set(i, transposedMatrix.multiply(as.get(i)));
                bs.set(i, transposedMatrix.operate(bs.get(i)));
            }
        }
    }

    public static void doCalculations(String methodName, RealMatrix a, RealVector b, RealVector x, int n, int normSequenceNumber) {
        int k = 0;
        double norm;
        int size = a.getRowDimension();

        RealVector previousXs;
        RealVector currentXs = x;
        RealVector r;
        double tau = 0;
        
        do {
            // вектор невязок на текущем шаге
            r = (a.operate(currentXs)).subtract(b);

            // длина шага вдоль направления градиента
            switch(methodName) {
                case MINIMUM_RESIDUALS_NAME:
                    tau = r.dotProduct(a.operate(r)) / a.operate(r).dotProduct(a.operate(r));
                    break;
                case STEEPEST_DESCENT_NAME:
                    tau = r.dotProduct(r) / (a.operate(r)).dotProduct(r);
                    break;
            }

            previousXs = currentXs;

            // новое приближение на текущем шаге
            currentXs = currentXs.subtract(r.mapMultiply(tau));

            // расчет нормы
            norm = getNorm(normSequenceNumber, size, currentXs, previousXs);

            ++k;
        } while (norm > ACCURACY.calculate(n));

        // вывод результатов вычислений
        System.out.printf("количество итераций: %d\nрезультат: ", k);
        System.out.println(Arrays.toString(currentXs.toArray()));
        System.out.printf("\n");
    }

    public static double getNorm(int normSequenceNumber, int size, RealVector currentXs, RealVector previousXs) {
        double norm = 0;

        switch (normSequenceNumber) {
            case 1:
                double[] absoluteDifference = new double[size];
                for (int i = 0; i < absoluteDifference.length; ++i) {
                    absoluteDifference[i] = Math.abs(currentXs.getEntry(i) - previousXs.getEntry(i));
                }
                norm = Arrays.stream(absoluteDifference).max().getAsDouble();
                break;
            case 2:
                for (int i = 0; i < size; ++i) {
                    norm += Math.abs(currentXs.getEntry(i) - previousXs.getEntry(i));
                }
                break;
            case 3:
                for (int i = 0; i < size; ++i) {
                    norm += Math.pow(currentXs.getEntry(i) - previousXs.getEntry(i), 2 * L);
                }
                norm = Math.pow(norm, 1. / (2 * L));
                break;
        }

        return norm;
    }
}