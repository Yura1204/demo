package com.example.demo.controller;

import com.example.demo.entity.TimeRange;
import com.example.demo.entity.User;
import com.example.demo.service.TimeRangeService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/timerange/")
public class TimeRangeController {
    private TimeRangeService timeRangeService;

    @Autowired
    public TimeRangeController(TimeRangeService timeRangeService) {
        this.timeRangeService = timeRangeService;
    }

    @GetMapping("admin/all")
    public List<TimeRange> getAllTimeRange() {
        return timeRangeService.getAllTimeRanges();
    }

    @PostMapping
    public void addTimeRange(@RequestBody TimeRange timeRange) {
        timeRangeService.save(timeRange);
    }

}
