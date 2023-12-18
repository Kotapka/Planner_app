package com.example.inz

import com.example.inz.category.domain.CategoryOperationFacade
import com.example.inz.category.domain.CategoryRepository
import com.example.inz.category.dto.CategoryDto
import com.example.inz.customer.operation.domain.CustomerRepository
import com.example.inz.customer.operation.exception.HttpException
import com.example.inz.task.provider.domain.AssignedTaskRepository
import com.example.inz.task.provider.domain.TaskProviderFacade
import com.example.inz.task.provider.domain.TaskRepository
import com.example.inz.task.provider.dto.AssignedTaskDto
import com.example.inz.task.provider.dto.TaskDto
import spock.lang.Specification

import java.text.SimpleDateFormat

class CategoryAndTaskSpec extends Specification implements Samples {

    CategoryRepository categoryRepository = new InMemoryCategoryRepository()
    CustomerRepository customerRepository = new InMemoryCustomerRepository()
    AssignedTaskRepository assignedTaskRepository = new InMemoryAssignedTaskRepository()
    TaskRepository taskRepository = new InMemoryTaskRepository()
    CategoryOperationFacade categoryOperationFacade = new CategoryOperationFacade(categoryRepository, customerRepository)
    TaskProviderFacade taskProviderFacade = new TaskProviderFacade(taskRepository, categoryRepository, customerRepository, assignedTaskRepository)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    //1
    def "Should create category with name"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        when: "Category with name 'school' is created"
        CategoryDto schoolCategoryDto = CategoryDto.builder().name("school").user("JAN").build()
        CategoryDto schoolCategory = categoryOperationFacade.saveCategory(schoolCategoryDto)
        then: "Category exist"
        assert schoolCategory.name == "school"
    }
    //2
    def "Should not create category without name"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        when: "Category without name is created"
        CategoryDto schoolCategoryDto = CategoryDto.builder().name("").user("JAN").build()
        categoryOperationFacade.saveCategory(schoolCategoryDto)
        then: "Exception is thrown"
        thrown(HttpException)
    }
    //3
    def "Should not create category if category with same name for specific user arleady exist"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryOperationFacade.saveCategory(CATEGORY_DTO_EXAMPLE)
        when: "User try to add category again"
        categoryOperationFacade.saveCategory(CATEGORY_DTO_EXAMPLE)
        then: "Exception is thrown"
        thrown(HttpException)
    }
    //4
    def "Should create task with category"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryOperationFacade.saveCategory(CATEGORY_DTO_EXAMPLE)
        when: "Task with name 'math' is created"
        TaskDto math = TaskDto.builder().user("JAN").category("school").name("math").build()
        taskProviderFacade.saveTask(math)
        then: "Task is saved"
        assert math.name == "math"
        assert math.category == "school"
    }
    //5
    def "Should not create task without category"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryOperationFacade.saveCategory(CATEGORY_DTO_EXAMPLE)
        when: "Task with name 'math' is created"
        TaskDto math = TaskDto.builder().user("JAN").category("").name("math").build()
        taskProviderFacade.saveTask(math)
        then: "Exception is thrown"
        thrown(HttpException)
    }
    //6
    def "Should not create task again for specific user"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryOperationFacade.saveCategory(CATEGORY_DTO_EXAMPLE)
        and: "Task with name 'math' is created"
        taskProviderFacade.saveTask(TASK_DTO_EXAMPLE)
        when: "User try to add task that already exist in category"
        taskProviderFacade.saveTask(TASK_DTO_EXAMPLE)
        then: "Exception is thrown"
        thrown(HttpException)
    }
    //7
    def "Should assign task to user"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryRepository.save(CATEGORY_EXAMPLE)
        and: "Task with name 'math' is created"
        taskRepository.save(TASK_EXAMPLE)
        when: "User assign task"
        AssignedTaskDto assignedTaskDto = AssignedTaskDto.builder()
                .startDate(sdf.parse("2023-12-18T10:20:02.245Z"))
                .endDate(sdf.parse("2023-12-18T10:26:02.245Z"))
                .description("description")
                .category(CATEGORY_EXAMPLE.getName())
                .task(TASK_EXAMPLE.getName())
                .user(USER_EXAMPLE.getLogin())
                .build()
        taskProviderFacade.saveAssignedTask(assignedTaskDto)
        then: "Task should be assigned"
        assert assignedTaskDto.task == "math"
        assert assignedTaskDto.category == "school"
    }
    //8
    def "Should not assign task to user without task chosen"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryRepository.save(CATEGORY_EXAMPLE)
        and: "Task with name 'math' is created"
        taskRepository.save(TASK_EXAMPLE)
        when: "User assign task"
        AssignedTaskDto assignedTaskDto = AssignedTaskDto.builder()
                .startDate(sdf.parse("2023-12-18T10:20:02.245Z"))
                .endDate(sdf.parse("2023-12-18T10:26:02.245Z"))
                .description("description")
                .category(CATEGORY_EXAMPLE.getName())
                .task("")
                .user(USER_EXAMPLE.getLogin())
                .build()
        taskProviderFacade.saveAssignedTask(assignedTaskDto)
        then: "Exception is thrown"
        thrown(HttpException)
    }
    //9
    def "Should not assign task to user without category chosen"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryRepository.save(CATEGORY_EXAMPLE)
        and: "Task with name 'math' is created"
        taskRepository.save(TASK_EXAMPLE)
        when: "User assign task"
        AssignedTaskDto assignedTaskDto = AssignedTaskDto.builder()
                .startDate(sdf.parse("2023-12-18T10:20:02.245Z"))
                .endDate(sdf.parse("2023-12-18T10:26:02.245Z"))
                .description("description")
                .category("")
                .task(TASK_EXAMPLE.getName())
                .user(USER_EXAMPLE.getLogin())
                .build()
        taskProviderFacade.saveAssignedTask(assignedTaskDto)
        then: "Exception is thrown"
        thrown(HttpException)
    }
    //10
    def "Should not assign task to user if data is wrong"() {
        given: "User exist"
        customerRepository.save(USER_EXAMPLE)
        and: "Category school exist"
        categoryRepository.save(CATEGORY_EXAMPLE)
        and: "Task with name 'math' is created"
        taskRepository.save(TASK_EXAMPLE)
        when: "User assign task"
        AssignedTaskDto assignedTaskDto = AssignedTaskDto.builder()
                .startDate(sdf.parse(START_DATE))
                .endDate(sdf.parse(END_DATE))
                .description("description")
                .category(CATEGORY_EXAMPLE.getName())
                .task(TASK_EXAMPLE.getName())
                .user(USER_EXAMPLE.getLogin())
                .build()
        taskProviderFacade.saveAssignedTask(assignedTaskDto)
        then: "Exception is thrown"
        thrown(HttpException)
        where:
        START_DATE | END_DATE
        "2023-12-11T10:20:02.245Z" | "2023-12-18T10:26:02.245Z"
        "2023-12-18T10:27:02.245Z" | "2023-12-18T10:26:02.245Z"

    }

}
