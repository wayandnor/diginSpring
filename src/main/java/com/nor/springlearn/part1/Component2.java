package com.nor.springlearn.part1;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Component2 {
    @EventListener
    public void listenMethod(UserRegisterEvent event){
        System.out.println(event);
    }
}
