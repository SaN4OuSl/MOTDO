package com.example.mptc.controller;

import com.example.mptc.model.Answer;
import com.example.mptc.model.Element;
import com.example.mptc.request.SimplexRequest;
import com.example.mptc.request.TransportRequest;
import com.example.mptc.response.TransportResponse;
import com.example.mptc.service.SimplexService;
import com.example.mptc.service.TransportService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class BasicController {

    public final SimplexService simplexService;

    public final TransportService transportService;
    @PostMapping("/symplex")
    public Answer getTables(@RequestBody SimplexRequest simplexRequest) {
        return simplexService.calculate(simplexRequest);
    }

    @PostMapping("/transport")
    public TransportResponse getTransportAnswer(@RequestBody TransportRequest transportRequest) {
        return transportService.calculate(transportRequest);
    }
}
