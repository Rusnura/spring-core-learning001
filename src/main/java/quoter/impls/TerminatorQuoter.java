package quoter.impls;

import quoter.interfaces.IQuoter;

public class TerminatorQuoter implements IQuoter {
    private String message;

    @Override
    public void say() {
        if (message != null && !message.isEmpty()) {
            System.out.println(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
