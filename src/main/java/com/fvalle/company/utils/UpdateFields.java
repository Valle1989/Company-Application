package com.fvalle.company.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Getter
public class UpdateFields {

    public <T> void updateIfValueIfNotBlankAndNotEqual(T source , T destination, Consumer<T> update, String parameterName){

        if (source != null && !source.equals(destination)){
            if ((source.getClass().equals(String.class) && ((String) source).isBlank()) || source.toString().equalsIgnoreCase("null")){
                throw new IllegalArgumentException("Cannot update " + parameterName + " because it is an empty value");
            }
            update.accept(source);
        }
    }

    public <T> void updateIfValueIfNotBlankAndNotEqualAndNotContainNumbersAndBoolean(T source , T destination, Consumer<T> update, String parameterName){

        if (source != null && !source.equals(destination)){
            if ((source.getClass().equals(String.class) && ((String) source).isBlank()) || source.toString().equalsIgnoreCase("null")){
                throw new IllegalArgumentException("Cannot update " + parameterName + " because it is an empty value");
            }
            if((source.toString().equalsIgnoreCase("true") || source.toString().equalsIgnoreCase("false"))
                    || containNumbers(source.toString())){
                throw new IllegalArgumentException("Cannot update " + parameterName + " because it cannot be a boolean or contains numbers");
            }
            update.accept(source);
        }
    }

    public static boolean containNumbers(String text) {
        return text.chars().anyMatch(Character::isDigit);
    }

}
