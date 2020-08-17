package quoter.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import quoter.annotations.Profiling;
import quoter.jmx.ProfilingController;
import quoter.jmx.interfaces.ProfilingControllerMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> map = new HashMap<>();
    private final ProfilingController controller = new ProfilingController();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        StandardMBean mBean = new StandardMBean(controller, ProfilingControllerMBean.class);
        mBeanServer.registerMBean(mBean, new ObjectName("Profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                if (controller.isEnabled()) {
                    System.out.println("Profiling...");
                    long before = System.nanoTime();
                    Object retValue = method.invoke(bean, args);
                    long after = System.nanoTime();
                    System.out.println("Execution time: " + (after - before));
                    System.out.println("Done!");
                    return retValue;
                } else {
                    return method.invoke(bean, args);
                }
            });
        }
        return bean;
    }
}
