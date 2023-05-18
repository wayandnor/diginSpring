package com.nor.springlearn.part1;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class SpringOneApplication {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringOneApplication.class, args);
        System.out.println(context);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        // BeanFactory的主要实现类
        Field field = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        field.setAccessible(true);
        Map<String, Object> map = (Map<String, Object>) field.get(beanFactory);
        map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
                        .forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));


        // ApplicationContext继承的四个接口实现的四个功能
        // 1.国际化
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));

        // 2.通配符寻找配置资源文件
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for(Resource re : resources){
            System.out.println(re);
        }

        // 3.获取环境变量
        ConfigurableEnvironment environment = context.getEnvironment();
        System.out.println(environment.getProperty("java_home"));
        System.out.println(environment.getProperty("logging.charset.console"));

        // 4.发布事件
        context.publishEvent(new UserRegisterEvent(context));
    }

}
