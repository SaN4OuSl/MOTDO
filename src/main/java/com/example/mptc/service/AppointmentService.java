package com.example.mptc.service;

import com.example.mptc.request.AppointmentRequest;
import com.example.mptc.response.AppointmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentService {
    private static Integer minCost;

    private void calcMinCost(int pos, int[] work, int[][] cost, int curSum) {
        if (pos == work.length) {
            minCost = Math.min(minCost, curSum);
        } else {
            for (int i = pos; i < work.length; i++) {
                {
                    int t = work[i];
                    work[i] = work[pos];
                    work[pos] = t;
                }
                calcMinCost(pos + 1, work, cost, curSum + cost[pos][work[pos]]);
                {
                    int t = work[i];
                    work[i] = work[pos];
                    work[pos] = t;
                }
            }
        }
    }

    public AppointmentResponse solveRec(AppointmentRequest appointmentRequest) {
        int n = appointmentRequest.getCost().length;
        int[] work = new int[n];
        for (int i = 0; i < n; i++) {
            work[i] = i;
        }
        minCost = Integer.MAX_VALUE;
        calcMinCost(0, work, appointmentRequest.getCost(), 0);
        return new AppointmentResponse(minCost);
    }
}
