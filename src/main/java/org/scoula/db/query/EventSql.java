package org.scoula.db.query;

public class EventSql {
    public static final String EVENT_INSERT = "INSERT INTO event(title, body, start_date, end_date) VALUES (?, ?, ?, ?)";
    public static final String EVENT_SELECT_ALL = "SELECT * FROM event";
    public static final String EVENT_SELECT_BY_ID = "SELECT * FROM event WHERE event_id = ?";
    public static final String EVENT_UPDATE = "UPDATE event SET title = ?, body = ?, start_date = ?, end_date = ? WHERE event_id = ?";
    public static final String EVENT_DELETE = "DELETE FROM event WHERE event_id = ?";

}
