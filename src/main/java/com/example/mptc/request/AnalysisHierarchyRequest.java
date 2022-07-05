package com.example.mptc.request;

import lombok.Data;

@Data
public class AnalysisHierarchyRequest {
    double[][] criteria;
    double[][] variants;
}
