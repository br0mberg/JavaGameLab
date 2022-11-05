import java.util.concurrent.atomic.AtomicInteger;

abstract public class GameObject {
    String name;
    private static int nextId = 0;
    private final int identifCode;
    public GameObject(String name) {
        this.name = name;
        this.identifCode = ++nextId;
    }

    public int getID() {
        return identifCode;
    }
};
