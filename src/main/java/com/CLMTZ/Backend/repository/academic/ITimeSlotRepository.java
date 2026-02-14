package com.CLMTZ.Backend.repository.academic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.academic.TimeSlot;

public interface ITimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

}
