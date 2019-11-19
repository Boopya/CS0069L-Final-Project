package teldir;

public class NullFirstNameException extends Exception {
    @Override
    public String getMessage() {
        return "First name cannot be empty.";
    }
}
