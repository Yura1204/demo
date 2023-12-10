package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.TimeRange;
import com.example.demo.entity.User;
import com.example.demo.repository.TimeRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeRangeService {
    @Autowired
    private TimeRangeRepository timeRangeRepository;

    public List<TimeRange> getAllTimeRanges() {
        List<TimeRange> timeRanges = timeRangeRepository.findAll();
        return timeRanges;
    }

    public TimeRange save(TimeRange timeRange) {
        return timeRangeRepository.save(timeRange);
    }


}
