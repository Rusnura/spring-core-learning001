package quoter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import quoter.interfaces.IQuoter;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        IQuoter quoter = (IQuoter)context.getBean("terminatorQuoter");
        quoter.say();
    }
}
