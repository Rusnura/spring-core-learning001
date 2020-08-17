package quoter.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import quoter.annotations.InjectRandom;
import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            InjectRandom injectRandomAnnotation = field.getAnnotation(InjectRandom.class);
            if (injectRandomAnnotation != null) {
                int min = injectRandomAnnotation.min();
                int max = injectRandomAnnotation.max();
                Random generator = new Random(System.currentTimeMillis());
                int random = min + generator.nextInt(max - min);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, random);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
