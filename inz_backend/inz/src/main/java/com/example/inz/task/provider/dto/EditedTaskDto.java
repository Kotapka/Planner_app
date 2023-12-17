package com.example.inz.task.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditedTaskDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private String description;
    private String category;
    private String task;
    private String login;
}
