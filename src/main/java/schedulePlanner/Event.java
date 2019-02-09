package schedulePlanner;

import java.util.ArrayList;

public class Event {

    public String name;
    public ArrayList<Session> sessions;

    public Event(String name) {
        this.name = name;
        this.sessions = new ArrayList<Session>();
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }
}
