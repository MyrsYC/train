package com.train.business.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.train.business.dto.TrainQueryDTO;
import com.train.business.entity.Train;
import com.train.business.mapper.TrainMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Train service
 */
@Slf4j
@Service
public class TrainService {
    
    @Resource
    private TrainMapper trainMapper;
    
    public List<Train> queryTrains(TrainQueryDTO dto) {
        LambdaQueryWrapper<Train> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Train::getStartStation, dto.getStartStation())
               .eq(Train::getEndStation, dto.getEndStation())
               .eq(Train::getStatus, 1);
        
        // Filter by date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date queryDate = sdf.parse(dto.getDepartureDate());
            String startDateStr = dto.getDepartureDate() + " 00:00:00";
            String endDateStr = dto.getDepartureDate() + " 23:59:59";
            
            wrapper.ge(Train::getDepartureTime, startDateStr)
                   .le(Train::getDepartureTime, endDateStr);
        } catch (Exception e) {
            log.error("Date parsing error", e);
        }
        
        return trainMapper.selectList(wrapper);
    }
    
    public Train getTrainById(Long trainId) {
        return trainMapper.selectById(trainId);
    }
    
    public List<Train> getAllTrains() {
        LambdaQueryWrapper<Train> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Train::getStatus, 1);
        return trainMapper.selectList(wrapper);
    }
}
