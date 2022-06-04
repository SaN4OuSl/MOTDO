package com.example.mptc.model;


import com.example.mptc.exception.NoSolutionException;
import java.util.List;


public class Problem {

    private final SimplexTable simplexTable;
    private final CostFunction costFunction;
    private final List<Limitation> limitations;
    private int[] rowId;
    private int[] colId;
    private boolean isFirstStepDone;

    public Problem(SimplexTable simplexTable, CostFunction costFunction, List<Limitation> limitations) {
        this.simplexTable = simplexTable;
        this.costFunction = costFunction;
        this.limitations = limitations;
        setup();
    }

    private void setup() {
        this.rowId = new int[simplexTable.rows() - 1];
        this.colId = new int[simplexTable.cols() - 1];
        this.isFirstStepDone = false;
    }

    void swapRowAndColId(int resRow, int resCol) {
        rowId[resRow] += colId[resCol - 1];
        colId[resCol - 1] = rowId[resRow] - colId[resCol - 1];
        rowId[resRow] -= colId[resCol - 1];
    }

    public Answer solve() throws NoSolutionException {
        boolean solved = false;

        for (int i = 0; i < simplexTable.cols() - 1; ++i) {
            colId[i] = i + 1;
        }
        for (int i = 0; i < simplexTable.rows() - 1; ++i) {
            rowId[i] = i + simplexTable.cols();
        }

        while (!solved) {
            if (!isFirstStepDone) {
                solveFirstStep();
            } else {
                solved = solveLastStep();
            }
        }

        return createAnswer();
    }

    private void solveFirstStep() throws NoSolutionException {
        int col = simplexTable.findColWithNegativeElement();
        if (col == 0) {
            isFirstStepDone = true;
        } else {
            int resRow = simplexTable.findResRow(col);
            if (resRow == -1) {
                throw new NoSolutionException("No solution");
            } else {
                int resCol = simplexTable.findResCol(resRow, true);
                swapRowAndColId(resRow, resCol);
                simplexTable.step(resRow, resCol);
            }
        }
    }

    private boolean solveLastStep() throws NoSolutionException {
        int resRow = simplexTable.findResRow();
        if (resRow == -1) {
            return true;
        } else {
            int resCol = simplexTable.findResCol(resRow, false);
            if (resCol == -1) {
                throw new NoSolutionException("No solution");
            } else {
                swapRowAndColId(resRow, resCol);
                simplexTable.step(resRow, resCol);
            }
        }
        return false;
    }

    private Answer createAnswer() {
        Answer answer = new Answer(simplexTable);

        int variableIndex = 1;
        for (Variable v : costFunction.getVariables()) {
            int j = 0;
            while (j < simplexTable.rows() - 1 && rowId[j] != variableIndex) {
                j++;
            }
            if (j == simplexTable.rows() - 1) {
                answer.addItem(v.toString(), 0);
            } else {
                answer.addItem(v.toString(), simplexTable.getElement(j, 0));
            }
            variableIndex++;
        }

        String optimizationDirection = costFunction.shouldBeMinimized() ? "min" : "max";
        double costFunctionValue = (costFunction.shouldBeMinimized() ? -1 : 1) *
            simplexTable.getElement(simplexTable.rows() - 1, 0);
        answer.addItem(optimizationDirection + " F", costFunctionValue);

        return answer;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(costFunction).append("\n");
        for (Limitation l : limitations) {
            result.append(l).append("\n");
        }

        return result.toString();
    }

}
