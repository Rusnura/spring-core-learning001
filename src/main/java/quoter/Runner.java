package quoter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import quoter.interfaces.IQuoter;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/context.xml");
        while (true) {
            ((IQuoter)context.getBean("terminatorQuoter")).say();
            Thread.sleep(500);
        }
    }
}
