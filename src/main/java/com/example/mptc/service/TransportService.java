package com.example.mptc.service;

import com.example.mptc.model.Element;
import com.example.mptc.request.TransportRequest;
import com.example.mptc.response.TransportResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransportService {

    public TransportResponse calculate(TransportRequest transportRequest) {
        int i = 0;
        int j = 0;
        int n = transportRequest.getA().length;
        int m = transportRequest.getB().length;
        int[] a = transportRequest.getA();
        int[] b = transportRequest.getB();
        List<List<Element>> C = transportRequest.getC();
        if (m == C.get(0).size()) {
            while (i < n && j < m) {
                try {
                    if (a[i] == 0) {
                        i++;
                    }
                    if (b[j] == 0) {
                        j++;
                    }
                    if (a[i] == 0 && b[j] == 0) {
                        i++;
                        j++;
                    }
                    C.get(i).get(j).setDelivery(Element.findMinElement(a[i], b[j]));
                    a[i] -= C.get(i).get(j).getDelivery();
                    b[j] -= C.get(i).get(j).getDelivery();
                } catch (RuntimeException ignored) {
                }
            }

            int resultFunction = 0;

            for (i = 0; i < n; i++) {
                for (j = 0; j < m; j++) {
                    resultFunction += (C.get(i).get(j).getValue() * C.get(i).get(j).getDelivery());
                }
            }
            TransportResponse transportResponse = new TransportResponse();
            transportResponse.setC(C);
            transportResponse.setResultFunction(resultFunction);
            return transportResponse;
        } else {
            throw new RuntimeException("Incorrect request");
        }
    }
}
