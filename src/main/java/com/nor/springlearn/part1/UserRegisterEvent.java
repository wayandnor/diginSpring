package com.nor.springlearn.part1;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class UserRegisterEvent extends ApplicationEvent {
    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
