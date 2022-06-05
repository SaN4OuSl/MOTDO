package com.example.mptc.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {

    private LinkedHashMap<String, Double> items;
    private SimplexTable simplexTable;

    Answer(SimplexTable simplexTable) {
        this.simplexTable = simplexTable;
        items = new LinkedHashMap<>();
    }

    public String convertToString(boolean integer, boolean asCsv) {
        StringBuilder string = new StringBuilder();

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern("0.####");

        for (Map.Entry<String, Double> item : items.entrySet()) {
            string.append("\n").append(item.getKey()).append(asCsv ? ", " : " = ");
            if (integer)
                string.append((int) Math.floor(item.getValue()));
            else
                string.append(df.format(item.getValue()));
        }

        return string.substring(1);
    }
    protected void addItem(String key, double value) {
        items.put(key, value);
    }
}
