package com.rumenz;



import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;


public class DemoApplication {

    public static void main(String[] args) {
         AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext();
         ac.register(DemoApplication.class);
         ac.refresh();
         //通过BeanFactory#getBean 单一类型查找/不安全
         byBeanFactory(ac);

         //通过ObjectFactory#getObject 单一类型查找/不安全
         byObjectFactory(ac);

         //通过ObjectProvider#getIfAvailable 单一类型查找/安全
         byObjectProvider(ac);

         // 通过ListableBeanFactory#getBeansOfType 集合类型查找
         byListableBeanFactory(ac);

         // 通过ObjectProvider#stream
         byObjectProviderStream(ac);


         ac.close();
    }

    private static void byObjectProviderStream(AnnotationConfigApplicationContext ac) {
        ObjectProvider<Rumenz> beanProvider = ac.getBeanProvider(Rumenz.class);
        printBeanException("byObjectProviderStream",()->beanProvider.stream().forEach(System.out::println));
    }

    private static void byListableBeanFactory(AnnotationConfigApplicationContext ac) {
        printBeanException("byListableBeanFactory",()->ac.getBeansOfType(Rumenz.class));

    }

    private static void byObjectProvider(AnnotationConfigApplicationContext ac) {
        ObjectProvider<Rumenz> beanProvider = ac.getBeanProvider(Rumenz.class);
        printBeanException("byObjectProvider",()->beanProvider.getIfAvailable());
    }

    private static void byObjectFactory(AnnotationConfigApplicationContext ac) {
        ObjectProvider<Rumenz> beanProvider = ac.getBeanProvider(Rumenz.class);
        printBeanException("byObjectFactory",()->beanProvider.getObject());
    }

    private static void byBeanFactory(AnnotationConfigApplicationContext ac) {
        printBeanException("byBeanFactory",()->ac.getBean(Rumenz.class));
    }

    private static void printBeanException(String msg,  Runnable runnable) {
        System.err.println("from---->>>"+msg);
        try{
            runnable.run();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
