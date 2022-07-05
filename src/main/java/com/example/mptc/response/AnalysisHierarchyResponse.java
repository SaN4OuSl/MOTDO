package com.example.mptc.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalysisHierarchyResponse {
    List<Double> results;
}
