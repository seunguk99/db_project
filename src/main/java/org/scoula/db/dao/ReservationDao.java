package org.scoula.db.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.scoula.db.dao.dto.ReservationDetail;
import org.scoula.db.domain.ReservationVO;

public interface ReservationDao {
    int create(ReservationVO reservation) throws SQLException;
    List<ReservationVO> getList() throws SQLException;
    Optional<ReservationVO> get(int id);
    int update(ReservationVO reservation) throws SQLException;
    int delete(int id) throws SQLException;
    List<ReservationDetail> getReservationDetail();
    int cancelReservation(int reservationId);
}
