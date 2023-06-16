package com.fvalle.company.utils;

import java.util.Map;

public interface GenericMethods<T> {

    /**
     *
     * @param id
     * @param fields
     * @return Entidad de tipo T
     */
    public T updateEntityByField(Integer id, Map<String,Object> fields);
}
