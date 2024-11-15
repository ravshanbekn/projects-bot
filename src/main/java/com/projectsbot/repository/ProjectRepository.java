package com.projectsbot.repository;

import com.projectsbot.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByName(String name);

    boolean existsByName(String name);

    List<Project> findByParentProjectIsNull();
}
