package com.example.cpme273.wlb.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

@ApiModel
public enum OfferType {
    RENT_OUT("rent"),
    SELL("buy");

    @Getter
    private final String correspondingAction;

    OfferType(String correspondingAction) {
        this.correspondingAction = correspondingAction;
    }
}