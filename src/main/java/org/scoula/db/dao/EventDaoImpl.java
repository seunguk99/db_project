package org.scoula.db.dao;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.EventVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.scoula.db.query.EventSql.*;

public class EventDaoImpl implements EventDao {
    Connection connection = JDBCUtil.getConnection();

    private EventVO map(ResultSet rs) throws SQLException {
        return new EventVO().builder()
                .id(rs.getInt("event_id"))
                .title(rs.getString("title"))
                .body(rs.getString("body"))
                .startDate(rs.getDate("start_date"))
                .endDate(rs.getDate("end_date"))
                .build();
    }

    @Override
    public void insert(EventVO event) {
        try(PreparedStatement ps = connection.prepareStatement(EVENT_INSERT)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getBody());
            ps.setDate(3, event.getStartDate());
            ps.setDate(4, event.getEndDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EventVO> selectAll() {
        List<EventVO> events = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(EVENT_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EventVO event = EventVO.builder()
                        .id(rs.getInt("event_id"))
                        .title(rs.getString("title"))
                        .body(rs.getString("body"))
                        .startDate(rs.getDate("start_date"))
                        .endDate(rs.getDate("end_date"))
                        .build();
                events.add(event);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public EventVO selectById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(EVENT_SELECT_BY_ID)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return EventVO.builder()
                            .id(rs.getInt("event_id"))
                            .title(rs.getString("title"))
                            .body(rs.getString("body"))
                            .startDate(rs.getDate("start_date"))
                            .endDate(rs.getDate("end_date"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void update(EventVO event) {
        try (PreparedStatement ps = connection.prepareStatement(EVENT_UPDATE)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getBody());
            ps.setDate(3, event.getStartDate());
            ps.setDate(4, event.getEndDate());
            ps.setInt(5, event.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = connection.prepareStatement(EVENT_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EventVO> getEvents() {
        List<EventVO> events = new ArrayList<>();
        String sql = "select * from event WHERE end_date >= CURRENT_DATE ORDER BY end_date ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                EventVO event = map(rs);
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public EventVO getEvent(int id) {
        String sql = "SELECT event_id, title, body, start_date, end_date FROM event WHERE event_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}