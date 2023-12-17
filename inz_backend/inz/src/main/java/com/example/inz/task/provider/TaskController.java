package com.example.inz.task.provider;

import com.example.inz.category.dto.CategoryDto;
import com.example.inz.customer.operation.dto.UserLoginDto;
import com.example.inz.task.provider.domain.TaskProviderFacade;
import com.example.inz.task.provider.dto.AssignedTaskDto;
import com.example.inz.task.provider.dto.LoginCategoryDto;
import com.example.inz.task.provider.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class TaskController {

    TaskProviderFacade taskProviderFacade;

    @Autowired
    public TaskController(TaskProviderFacade taskProviderFacade) {
        this.taskProviderFacade = taskProviderFacade;
    }

    @PostMapping(value = "/addTask", produces = "application/json")
    @Operation(summary = "add new task")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
        TaskDto task = taskProviderFacade.saveTask(taskDto);
        return ResponseEntity.ok(task);
    }

    @PostMapping(value = "/user/getTasks", consumes = "application/json", produces = "application/json")
    @Operation(summary = "get tasks by user")
    public ResponseEntity<List<TaskDto>> getTaskByUser(@RequestBody UserLoginDto user) {
        List<TaskDto> task = taskProviderFacade.getTaskByUser(user);
        return ResponseEntity.ok(task);
    }

    @PostMapping(value = "/user/getTasksByCategory", consumes = "application/json", produces = "application/json")
    @Operation(summary = "get tasks by user and category")
    public ResponseEntity<List<TaskDto>> getTaskByUserAndCategory(@RequestBody LoginCategoryDto user) {
        List<TaskDto> task = taskProviderFacade.getTaskByUserAndCategory(user);
        return ResponseEntity.ok(task);
    }

}