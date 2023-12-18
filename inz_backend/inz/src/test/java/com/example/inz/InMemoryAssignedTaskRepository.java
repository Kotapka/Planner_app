package com.example.inz;

import com.example.inz.task.provider.domain.AssignedTask;
import com.example.inz.task.provider.domain.AssignedTaskRepository;
import com.example.inz.task.provider.domain.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryAssignedTaskRepository implements AssignedTaskRepository {

    List<AssignedTask> task = new ArrayList<>();

    @Override
    public List<AssignedTask> getAssignedByUserId(Long userId) {
        return null;
    }

    @Override
    public Optional<AssignedTask> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AssignedTask> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AssignedTask> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<AssignedTask> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AssignedTask getOne(Long aLong) {
        return null;
    }

    @Override
    public AssignedTask getById(Long aLong) {
        return null;
    }

    @Override
    public AssignedTask getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends AssignedTask> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AssignedTask> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AssignedTask> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AssignedTask> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AssignedTask> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AssignedTask> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AssignedTask, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends AssignedTask> S save(S entity) {
        task.add(entity);
        return entity;
    }

    @Override
    public <S extends AssignedTask> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<AssignedTask> findAll() {
        return null;
    }

    @Override
    public List<AssignedTask> findAllById(Iterable<Long> longs) {
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
    public void delete(AssignedTask entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends AssignedTask> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<AssignedTask> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<AssignedTask> findAll(Pageable pageable) {
        return null;
    }
}
