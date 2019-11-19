package teldir;

public class FirstNameFormatException extends Exception {
    @Override
    public String getMessage() {
        return "First name must contain only letters.";
    }
}
