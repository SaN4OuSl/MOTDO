package com.example.mptc.response;

import com.example.mptc.model.Element;
import java.util.List;
import lombok.Data;

@Data
public class TransportResponse {
    List<List<Element>> C;
    Integer resultFunction;
}
