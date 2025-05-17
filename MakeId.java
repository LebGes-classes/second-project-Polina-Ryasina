import java.util.UUID;

public class MakeId {
    public static String makeId() {
        return UUID.randomUUID().toString();
    }
}
