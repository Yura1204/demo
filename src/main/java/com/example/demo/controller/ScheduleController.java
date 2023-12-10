package com.example.demo.controller;

import com.example.demo.dto.ScheduleDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.User;
import com.example.demo.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("admin/all")
    public List<Schedule> allSchedules() {
        return scheduleService.getAllSchedule();
    }

//    @GetMapping("/my")
//    public ResponseEntity<ScheduleDTO> viewScheduleById(@PathVariable Long id) {
//        // Ищем расписание по id
//        Schedule schedule = scheduleService.getScheduleById(id);
//
//        // Если расписание не найдено, возвращаем ответ с кодом 404 Not Found
//        if (schedule == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Если расписание найдено, возвращаем его данные с кодом 200 OK
//        return ResponseEntity.ok(ScheduleDTO.toModel(schedule));
//    }


    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @PostMapping()
    public void addSchedule(Schedule schedule) {
        scheduleService.save(schedule);
    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return id;
    }
}
