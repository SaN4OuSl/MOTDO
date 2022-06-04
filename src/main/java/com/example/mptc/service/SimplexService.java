package com.example.mptc.service;

import com.example.mptc.exception.InputException;
import com.example.mptc.exception.NoSolutionException;
import com.example.mptc.model.Answer;
import com.example.mptc.model.CostFunction;
import com.example.mptc.model.Input;
import com.example.mptc.model.Limitation;
import com.example.mptc.model.SimplexTable;
import com.example.mptc.model.Variable;
import com.example.mptc.model.Problem;
import com.example.mptc.request.SimplexRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimplexService {

    public Answer calculate(SimplexRequest simplexRequest) {
        CostFunction costFunction;
        try {
            costFunction = stringToCostFunction(simplexRequest.getCostFunction());
        } catch (InputException e) {
            throw new RuntimeException(e);
        }
        List<Limitation> limitations = new ArrayList<>();
        simplexRequest.getLimitations().forEach( limitation -> {
            try {
                limitations.add(stringToLimitation(limitation));
            } catch (InputException e) {
                throw new RuntimeException(e);
            }
        });
        Input input = new Input(costFunction, limitations);
        Problem problem = createProblem(input);
        try {
            return problem.solve();
        } catch (NoSolutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Problem createProblem(Input input) {
        LinkedHashSet<Variable> variables = mergeVariables(input);
        int rows = input.getLimitationCount() + 1;
        int cols = variables.size() + 1;
        double[][] table = new double[rows][cols];

        table[rows - 1][0] = 0;

        int limitationIndex = 0, variableIndex = 0;
        for (Variable v : variables) {
            if (input.getCostFunction().shouldBeMinimized()) {
                table[rows - 1][variableIndex + 1] = input.getCostFunction().getCoef(v);
            } else {
                table[rows - 1][variableIndex + 1] = -input.getCostFunction().getCoef(v);
            }
            variableIndex++;
        }

        for (Limitation l : input.getLimitations()) {
            variableIndex = 1;
            for (Variable v : variables) {
                if (l.getSign() == Limitation.LimitationSign.GE) {
                    table[limitationIndex][variableIndex] =
                        -input.getLimitation(limitationIndex).getCoef(v);
                } else {
                    table[limitationIndex][variableIndex] =
                        input.getLimitation(limitationIndex).getCoef(v);
                }
                variableIndex++;
            }
            limitationIndex++;
        }

        limitationIndex = 0;
        for (Limitation l : input.getLimitations()) {
            if (l.getSign() == Limitation.LimitationSign.GE) {
                table[limitationIndex][0] = -l.getFreeTerm();
            } else {
                table[limitationIndex][0] = l.getFreeTerm();
            }
            limitationIndex++;
        }

        return new Problem(new SimplexTable(table), input.getCostFunction(),
            input.getLimitations());
    }

    private static LinkedHashSet<Variable> mergeVariables(Input input) {
        LinkedHashSet<Variable> variables =
            new LinkedHashSet<>(Arrays.asList(input.getCostFunction().getVariables()));

        for (Limitation l : input.getLimitations()) {
            Collections.addAll(variables, l.getVariables());
        }

        return variables;
    }

    public static CostFunction stringToCostFunction(String input) throws InputException {
        input = input.replaceAll("\\s", "");

        if (!input.contains("min") && !input.contains("max")) {
            throw new InputException("Optimization direction is not specified");
        }
        if (input.contains("min") && input.contains("max")) {
            throw new InputException("Multiple optimization directions are specified");
        }
        boolean shouldBeMinimized = input.contains("min");

        input = input.replaceAll("(-?->)?(max|min)", "");
        ArrayList<String> atoms = new ArrayList<>(Arrays.asList(input.split("(?=\\+)|(?=\\-)")));

        HashMap<String, Object> parseResult = parseAtoms(atoms);

        return new CostFunction((double[]) parseResult.get("coefs"),
            (Variable[]) parseResult.get("vars"), shouldBeMinimized);
    }

    public static Limitation stringToLimitation(String input) throws InputException {
        Limitation.LimitationSign sign;

        if (input.contains("<=")) {
            if (input.contains(">=")) {
                throw new InputException("Incorrect limitation sign");
            }
            sign = Limitation.LimitationSign.LE;
        } else if (input.contains(">=")) {
            sign = Limitation.LimitationSign.GE;
        } else {
            sign = Limitation.LimitationSign.EQ;
        }

        if (!input.contains("=")) {
            throw new InputException("Limitation sign is not specified");
        }

        input = input.replaceAll("\\s|<|>", "");

        ArrayList<String> atoms = new ArrayList<>(Arrays.asList(input.split("=|(?=\\+)|(?=\\-)")));
        atoms.removeAll(Arrays.asList("", null));
        double freeTerm = Double.parseDouble(atoms.remove(atoms.size() - 1));

        HashMap<String, Object> parseResult = parseAtoms(atoms);

        return new Limitation((double[]) parseResult.get("coefs"),
            (Variable[]) parseResult.get("vars"), sign, freeTerm);
    }

    private static HashMap<String, Object> parseAtoms(ArrayList<String> atoms)
        throws InputException {
        int coefsCount = 0;
        Pattern p = Pattern.compile("((?:[\\-\\+])?\\d*(?:\\.\\d+)?)?([a-zA-Z]+)?(\\d*)?");
        double[] coefs = new double[atoms.size()];
        Variable[] variables = new Variable[atoms.size()];

        for (String atom : atoms) {
            Matcher m = p.matcher(atom);
            if (m.find()) {
                if (m.group(1).equals("") || m.group(1).equals("+")) {
                    coefs[coefsCount] = 1;
                } else if (m.group(1).equals("-")) {
                    coefs[coefsCount] = -1;
                } else {
                    coefs[coefsCount] = Double.parseDouble(m.group(1));
                }
                if (m.group(3).equals("")) {
                    variables[coefsCount] = new Variable(m.group(2));
                } else {
                    variables[coefsCount] = new Variable(m.group(2), Integer.parseInt(m.group(3)));
                }
            } else {
                throw new InputException("Variables are supplied incorrectly");
            }

            coefsCount++;
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("coefs", coefs);
        result.put("vars", variables);
        return result;
    }
}
