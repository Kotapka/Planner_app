package com.example.inz.statistics;

import com.example.inz.statistics.dto.StatisticsDto;
import com.example.inz.statistics.dto.StatisticsTaskDto;
import com.example.inz.task.provider.domain.TaskProviderFacade;
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
public class StatisticsController {

    TaskProviderFacade taskProviderFacade;
    @Autowired
    public StatisticsController(TaskProviderFacade taskProviderFacade) {
        this.taskProviderFacade = taskProviderFacade;
    }

    @PostMapping(value = "/getStatistics", consumes = "application/json", produces = "application/json")
    @Operation(summary = "get statistics by time")
    public ResponseEntity<List<StatisticsTaskDto>> getStatistics(@RequestBody StatisticsDto statisticsDto) {
        List<StatisticsTaskDto> task = taskProviderFacade.getStatistics(statisticsDto);
        return ResponseEntity.ok(task);
    }

}
