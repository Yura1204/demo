package com.example.demo.dto;

import com.example.demo.entity.Schedule;
import com.example.demo.entity.TimeRange;
import com.example.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTimeRangeDTO {
    private String userName;
    private List<Schedule> schedules;
//    private List<TimeRange> timeRanges;

    public static UserTimeRangeDTO toModel(User entity, TimeRange timeRange) {
        UserTimeRangeDTO model = new UserTimeRangeDTO();
        model.setUserName(entity.getUsername());
        model.setSchedules((List<Schedule>) timeRange.getSchedule());
//        model.setTimeRanges(entity.getTimeRanges());
        return model;
    }
}
