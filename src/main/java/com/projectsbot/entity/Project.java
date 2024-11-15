package com.projectsbot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_project")
    private Project parentProject;

    @OneToMany(mappedBy = "parentProject", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Project> subprojects;

    public Project(String name) {
        this.name = name;
    }

    public void addSubproject(Project subproject) {
        subprojects.add(subproject);
        subproject.setParentProject(this);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    private String toString(int level) {
        String indent = "  ".repeat(level);

        String subprojectsString = subprojects == null ? "" :
                subprojects.stream()
                        .map(sub -> sub.toString(level + 1))
                        .collect(Collectors.joining("\n"));
        return indent + "-- " + name + (subprojectsString.isEmpty() ? "" : "\n" + subprojectsString);
    }
}
