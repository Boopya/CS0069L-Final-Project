package teldir;

public class UniqueContactIdException extends Exception {
    @Override
    public String getMessage() {
        return "That Contact ID is already taken. Get another one.";
    }
}
