package com.fvalle.company.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageExtensionValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageExtension {

    String message() default "Invalid image URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
