package consoleApp.menu;

public abstract class MenuItem {
    private final String name;
    private final boolean closeAfter;

    public MenuItem(String name, boolean closeAfter) {
        this.name = name;
        this.closeAfter = closeAfter;
    }

    public MenuItem(String name) {
        this(name, false);
    }

    public String getName() {
        return name;
    }

    public abstract void exec();

    public boolean closeAfter() {
        return closeAfter;
    }
}