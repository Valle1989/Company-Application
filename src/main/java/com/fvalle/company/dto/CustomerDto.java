package com.fvalle.company.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    @NotNull(message = "customerName field cannot be null")
    private String customerName;

    @NotNull(message = "customerAddress field cannot be null")
    private String customerAddress;

    @NotNull(message = "customerCity field cannot be null")
    private String customerCity;

    @NotNull(message = "postalCode field cannot be null")
    private String postalCode;

    @NotNull(message = "country field cannot be null")
    private String country;

    @NotNull(message = "customerPhone field cannot be null")
    @Pattern(regexp = "^(?:(?:\\+|00)54[-\\s]?)?(?:\\()?0?(?:11|[2368]\\d)(?:\\))?(?:[-\\s]?\\d{4}){2}$",
            message = "The entered customerPhone number is invalid.")
    private String customerPhone;
}
