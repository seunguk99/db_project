package org.scoula.db.dao;

import org.scoula.db.domain.ReservationVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ReservationDao {
    int create(ReservationVO reservation) throws SQLException;
    List<ReservationVO> getList() throws SQLException;
    Optional<ReservationVO> get(int id) throws SQLException;
    int update(ReservationVO reservation) throws SQLException;
    int delete(int id) throws SQLException;
}
