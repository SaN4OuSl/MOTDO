package com.example.mptc.request;

import java.util.List;
import lombok.Data;

@Data
public class SimplexRequest {
    String costFunction;
    List<String> limitations;
}
