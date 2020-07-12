package com.cloud.base.repository;

import com.cloud.base.models.Specialties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtiesRepository extends JpaRepository<Specialties, Long> {
}
