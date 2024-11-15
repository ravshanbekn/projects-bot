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
public class AddSubProjectCommand implements Command, SendMessageBuilder {
    private final ProjectRepository projectRepository;

    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.ADD_SUBPROJECT;
    }

    @Override
    @Transactional
    public SendMessage executeCommand(Update userUpdate) {
        String message = userUpdate.getMessage().getText();
        String[] args = message.split("\\s+");
        String projectName = args[1];
        String subprojectName = args[2];
        if (projectRepository.existsByName(subprojectName)) {
            throw new IllegalArgumentException("Project with name: %s already exists");
        }
        Project project = projectRepository.findByName(projectName).orElseThrow(
                () -> new IllegalArgumentException("Project with name: %s was not found".formatted(projectName)));
        Project newProject = new Project(subprojectName);
        project.addSubproject(newProject);
        projectRepository.save(project);
        return buildMessage(userUpdate.getMessage().getChatId(),
                "Subproject of: %s with name: %s was successfully created".formatted(projectName, subprojectName));
    }
}
