package com.example.inz;

import com.example.inz.customer.operation.domain.Customer;
import com.example.inz.task.provider.domain.Task;
import com.example.inz.task.provider.domain.TaskRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryTaskRepository implements TaskRepository {
    List<Task> task = new ArrayList<>();

    @Override
    public Optional<Task> findByName(String name) {
        return task.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Task> findByNameAndUser(Long userId, String name) {
        return task.stream()
                .filter(c -> c.getName().equals(name))
                .filter(c -> Objects.equals(c.getUser().getId(), userId))
                .findFirst();
    }

    @Override
    public Optional<Task> findByNameAndUserAndCategory(Long userId, String name, Long category) {
        return task.stream()
                .filter(c -> c.getName().equals(name))
                .filter(c -> Objects.equals(c.getUser().getId(), userId))
                .filter(c -> Objects.equals(c.getCategory().getId(), category))
                .findFirst();
    }

    @Override
    public List<Task> getTaskByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Task> getTaskByUserIdAndCategory(Long userId, Long categoryId) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Task> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Task> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Task> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Task getOne(Long aLong) {
        return null;
    }

    @Override
    public Task getById(Long aLong) {
        return null;
    }

    @Override
    public Task getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Task> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Task> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Task> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Task> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Task> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Task> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Task, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Task> S save(S entity) {
        task.add(entity);
        return entity;
    }

    @Override
    public <S extends Task> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Task> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Task> findAll() {
        return null;
    }

    @Override
    public List<Task> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Task entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Task> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Task> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return null;
    }
}
