package teldir;

public class NullLastNameException extends Exception {
    @Override
    public String getMessage() {
        return "Last name cannot be empty.";
    }
}
