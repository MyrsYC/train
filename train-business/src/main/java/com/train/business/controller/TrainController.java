package com.train.business.controller;

import com.train.business.dto.TrainQueryDTO;
import com.train.business.entity.Train;
import com.train.business.service.TrainService;
import com.train.common.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Train controller
 */
@RestController
@RequestMapping("/train")
public class TrainController {
    
    @Resource
    private TrainService trainService;
    
    @PostMapping("/query")
    public Result<List<Train>> queryTrains(@RequestBody @Validated TrainQueryDTO dto) {
        List<Train> trains = trainService.queryTrains(dto);
        return Result.success(trains);
    }
    
    @GetMapping("/list")
    public Result<List<Train>> getAllTrains() {
        List<Train> trains = trainService.getAllTrains();
        return Result.success(trains);
    }
    
    @GetMapping("/{trainId}")
    public Result<Train> getTrainById(@PathVariable Long trainId) {
        Train train = trainService.getTrainById(trainId);
        return Result.success(train);
    }
}
