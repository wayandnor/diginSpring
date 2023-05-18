package com.nor.springlearn.part1;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;

public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class)
                        .setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config", beanDefinition);

        // 给beanFactory添加处理注解的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
        // 注意此时虽然已经把Bean添加进了BeanFactory，但是没有完成依赖注入。
        System.out.println("添加bean后处理器后：" + beanFactory.getBean(Bean1.class).getBean2());

        // bean后处理器，针对bean的各个生命周期提供扩展
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        System.out.println("添加bean后处理器后：" + beanFactory.getBean(Bean1.class).getBean2()); // 如果上面调用getBean这个时候调用已经不会依赖注入了。

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        beanFactory.preInstantiateSingletons(); // 提前创建Bean实例
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

        /**
         * 总结
         * 一、BeanFactory不会自动做的事情
         * 1. 不会主动调用BeanFactory的后处理器，也就是不会扫描注解添加bean定义等
         * 2. 不会主动添加bean的后处理器，完成比如依赖注入等功能
         * 3. 不会主动实例化单例对象
         * 4. 不会解beanFactory，还不会解析${ }与 # { }
         * */
    }
}

@Configuration
class Config {
    @Bean
    public Bean1 Bean1() {
        return new Bean1();
    }

    @Bean
    public Bean2 Bean2() {
        return new Bean2();
    }
}

class Bean1 {
    @Resource
    private Bean2 bean2;

    public Bean1() {
        System.out.println("构造Bean1");

    }

    public Bean2 getBean2() {
        return bean2;
    }
}

class Bean2 {
    public Bean2() {
        System.out.println("构造Bean2");
    }
}
