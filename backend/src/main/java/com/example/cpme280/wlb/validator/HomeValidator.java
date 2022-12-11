package com.example.cpme273.wlb.validator;

import com.example.cpme273.wlb.dto.HomeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class HomeValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return HomeDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String a = "a";
    }
}
