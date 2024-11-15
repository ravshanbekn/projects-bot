package com.projectsbot.command;

import com.projectsbot.builder.SendMessageBuilder;
import com.projectsbot.entity.Project;
import com.projectsbot.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class RemoveElementCommand implements Command, SendMessageBuilder {
    private final ProjectRepository projectRepository;

    @Override
    public CommandIdentifier getIdentifier() {
        return CommandIdentifier.REMOVE_ELEMENT;
    }

    @Override
    @Transactional
    public SendMessage executeCommand(Update userUpdate) {
        String message = userUpdate.getMessage().getText();
        String[] args = message.split("\\s+");
        String projectNameToRemove = args[1];
        Project projectToRemove = projectRepository.findByName(projectNameToRemove).orElseThrow(
                () -> new EntityNotFoundException("Project with name: %s was not found"));
        projectRepository.delete(projectToRemove);
        return buildMessage(userUpdate.getMessage().getChatId(),
                "Project with name: %s was successfully deleted".formatted(projectNameToRemove));
    }
}
