package org.scoula.db.dao;

import org.scoula.db.domain.SeatVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SeatDao {
    int create(SeatVO seat) throws SQLException;
    List<SeatVO> getList() throws SQLException;
    Optional<SeatVO> get(int id) throws SQLException;
    int update(SeatVO seat) throws SQLException;
    int delete(int id) throws SQLException;

}
