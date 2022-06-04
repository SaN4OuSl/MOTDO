package com.example.mptc.controller;

import com.example.mptc.model.Answer;
import com.example.mptc.request.SimplexRequest;
import com.example.mptc.service.SimplexService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("symplex")
@CrossOrigin
@AllArgsConstructor
public class SimplexController {

    public final SimplexService simplexService;

    @PostMapping
    public Answer getTables(@RequestBody SimplexRequest simplexRequest) {
        return simplexService.calculate(simplexRequest);
    }

}
