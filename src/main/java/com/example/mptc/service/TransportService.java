package com.example.mptc.service;

import com.example.mptc.request.TransportRequest;
import com.example.mptc.response.TransportResponse;
import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service
public class TransportService {

    public static int[][] x;
    public static int[][] c; // дополнительная матрица
    public static int min = 999;
    public static int index_min_i = 0;
    public static int index_min_j = 0;

    public TransportResponse calculate(TransportRequest transportRequest) {
        int[] a = transportRequest.getA();  // запасы
        int[] b = transportRequest.getB();  // потребности
        int[][] l = transportRequest.getC();
        x = new int[l.length + 1][l[0].length + 1];
        for (int i = 0; i < x.length; i++) {
            if (i >= 1) {
                x[i][0] = a[i - 1];
                if (x[i].length - 1 >= 0) {
                    System.arraycopy(l[i - 1], 0, x[i], 1, x[i].length - 1);
                }
            } else {
                x[0][0] = 0;
                if (x[i].length - 1 >= 0) {
                    System.arraycopy(b, 0, x[0], 1, x[i].length - 1);
                }
            }
        }
        c = new int[x.length - 1][x.length];// иницилизация матрицы результатов
        while ((summaStocks(x) != 0) &&
            (summaRequest(x) != 0)) { // считаем пока есть запасы и потребности
            findMin(x); // нахождение минимального элемента
            if (x[index_min_i][0] < x[0][index_min_j]) { // потребности меньше запаса
                c[index_min_i - 1][index_min_j - 1] = x[index_min_i][0];
                x[index_min_i][index_min_j] = 0;
                x[0][index_min_j] -= x[index_min_i][0];
                x[index_min_i][0] = 0;
            } else {
                c[index_min_i - 1][index_min_j - 1] = x[0][index_min_j];
                x[index_min_i][index_min_j] = 0;
                x[index_min_i][0] -= x[0][index_min_j];
                x[0][index_min_j] = 0;
            }
        }
        int resultFunction = 0;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                resultFunction += c[i][j] * l[i][j];
            }
        }
        TransportResponse transportResponse = new TransportResponse();
        transportResponse.setResultFunction(resultFunction);
        transportResponse.setC(c);
        return transportResponse;
    }


    // нахождение минимального элемента
    public static void findMin(int[][] mas) {
        min = 999;
        for (int i = mas.length - 1; i > 0; i--) {
            for (int j = mas[0].length - 1; j > 0; j--) {
                if ((mas[i][0] != 0) && (mas[0][j] != 0)) {
                    if ((mas[i][j] < min)) {
                        min = mas[i][j];
                        index_min_i = i;
                        index_min_j = j;
                    }
                }
            }
        }
    }

    // сумма запасов
    public static int summaStocks(int[][] mas) {
        int temp = 0;
        for (int j = 0; j < x.length; j++) {
            temp += mas[j][0];
        }
        return (temp);
    }

    // сумма потребностей
    public static int summaRequest(int[][] mas) {
        int temp = 0;
        for (int j = 0; j < x[0].length; j++) {
            temp += mas[0][j];
        }
        return (temp);
    }
}
