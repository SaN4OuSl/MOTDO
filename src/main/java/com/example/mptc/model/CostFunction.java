package com.example.mptc.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class CostFunction {
	
	private final double[] coefs;
	private final Variable[] variables;
	private final boolean isMinimized;

	public CostFunction(double[] coefs, Variable[] variables, boolean isMinimized) {
		this.coefs = coefs;
        this.variables = variables;
		this.isMinimized = isMinimized;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern("0.####");

        if (coefs[0] != 0)
            string.append(df.format(coefs[0])).append(variables[0]).append(" ");
		
		for (int i = 1; i < coefs.length; i++) {
			if (coefs[i] != 0) {
				if (Math.signum(coefs[i]) >= 0)
					string.append("+");
				else
					string.append("-");
				string.append(df.format(Math.abs(coefs[i]))).append(variables[i]).append(" ");
			}
		}
		
		string.append("--> ").append(isMinimized ? "min" : "max");
		
		return string.toString();
	}

    public double getCoef(Variable variable) {
        ArrayList<Variable> variableList = new ArrayList<>(Arrays.asList(variables));
        if (variableList.contains(variable))
            return coefs[variableList.indexOf(variable)];
        return 0;
    }

    public Variable[] getVariables() {
        return variables;
    }

	public boolean shouldBeMinimized() {return isMinimized;}
	
}
