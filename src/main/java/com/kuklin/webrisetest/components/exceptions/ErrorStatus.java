package com.kuklin.webrisetest.components.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "User not found!"),
    SERVICE_PLAN_NOT_FOUND(HttpStatus.BAD_REQUEST, "The application does not support working with this service"),
    SUBSCRIPTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "Subscription not found!"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Email already exists!"),
    NOT_VALID_EMAIL(HttpStatus.BAD_REQUEST, " Not valid email")
    ;

    private HttpStatus httpStatus;
    private String message;
}
