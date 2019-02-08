package schedulePlanner;

import java.time.LocalDateTime;

public final class Session {

    private final Venue venue;
    private final LocalDateTime dateTime;
    private final int duration;

    public Session(Venue venue, LocalDateTime dateTime, int duration) {
        this.venue = venue;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    public Venue getVenue() {
        return venue;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }
}
