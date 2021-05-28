package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String toCC;
    private final String subject;
    private final String message;


}
