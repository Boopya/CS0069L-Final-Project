package teldir;

public class TelephoneNumberFormatException extends Exception {
    @Override
    public String getMessage() {
        return "Please enter a valid telephone number.";
    }
}
