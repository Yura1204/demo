package com.example.demo.repository;

import com.example.demo.entity.TimeRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRangeRepository extends JpaRepository<TimeRange, Long> {
}
