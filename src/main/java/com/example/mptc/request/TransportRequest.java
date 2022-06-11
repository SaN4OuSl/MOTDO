package com.example.mptc.request;

import com.example.mptc.model.Element;
import java.util.List;
import lombok.Data;

@Data
public class TransportRequest {
    int[] a;
    int[] b;
    List<List<Element>> C;
}
