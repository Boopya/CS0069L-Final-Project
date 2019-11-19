package teldir;

public class LastNameFormatException extends Exception {
    @Override
    public String getMessage() {
        return "Last name must contain only letters.";
    }
}
