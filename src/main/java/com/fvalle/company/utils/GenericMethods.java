package com.fvalle.company.utils;

import java.util.Map;
import java.util.function.Consumer;

public interface GenericMethods<T> {

    /**
     *
     * @param id
     * @param fields
     * @return Entidad de tipo T
     */
    public T updateEntityByField(Integer id, Map<String,Object> fields);

}
