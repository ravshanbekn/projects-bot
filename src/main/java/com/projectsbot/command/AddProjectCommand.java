package com.projectsbot.command;

import com.projectsbot.builder.SendMessageBuilder;
import com.projectsbot.entity.Project;
import com.projectsbot.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AddProjectCommand implements Command, SendMessageBuilder {
    private final ProjectRepository projectRepository;

    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.ADD_PROJECT;
    }

    @Override
    @Transactional
    public SendMessage executeCommand(Update userUpdate) {
        String message = userUpdate.getMessage().getText();
        String[] args = message.split("\\s+");
        String projectName = args[1];
        if (projectRepository.existsByName(projectName)) {
            throw new IllegalArgumentException("Project with this name already exists");
        }
        Project newProject = new Project(projectName);
        Project saved = projectRepository.save(newProject);
        return buildMessage(userUpdate.getMessage().getChatId(),
                "Project with name: %s was successfully created".formatted(saved.getName()));
    }
}
