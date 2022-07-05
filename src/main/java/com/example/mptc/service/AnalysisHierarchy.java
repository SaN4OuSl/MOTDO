package com.example.mptc.service;

import com.example.mptc.request.AnalysisHierarchyRequest;
import com.example.mptc.response.AnalysisHierarchyResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnalysisHierarchy {

    public AnalysisHierarchyResponse calculate(AnalysisHierarchyRequest analysisHierarchyRequest) {
        List<Double> answers = new ArrayList<>();
        int numberOfCriteria = analysisHierarchyRequest.getCriteria().length + 1;
        double[][] criteria = new double[numberOfCriteria][numberOfCriteria];
        double[][] variants = analysisHierarchyRequest.getVariants();
        double[] sum = new double[numberOfCriteria];
        double[] coef = new double[numberOfCriteria];

        //Заносим все коэффициенты сравнения между критериями
        for (int i = 0; i < numberOfCriteria - 1; i++) {
            for (int j = i + 1; j < numberOfCriteria; j++) {
                criteria[i][j] = analysisHierarchyRequest.getCriteria()[i][j-1];
                //Заполняем оставшиеся ячейки, где сравниваются разные критерии
                try {
                    criteria[j][i] = 1 / criteria[i][j];
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //Заносим коэффициенты между одними и теми же критериями (коэффициенты, равные единице)
        for (int i = 0; i < numberOfCriteria; i++) {
            for (int j = i; j < numberOfCriteria; j++) {
                if (i == j) {
                    criteria[i][j] = 1;
                }
            }
        }
        double s = 0;//общая сумма по всем критериям
        for (int i = 0; i < numberOfCriteria; i++) {
            sum[i] = 0;
            for (int j = 0; j < numberOfCriteria; j++) {
                sum[i] = sum[i] + criteria[i][j];

            }
            s = s + sum[i];
        }
        //Считаем коэффициенты
        for (int i = 0; i < numberOfCriteria; i++) {
            coef[i] = sum[i] / s;
        }
        int numberOfVariants = variants[0].length;

        //Высчитываем коефициенты для каждого варианта
        for (int i = 0; i < numberOfVariants; i++) {
            double some = 0;
            for (int j = 0; j < numberOfCriteria; j++) {
                some += coef[coef.length - 1 - j] * variants[j][i];
            }
            answers.add(some);
        }
        return new AnalysisHierarchyResponse(answers);
    }
}
