package quoter.impls;

import quoter.annotations.InjectRandom;
import quoter.interfaces.IQuoter;

public class TerminatorQuoter implements IQuoter {
    private String message;
    @InjectRandom(min = 2, max = 10)
    private int repeatsCount;

    @Override
    public void say() {
        if (message != null && !message.isEmpty()) {
            for (int i = 0; i < repeatsCount; i++) {
                System.out.println(message);
            }
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
