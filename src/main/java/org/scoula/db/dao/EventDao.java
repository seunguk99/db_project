package org.scoula.db.dao;

import org.scoula.db.domain.EventVO;

import java.util.List;

public interface EventDao {
    void insert(EventVO event);
    List<EventVO> selectAll();
    EventVO selectById(int id);
    void update(EventVO event);
    void delete(int id);

    List<EventVO> getEvents();
    EventVO getEvent(int id);
}