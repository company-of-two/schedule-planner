package schedulePlanner;

public class Venue {

    private String name;
    private int capacity;

    public Venue(String name) {
        this.name = name;
        this.capacity = 0;
    }

    public Venue(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }
}
