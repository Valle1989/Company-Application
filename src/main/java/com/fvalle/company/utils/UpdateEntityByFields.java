package com.fvalle.company.utils;

import com.fvalle.company.exception.BadRequestException;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class UpdateEntityByFields <T,K> {

    /**
     * Method used to make a partial update of the fields of an entity. In addition, it is checked that the
     * fields are not null, empty . Otherwise, an exception is triggered.
     * @param id
     * @param fields
     * @param repository
     * @param entityClass
     * @return
     */
    public T updateFields(Integer id, Map<String,Object> fields, JpaRepository<T,Integer> repository, K entityClass) {
        T entity = repository
                .findById(id).orElseThrow(() -> new NotFoundException("Id not found"));

        List<ErrorDetails> list = new ArrayList<>();
        fields.forEach((key,value) -> {
            if(value == null || value.equals("")){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),key + " field must be send"));
            } else if (value.getClass() != entityClass.getClass()) {
                throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,key + " field must be an String value",list);
            }
            Field field = ReflectionUtils.findField(entity.getClass(),key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, entity, value);
        });
        if(!list.isEmpty()){
            throw new BadRequestException("GSS-400-003",HttpStatus.BAD_REQUEST,"Field or fields cannot be null or an empty value",list);
        }
        return repository.save(entity);
    }


}
