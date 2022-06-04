package com.example.mptc.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Limitation {

	public enum LimitationSign { LE, EQ, GE }
	
	private final double[] coefs;
	private final Variable[] variables;
	private final LimitationSign sign;
	private final double freeTerm;

	public Limitation(double[] coefs, Variable[] variables, LimitationSign sign, double freeTerm) {
		this.coefs = coefs;
		this.variables = variables;
		this.sign = sign;
		this.freeTerm = freeTerm;
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

		switch (sign) {
			case LE -> string.append("<= ");
			case EQ -> string.append("= ");
			case GE -> string.append(">= ");
			default -> {
			}
		}
		
		string.append(df.format(freeTerm));
		
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
	
	public LimitationSign getSign() {
		return sign;
	}

	public double getFreeTerm() {
		return freeTerm;
	}
	
}
