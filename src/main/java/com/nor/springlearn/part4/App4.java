package com.nor.springlearn.part4;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

public class App4 {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);


        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());  // 获取字符串类型需要
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);  // 解析AutoWired和Value

        context.registerBean(CommonAnnotationBeanPostProcessor.class);  // 解析Resource、PostConstruct、PreDestroy
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());  // 解析ConfigurationProperties
        context.refresh();
        System.out.println(context.getBean("bean4"));
        context.close();
    }
}
