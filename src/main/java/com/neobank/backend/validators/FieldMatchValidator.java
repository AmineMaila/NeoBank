package com.neobank.backend.validators;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraints) {
        this.firstFieldName = constraints.first();
        this.secondFieldName = constraints.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object first = BeanUtils
                .getPropertyDescriptor(value.getClass(), firstFieldName)
                .getReadMethod()
                .invoke(value);

            Object second = BeanUtils
                .getPropertyDescriptor(value.getClass(), secondFieldName) // ->  this method get the property in value object with name matching the second argument
                .getReadMethod() // this method gets the reader method of that property which is get<Property> (the getter)
                .invoke(value); // this method invokes the getter with the object value ex: value.getPassword();
            
            return Objects.equals(first, second);
        } catch (Exception e) {
            return false;
        }
    }
}
