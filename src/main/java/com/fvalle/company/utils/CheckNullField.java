package com.fvalle.company.utils;

import com.fvalle.company.exception.ErrorDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CheckNullField {

    private static Log logger = LogFactory.getLog(CheckNullField.class);

    public static <T> boolean checkError (Predicate<T> value, T body){
        return value.test(body);
    }

    public static <T> void checkIfIsNull(T parameter, String field, Predicate<T> value, String regex, List<ErrorDetails> list){
        Optional<T> optional = Optional.ofNullable(parameter);
        if(optional.isPresent()){
            if(value.test(parameter) || !isValid(parameter.toString(), regex)){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field is mandatory. However, " +
                        "it cannot be an empty string, numbers are not allowed, and null cannot be entered."));
                logger.error("Error, el campo " + field.toLowerCase() + " debe mandarse obligatoriamente");
            }
        }else{
            list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field cannot be null"));
            logger.error("Error, el campo " + field.toLowerCase() + " no debe ser nulo");
        }
    }

    public static <T> void checkIfIsNullWithoutRegExp(T parameter, String field, Predicate<T> value, List<ErrorDetails> list){
        Optional<T> optional = Optional.ofNullable(parameter);
        if(optional.isPresent()){
            if(value.test(parameter)){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field is mandatory. However, " +
                        "it cannot be an empty string, numbers are not allowed, and null cannot be entered."));
                logger.error("Error, el campo " + field.toLowerCase() + " debe mandarse obligatoriamente");
            }
        }else{
            list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field cannot be null"));
            logger.error("Error, el campo " + field.toLowerCase() + " no debe ser nulo");
        }
    }

    public static boolean isValid(String data, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
