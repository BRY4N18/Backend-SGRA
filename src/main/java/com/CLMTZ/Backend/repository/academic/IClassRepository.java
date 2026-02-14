package com.CLMTZ.Backend.repository.academic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CLMTZ.Backend.model.academic.Class;

public interface IClassRepository extends JpaRepository<Class, Integer> {

}
