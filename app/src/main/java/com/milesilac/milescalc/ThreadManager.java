package com.milesilac.milescalc;


import android.os.Handler;
import android.os.Looper;

import com.milesilac.milescalc.common.CalculatorContract;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadManager implements CalculatorContract.ThreadManager {

    private String output;

    private final CalculatorContract.CalculationLogic calculationLogic = new CalculationLogic();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ThreadManager() {
    }


    @Override
    public void execute(String s, CalculatorContract.ResultsGetter resultsGetter) {

        executorService.execute(() -> {
            System.out.println("Running in bg");
            output = calculationLogic.solveString(s);

            handler.post(() -> {
                System.out.println("Running in ui");
                resultsGetter.results(output);
            });
        });

    }
}
