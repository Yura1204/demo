package com.example.demo.service;

//import com.example.demo.dto.ScheduleDTO;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDTO::toModel)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return UserDTO.toModel(user);
    }

//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }

    public User getUserByUsername(String username) throws AccessDeniedException {
        // Загрузка данных пользователя из базы данных по его имени
        User user = userRepository.findByUsername(username);

        // Проверка, что текущий пользователь совпадает с запрашиваемым пользователем
        if (!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new AccessDeniedException("У вас нет разрешения на доступ к этому ресурсу");
        }
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Long delete(Long id) {
        userRepository.deleteById(id);
        return id;
    }


    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                User user = (User) principal;
                return user.getId().equals(userId);
            }
        }
        return false;
    }

}
