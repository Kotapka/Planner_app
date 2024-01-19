//package com.example.inz.statistics.domain;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//
//public class StatisticsFacadeConfiguration {
//    StatisticsRepository statisticsRepository;
//
//    @Autowired
//    StatisticsFacadeConfiguration(StatisticsRepository statisticsRepository){
//        this.statisticsRepository = statisticsRepository;
//    }
//    @Bean
//    StatisticsFacade statisticsFacade(){
//        return statisticsFacade(statisticsRepository);
//    }
//
//    static StatisticsFacade statisticsFacade(StatisticsRepository statisticsRepository){
//        return new StatisticsFacade(statisticsRepository);
//    }
//}
