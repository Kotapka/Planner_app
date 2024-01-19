package com.example.inz.task.provider.domain;

import com.example.inz.category.domain.Category;
import com.example.inz.category.domain.CategoryRepository;
import com.example.inz.customer.operation.domain.Customer;
import com.example.inz.customer.operation.domain.CustomerRepository;
import com.example.inz.customer.operation.dto.UserLoginDto;
import com.example.inz.customer.operation.exception.HttpException;
import com.example.inz.statistics.dto.StatisticsDto;
import com.example.inz.statistics.dto.StatisticsTaskDto;
import com.example.inz.task.provider.dto.AssignedTaskDto;
import com.example.inz.task.provider.dto.EditedTaskDto;
import com.example.inz.task.provider.dto.LoginCategoryDto;
import com.example.inz.task.provider.dto.TaskDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskProviderFacade {

    TaskRepository taskRepository;
    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;
    AssignedTaskRepository assignedTaskRepository;

    @Autowired
    public TaskProviderFacade(TaskRepository taskRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository, AssignedTaskRepository assignedTaskRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.assignedTaskRepository = assignedTaskRepository;
    }


    public TaskDto saveTask(TaskDto taskDto) {
        Optional<Customer> customerId = customerRepository.findByLogin(taskDto.getUser());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> categoryId = categoryRepository.findByNameAndUser(customerId.get().getId(),taskDto.getCategory());
        if (categoryId.isEmpty()) {
            throw new HttpException("Error with category", HttpStatus.BAD_REQUEST);
        }
        Optional<Task> doesTaskExist = taskRepository.findByNameAndUserAndCategory(customerId.get().getId(),taskDto.getName(),categoryId.get().getId());
        if (doesTaskExist.isPresent()) {
            throw new HttpException("Task already exists", HttpStatus.BAD_REQUEST);
        }
        Task savedTask = taskRepository.save(Task.builder()
                .name(taskDto.getName())
                .category(categoryId.get())
                .user(customerId.get())
                .isActive(false)
                .build());

        return TaskDto.builder().name(savedTask.getName()).build();
    }

    public List<TaskDto> getTaskByUser(UserLoginDto user) {
        Optional<Customer> customerId = customerRepository.findByLogin(user.getLogin());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }
        List<Task> category = taskRepository.getTaskByUserId(customerId.get().getId());

        List<TaskDto> taskListDto = category.stream()
                .map(this::mapToDto)
                .toList();

        return taskListDto;
    }
    public List<TaskDto> getTaskByUserAndCategory(LoginCategoryDto user) {
        Optional<Customer> customerId = customerRepository.findByLogin(user.getLogin());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> category = categoryRepository.findByName(user.getCategory());
        if (category.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }

        List<Task> tasks = taskRepository.getTaskByUserIdAndCategory(customerId.get().getId(),category.get().getId());

        List<TaskDto> taskListDto = tasks.stream()
                .map(this::mapToDto)
                .toList();

        return taskListDto;
    }
    private TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .name(task.getName())
                .category(task.getCategory().getName())
                .user(task.getUser().getLogin())
                .build();
    }

    public AssignedTaskDto saveAssignedTask(AssignedTaskDto task) {
        Optional<Customer> customerId = customerRepository.findByLogin(task.getUser());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> categoryId = categoryRepository.findByNameAndUser(customerId.get().getId(),task.getCategory());
//        if (categoryId.isEmpty()) {
//            throw new HttpException("Error with category", HttpStatus.BAD_REQUEST);
//        }
        Optional<Task> optionalTask = taskRepository.findByNameAndUserAndCategory(customerId.get().getId(),task.getTask(),categoryId.get().getId());
//        if (optionalTask.isEmpty()) {
//            throw new HttpException("Task doesn't exists", HttpStatus.BAD_REQUEST);
//        }
        if(!task.getStartDate().before(task.getEndDate())){
            throw new HttpException("Date error", HttpStatus.BAD_REQUEST);
        }
        long diffMillis = Math.abs(task.getStartDate().getTime() - task.getEndDate().getTime());
        long diffHours = diffMillis / (60 * 60 * 1000);
        if(diffHours > 24){
            throw new HttpException("Date too long", HttpStatus.BAD_REQUEST);
        }


        AssignedTask savedAssignedTask = assignedTaskRepository.save(AssignedTask.builder()
                .category(categoryId.get())
                .user(customerId.get())
                .task(optionalTask.get())
                .description(task.getDescription())
                .endDate(task.getEndDate())
                .startDate(task.getStartDate())
                .isActive(true)
                .build());

        return AssignedTaskDto.builder()
                .category(savedAssignedTask.getCategory().getName())
                .user(savedAssignedTask.getUser().getLogin())
                .task(savedAssignedTask.getTask().getName())
                .description(savedAssignedTask.getDescription())
                .endDate(savedAssignedTask.getEndDate())
                .startDate(savedAssignedTask.getStartDate())
                .isActive(savedAssignedTask.isActive())
                .build();
    }

    public List<AssignedTaskDto> getAssignedTaskListByUser(UserLoginDto user) {
        Optional<Customer> customerId = customerRepository.findByLogin(user.getLogin());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }

        List<AssignedTask> tasks = assignedTaskRepository.getAssignedByUserId(customerId.get().getId());

        List<AssignedTaskDto> taskListDto = tasks.stream()
                .map(this::mapToAssignedTaskDto)
                .toList();

        return taskListDto;
    }

    private AssignedTaskDto mapToAssignedTaskDto(AssignedTask task){
    return AssignedTaskDto.builder()
            .category(task.getCategory().getName())
            .task(task.getTask().getName())
            .user(task.getUser().getLogin())
            .isActive(task.isActive())
            .startDate(task.getStartDate())
            .endDate(task.getEndDate())
            .description(task.getDescription())
            .id(task.getId())
            .build();
    }

    public AssignedTaskDto deleteAssignedTask(Long assignedTaskDto) {
        Optional<AssignedTask> task = assignedTaskRepository.findById(assignedTaskDto);
        if (task.isEmpty()) {
            throw new HttpException("Error with task", HttpStatus.BAD_REQUEST);
        }
        AssignedTask assignedTask = task.get();
        assignedTask.setActive(false);

        assignedTaskRepository.save(assignedTask);

        return AssignedTaskDto.builder()
                .category(assignedTask.getCategory().getName())
                .task(assignedTask.getTask().getName())
                .user(assignedTask.getUser().getLogin())
                .isActive(assignedTask.isActive())
                .startDate(assignedTask.getStartDate())
                .endDate(assignedTask.getEndDate())
                .description(assignedTask.getDescription())
                .id(assignedTask.getId())
                .build();
    }

    public EditedTaskDto editAssignedTask(EditedTaskDto assignedTaskDto) {
        Optional<AssignedTask> task = assignedTaskRepository.findById(assignedTaskDto.getId());
        if (task.isEmpty()) {
            throw new HttpException("Error with task", HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> customerId = customerRepository.findByLogin(assignedTaskDto.getLogin());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }
        Optional<Category> categoryId = categoryRepository.findByNameAndUser(customerId.get().getId(), assignedTaskDto.getCategory());
        if (categoryId.isEmpty()) {
            throw new HttpException("Error with category", HttpStatus.BAD_REQUEST);
        }
        Optional<Task> taskId = taskRepository.findByNameAndUser(customerId.get().getId(), assignedTaskDto.getTask());
        if (taskId.isEmpty()) {
            throw new HttpException("Error with task", HttpStatus.BAD_REQUEST);
        }


        AssignedTask assignedTask = task.get();
        assignedTask.setCategory(categoryId.get());
        assignedTask.setTask(taskId.get());
        assignedTask.setEndDate(assignedTaskDto.getEndDate());
        assignedTask.setStartDate(assignedTaskDto.getStartDate());
        assignedTask.setDescription(assignedTaskDto.getDescription());

        assignedTaskRepository.save(assignedTask);

        return EditedTaskDto.builder()
                .category(assignedTask.getCategory().getName())
                .task(assignedTask.getTask().getName())
                .startDate(assignedTask.getStartDate())
                .endDate(assignedTask.getEndDate())
                .description(assignedTask.getDescription())
                .id(assignedTask.getId())
                .build();
    }

    public List<StatisticsTaskDto> getStatistics(StatisticsDto statisticsDto) {
        Optional<Customer> customerId = customerRepository.findByLogin(statisticsDto.getUser());
        if (customerId.isEmpty()) {
            throw new HttpException("Error with user", HttpStatus.BAD_REQUEST);
        }

        List<AssignedTask> tasks = assignedTaskRepository.getAssignedByUserId(customerId.get().getId());

        return generateStatistics(statisticsDto.getStartDate(), statisticsDto.getEndDate(), tasks);
    }

    private List<StatisticsTaskDto> generateStatistics(Date startDate, Date endDate, List<AssignedTask> tasks) {
        Map<String, List<AssignedTask>> groupedTasks = tasks.stream()
                .filter(task -> task.isActive() && isTaskWithinDateRange(task, startDate, endDate))
                .collect(Collectors.groupingBy(
                        task -> task.getCategory().getName() + task.getTask().getName()
                ));

        return groupedTasks.values().stream()
                .map(this::mergeTasks)
                .collect(Collectors.toList());
    }

    private boolean isTaskWithinDateRange(AssignedTask task, Date startDate, Date endDate) {
        Date taskStartDate = task.getStartDate();
        return taskStartDate != null && !taskStartDate.before(startDate) && !taskStartDate.after(endDate);
    }

    private StatisticsTaskDto mergeTasks(List<AssignedTask> tasksWithSameCategoryAndTask) {
        Duration totalDuration = tasksWithSameCategoryAndTask.stream()
                .map(task -> calculateTaskDuration(task.getStartDate(), task.getEndDate()))
                .reduce(Duration.ZERO, Duration::plus);

        AssignedTask sampleTask = tasksWithSameCategoryAndTask.get(0); // We assume at least one task in the list

        return StatisticsTaskDto.builder()
                .category(sampleTask.getCategory().getName())
                .task(sampleTask.getTask().getName())
                .duration((int) totalDuration.toMinutes())
                .build();
    }

    private Duration calculateTaskDuration(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            return Duration.between(startDate.toInstant(), endDate.toInstant());
        } else {
            return Duration.ZERO;
        }
    }
}