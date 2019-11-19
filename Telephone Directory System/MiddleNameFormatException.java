package teldir;

public class MiddleNameFormatException extends Exception {
    @Override
    public String getMessage() {
        return "Middle name must contain only letters.";

    }
}
