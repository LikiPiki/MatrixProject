import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by sergey on 01.02.16.
 * TODO
 * сделать каркасную модель
 */
public class Main {

    public static int SIMPLESIZE = 4;
    public static int MAINANGLEA = 210;
    public static int MAINANGLEB = -30;

    public static void main(String[] args) throws IOException {

        int n1, m1;
        n1 = 4;
        m1 = 3;
        double matrix1[][] = {
                {1, 5, 1},
                {5, 5, 5},
                {5, 5, 5},
                {1, 5, 1},
        };
        int n2, m2;
        n2 = 3;
        m2 = 3;
        double matrix2[][] = {
                {1, 7, 1},
                {7, 6, 7},
                {1, 7, 1},
        };

        double cube[][] = {
                {0, 0, 0, 1},
                {1, 0, 0, 1},
                {0, 1, 0, 1},
                {1, 1, 0, 1},
                {0, 1, 0, 1},
                {0, 0, 1, 1},
                {1, 0, 1, 1},
                {0, 1, 1, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
        };

        double startOO[][] = {{0, 0, 0, 1}};
        double startOY[][] = {{0, 0, 50, 1}};
        double startOX[][] = {{0, 50, 0, 1}};
        double startOZ[][] = {{50, 0, 0, 1}};
        double[][] testMatrix = rotateXMatrix(60);

        double point[][] = {{1, 0, 0, 1}};
        double zeroOne[][] = {{1, 1, 0, 1}};

        double[][] matrixP = proection(300, 200, 100);

        System.err.println(Arrays.deepToString(matrixP));


        for (int i = 0; i < cube[0].length; i++) {
            double[][] poi = mul(new double[][]{cube[i]}, matrixP);
            System.out.println(Arrays.deepToString(poi));
        }
        BufferedImage image = new BufferedImage(600, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics gc = image.getGraphics();
        gc.setColor(Color.RED);
        double[][] z0 = mul(startOO, matrixP);
        double[][] z1 = mul(startOY, matrixP);
        gc.drawPolyline(new int[]{(int) Math.round(z0[0][0]), (int) Math.round(z1[0][0])},
                new int[]{(int) Math.round(z0[0][1]), (int) Math.round(z1[0][1])}, 2);

        gc.setColor(Color.GREEN);
        double[][] z3 = mul(startOX, matrixP);
        System.err.println("oy==" + Arrays.deepToString(z3));
        gc.drawPolyline(new int[]{(int) Math.round(z0[0][0]), (int) Math.round(z3[0][0])},
                new int[]{(int) Math.round(z0[0][1]), (int) Math.round(z3[0][1])}, 2);

        gc.setColor(Color.BLUE);
        double[][] z4 = mul(startOZ, matrixP);
        gc.drawPolyline(new int[]{(int) Math.round(z0[0][0]), (int) Math.round(z4[0][0])},
                new int[]{(int) Math.round(z0[0][1]), (int) Math.round(z4[0][1])}, 2);


        gc.setColor(Color.BLACK);
        for (int i = 0; i < cube.length; i++) {
            double[][] poi = mul(new double[][]{cube[i]}, matrixP);
            System.out.println(Arrays.deepToString(poi));
            gc.fillRect((int) poi[0][0], (int) poi[0][1], 2, 2);
        }
        ImageIO.write(image, "png", new File("a.png"));

    }

    private static double[][] mul(double matrix1[][], double matrix2[][]) {
        double[][] rezMatrix = new double[matrix1.length][matrix2[0].length];
        int n1 = matrix1.length;
        int m1 = matrix1[0].length;
        int m2 = matrix2[0].length;
        for (int i = 0; i < n1; i++) {
            for (int k = 0; k < m2; k++) {
                double summary = 0.;
                for (int j = 0; j < m1; j++) {
                    summary += matrix1[i][j] * matrix2[j][k];
                }
                rezMatrix[i][k] = summary;
            }
        }
        return rezMatrix;
    }

    private static double[][] rotateZMatrix(int alpha) {
        double rezMatrix[][] = createSimpleMatrix();
        rezMatrix[0][0] = rezMatrix[1][1] = Math.cos(Math.toRadians(alpha));
        rezMatrix[0][1] = Math.sin(Math.toRadians(alpha));
        rezMatrix[1][0] = -1 * rezMatrix[0][1];
        return rezMatrix;
    }

    private static double[][] rotateYMatrix(int alpha) {
        double rezMatrix[][] = createSimpleMatrix();
        rezMatrix[0][0] = rezMatrix[2][2] = Math.cos(Math.toRadians(alpha));
        rezMatrix[2][0] = Math.sin(Math.toRadians(alpha));
        rezMatrix[0][2] = -1 * rezMatrix[2][0];
        return rezMatrix;
    }

    private static double[][] rotateXMatrix(int alpha) {
        double rezMatrix[][] = createSimpleMatrix();
        rezMatrix[1][1] = rezMatrix[2][2] = Math.cos(Math.toRadians(alpha));
        rezMatrix[2][1] = Math.sin(Math.toRadians(alpha));
        rezMatrix[1][2] = -1 * rezMatrix[2][1];
        return rezMatrix;
    }

    private static double[][] createSimpleMatrix() {
        double newMatrix[][] = new double[SIMPLESIZE][SIMPLESIZE];
        newMatrix[0][0] = newMatrix[1][1] = newMatrix[2][2] = newMatrix[3][3] = 1;
        return newMatrix;
    }

    private static double[][] proection(int startX, int startY, int scale) {
        double rezMatrix[][] = new double[SIMPLESIZE][SIMPLESIZE];
        rezMatrix[0][0] = -scale * Math.sin(Math.toRadians(-60));
        rezMatrix[1][0] = scale * Math.sin(Math.toRadians(180 + 60));
        rezMatrix[3][0] = startX;

        rezMatrix[0][1] = scale * Math.cos(Math.toRadians(-60));
        rezMatrix[1][1] = -scale * Math.cos(Math.toRadians(180 + 60));
        rezMatrix[2][1] = -scale;
        rezMatrix[3][1] = startY;

        rezMatrix[3][3] = 1;
        return rezMatrix;
    }
}
