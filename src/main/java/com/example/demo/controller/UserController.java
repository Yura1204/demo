package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserTimeRangeDTO;
import com.example.demo.entity.Schedule;
import com.example.demo.entity.TimeRange;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    Вывод всех пользователей
    @GetMapping("admin/users")
    public List<UserDTO> allUsers() {
        return userService.getAllUser();
    }

    @GetMapping("user/profile") // Профиль пользователя
    public ResponseEntity<UserDTO> viewUserProfile(Authentication authentication) throws AccessDeniedException {
        // Проверяем, что пользователь аутентифицирован, если да, то получаем имя из аутентификации, затем данные пользователя по его имени
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);

            // Если пользователя не существует, возвращаем ответ с кодом 404 Not Found
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            // Если пользователь найден, возвращаем его данные с кодом 200 OK
            return ResponseEntity.ok(UserDTO.toModel(user));
        }
        // Если пользователь не аутентифицирован, возвращаем ответ с кодом 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/user/profile/my-schedule")
    public ResponseEntity<UserTimeRangeDTO> viewUserSchedule(Authentication authentication) throws AccessDeniedException {
        // Проверяем, что пользователь аутентифицирован, если да, то получаем имя из аутентификации, затем данные пользователя по его имени
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);

            // Если пользователя не существует, возвращаем ответ с кодом 404 Not Found
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // Получаем список временных диапазонов пользователя
            List<TimeRange> timeRanges = user.getTimeRanges();

            // Создаем объект UserScheduleDTO с именем пользователя и его списком временных диапазонов
            UserTimeRangeDTO userScheduleDTO = new UserTimeRangeDTO();
            userScheduleDTO.setUserName(user.getUsername());

            // Создаем список расписаний пользователя
            List<Schedule> schedules = timeRanges.stream()
                    .map(TimeRange::getSchedule)
                    .distinct()
                    .collect(Collectors.toList());

            // Устанавливаем список расписаний в UserTimeRangeDTO
            userScheduleDTO.setSchedules(schedules);

            // Возвращаем объект UserScheduleDTO с кодом 200 OK
            return ResponseEntity.ok(userScheduleDTO);
        }

        // Если пользователь не аутентифицирован, возвращаем ответ с кодом 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }



    @GetMapping("admin/users/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.save(user);
    }

    @DeleteMapping("admin/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
