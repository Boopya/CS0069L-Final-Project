package  teldir;

public interface DisplayDataModes {
    public abstract void displayDefaultData();
    public abstract void displayLatestData();
    public abstract void displayLatestData(String selected_category);
    public abstract void displayLatestData(String selected_category, String input);
}
