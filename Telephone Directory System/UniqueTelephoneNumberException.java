package teldir;

public class UniqueTelephoneNumberException extends Exception {
    @Override
    public String getMessage() {
        return "Telephone number already exists.";
    }
}
