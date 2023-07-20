package com.fvalle.company.utils;

import com.fvalle.company.entity.AuditableEntity;
import com.fvalle.company.entity.Customer;
import com.fvalle.company.exception.ErrorDetails;
import com.fvalle.company.exception.ValueExistException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.repository.JpaRepository;
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

    /**
     * This method checks if the value returns true or false depending on the condition to evaluate.
     * @param value
     * @param body
     * @return true or false
     * @param <T>
     */
    public static <T> boolean checkError (Predicate<T> value, T body){
        return value.test(body);
    }

    /**
     * Method used to check if the value of the entity field of type T is null or empty. In addition,
     * the value of the field is checked for compliance with a given regular expression.
     * If not, an exception is thrown.
     * @param parameter
     * @param field
     * @param value
     * @param regex
     * @param list
     * @param <T>
     */
    public static <T> void checkIfIsNull(T parameter, String field, Predicate<T> value, String regex, List<ErrorDetails> list){
        Optional<T> optional = Optional.ofNullable(parameter);
        if(optional.isPresent()){
            if(value.test(parameter) || !isValid(parameter.toString(), regex)){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field is mandatory. However, " +
                        "it cannot be an empty string, numbers, dates and boolean values are not allowed, and null cannot be entered."));
                logger.error("Error, el campo " + field.toLowerCase() + " debe mandarse obligatoriamente");
            }
        }else{
            list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field cannot be null"));
            logger.error("Error, el campo " + field.toLowerCase() + " no debe ser nulo");
        }
    }

    /**
     * Method used to check if the value of the entity field of type T is null or empty.
     * If not, an exception is thrown.
     * @param parameter
     * @param field
     * @param value
     * @param list
     * @param <T>
     */
    public static <T> void checkIfIsNullWithoutRegExp
    (T parameter, String field, Predicate<T> value, List<ErrorDetails> list){
        Optional<T> optional = Optional.ofNullable(parameter);
        if(optional.isPresent()){
            if(value.test(parameter)){
                list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field is mandatory. However, " +
                        "it cannot be an empty string, numbers, dates and boolean values are not allowed, and null cannot be entered."));
                logger.error("Error, el campo " + field.toLowerCase() + " debe mandarse obligatoriamente");
            }
        }else{
            list.add(new ErrorDetails(HttpStatus.BAD_REQUEST.value(),field + " field cannot be null"));
            logger.error("Error, el campo " + field.toLowerCase() + " no debe ser nulo");
        }
    }

    /**
     * Method used to validate if the string received as a parameter matched with the regular expression that must be
     * entered as a parameter too.
     * @param data
     * @param regex
     * @return true or false
     */
    public static boolean isValid(String data, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }

}
