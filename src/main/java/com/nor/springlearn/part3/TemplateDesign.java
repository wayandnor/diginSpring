package com.nor.springlearn.part3;

import java.util.ArrayList;
import java.util.List;

public class TemplateDesign {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public void inject(Object bean) {
                System.out.println("inject" + bean);
            }
        });
        System.out.println(beanFactory.getBean());
    }

    static class MyBeanFactory {
        private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

        public Object getBean() {
            Object bean = new Object();
            System.out.println("构造" + bean);
            for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                beanPostProcessor.inject(bean);
            }
            System.out.println("依赖注入" + bean);
            System.out.println("初始化");
            return bean;
        }

        public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
            beanPostProcessors.add(postProcessor);
        }
    }

    interface BeanPostProcessor {
        void inject(Object bean);
    }
}


