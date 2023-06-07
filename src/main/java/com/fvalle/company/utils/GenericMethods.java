package com.fvalle.company.utils;

import java.util.Map;

public interface GenericMethods<T> {

    public T updateEntityByField(Integer id, Map<String,Object> fields);
}
