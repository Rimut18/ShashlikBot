package com.rimut.db.microservice.mappers;

public interface BaseMapper<E, T> {

    E ToEntity(T t);

    T ToDto(E e);
}
