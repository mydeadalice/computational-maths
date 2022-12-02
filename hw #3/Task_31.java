import java.util.LinkedList;

public class Task_31 {
    public static void main(String[] args) {
        LinkedList<double[][]> equations = new LinkedList<double[][]>();

        equations.add(new double[][] { { 2, 2, -1, 1, 4 }, { 4, 3, -1, 2, 6 }, { 8, 5, -3, 4, 12 }, { 3, 3, -2, 2, 6 } });
        equations.add(new double[][] { { 1, 7, -9, -8, -7 }, { -3, -18, 23, 28, 5 }, { 0, -3, 6, -1, 8 }, { -1, -1, 1, 18, -29 } });
        equations.add(new double[][] { { 3, -3, 7, -4, 0 }, { -6, 9, -21, 9, 9 }, { 9, -12, 30, -22, -2 }, { 6, 0, 6, -31, 37 } });
        equations.add(new double[][] { { 9, -5, -6, 3, -8 }, { 1, -7, 1, 0, 38 }, { 3, -4, 9, 0, 47 }, { 6, -1, 9, 8, -8 } });
        equations.add(new double[][] { { -6, -5, -3, -8, 101 }, { 5, -1, -5, -4, 51 }, { -6, 0, 5, 5, -53 }, { -7, -2, 8, 5, -63 } });
    
        int i = 1;
        System.out.printf("задание 3.1.\n\nметод гаусса.\n\n");
        for (double[][] equation : equations) {
            System.out.printf("решение слау #%d:\n\n", i);
            doGauss(equation);
            ++i;
        }
    }

    public static void doGauss(double[][] matrix) {
        int n = matrix.length;  // размерность начальной матрицы (кол-во строк)
        double[][] matrixClone = new double[n][n + 1];  // матрица-дублер
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n + 1; ++j) {
                matrixClone[i][j] = matrix[i][j];
            }
        }

        // прямой ход (зануление нижнего левого угла)
        for (int k = 0; k < n; ++k) {   // k -- номер строки
            for (int i = 0; i < n + 1; ++i) {   // i -- номер столбца
                matrixClone[k][i] /= matrix[k][k];   // деление k-ой строки на первый член !=0 для преобразования его в 1
            }

            for (int i = k + 1; i < n; ++i) {   // i -- номер следующей после k строки
                double ratio = matrixClone[i][k] / matrixClone[k][k];   // коэффициент
                for (int j = 0; j < n + 1; ++j) {   // j -- номер столбца следующей после k строки
                    matrixClone[i][j] -= matrixClone[k][j] * ratio; // зануление элементов матрицы ниже первого члена, преобразованного в 1
                }
            }

            for (int i = 0; i < n; ++i) {   // внесение изменений в начальную матрицу
                for (int j = 0; j < n + 1; ++j) {
                    matrix[i][j] = matrixClone[i][j];
                }
            }
        }

        // обратный ход (зануление верхнего правого угла)
        for (int k = n - 1; k > -1; --k) {  // k -- номер строки
            for (int i = n; i > -1; --i) { //i-номер столбца
                matrixClone[k][i] /= matrix[k][k];
            }

            for (int i = k - 1; i > -1; --i) {  // i -- номер следующей после k строки
                double ratio = matrixClone[i][k] / matrixClone[k][k];
                for (int j = n; j > -1; --j) {  // j -- номер столбца следующей после k строки
                    matrixClone[i][j] -= matrixClone[k][j] * ratio;
                }
            }
        }

        // вывод ответов
        for (int i = 0; i < n; ++i) {
            System.out.printf("x%d = %f\n", i + 1, matrixClone[i][n]);
        }
        System.out.printf("\n");
    }
}