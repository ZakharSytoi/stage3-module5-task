package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDBRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {
    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<T> entityClass;
    private final Class<K> idClass;

    protected AbstractDBRepository() {
        ParameterizedType type = ((ParameterizedType) getClass()
                .getGenericSuperclass());
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        idClass = (Class<K>) type.getActualTypeArguments()[1];
    }

    protected abstract void updateEntity(T dbEntity, T newEntity);
    @Override
    public List<T> readAll() {
        return entityManager.createQuery("SELECT e FROM "
                + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return readById(entity.getId()).map(dbEntity -> {
            updateEntity(dbEntity, entity);
            T returnEntity = entityManager.merge(dbEntity);
            entityManager.flush();
            return returnEntity;
        }).orElse(null);
    }

    @Override
    public Boolean deleteById(K id) {
        T entityRef;
        if ((entityRef = entityManager.getReference(entityClass, id)) != null) {
            entityManager.remove(entityRef);
            return true;
        }
        else return false;
    }

    @Override
    public Boolean existById(K id) {
        return (entityManager.getReference(entityClass, id)) != null;
    }
    @Override
    public T getReference(K id){
        return entityManager.getReference(entityClass, id);
    }
}
