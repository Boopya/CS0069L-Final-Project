package teldir;

public class ContactIdFormatException extends Exception{
    @Override
    public String getMessage() {
        return "Contact ID must contain digits only.";
    }
}
