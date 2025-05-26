package org.scoula.db.dao;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.EventVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.scoula.db.query.EventSql.*;

public class EventDaoImpl implements EventDao {
    Connection connection = JDBCUtil.getConnection();

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
}
