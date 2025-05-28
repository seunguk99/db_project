package org.scoula.db.service;

import java.sql.SQLException;
import java.util.List;
import org.scoula.db.dao.dto.ReservationDetail;

public interface ReservationCancelService {
    List<ReservationDetail> getReservationDetail();
    boolean cancelReservation(int reservationId);

}
