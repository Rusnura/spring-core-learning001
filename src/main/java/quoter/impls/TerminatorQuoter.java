package quoter.impls;

import quoter.annotations.InjectRandom;
import quoter.interfaces.IQuoter;

public class TerminatorQuoter implements IQuoter {
    private String message;
    @InjectRandom(min = 2, max = 10)
    private int repeatsCount;

    public TerminatorQuoter() {
        System.out.println("=== Phase 1 constructor ===");
        System.out.println("This constructor called by spring's reflection before BeanPostProcessing..." + System.lineSeparator() +
            "repeatsCount property value at current moment = 0");
    }

    // We can call this constructor via @PostConstruct, but we need declare in context.xml:
    // <context:annotation-config />
    // This line adding all annotations (ex: @PostConstruct, @Autowired and etc) to current application
    public void init() {
        System.out.println("=== Phase 2 constructor ===");
        System.out.println("I'am TerminatorQuoter, i will do repeat my message: " + repeatsCount + " times!");
    }

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
