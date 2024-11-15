package com.projectsbot.command;

import com.projectsbot.builder.SendMessageBuilder;
import com.projectsbot.entity.Project;
import com.projectsbot.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewTreeCommand implements Command, SendMessageBuilder {
    private final ProjectRepository projectRepository;

    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.VIEW_TREE;
    }

    @Override
    @Transactional(readOnly = true)
    public SendMessage executeCommand(Update userUpdate) {
        List<Project> rootProjects = projectRepository.findByParentProjectIsNull();
        rootProjects.forEach(this::initializeSubprojectsRecursively);
        StringBuilder resultTree = new StringBuilder("Current Projects:\n");
        for (Project project : rootProjects) {
            resultTree.append(project.toString()).append("\n");
        }
        return buildMessage(userUpdate.getMessage().getChatId(), resultTree.toString());
    }

    private void initializeSubprojectsRecursively(Project project) {
        if (project.getSubprojects() != null) {
            project.getSubprojects().forEach(this::initializeSubprojectsRecursively);
        }
    }
}
