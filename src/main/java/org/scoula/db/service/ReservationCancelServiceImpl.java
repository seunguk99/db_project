package org.scoula.db.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.scoula.db.dao.ReservationDao;
import org.scoula.db.dao.SeatReservationDao;
import org.scoula.db.dao.dto.ReservationDetail;
import org.scoula.db.domain.ReservationVO;

@RequiredArgsConstructor
public class ReservationCancelServiceImpl implements ReservationCancelService {
    private final ReservationDao reservationDao;
    private final SeatReservationDao seatReservationDao;

    public List<ReservationDetail> getReservationDetail(){
        return reservationDao.getReservationDetail();
    }

    public boolean cancelReservation(int reservationId){
        Optional<ReservationVO> selected = reservationDao.get(reservationId);
        if (selected.isEmpty()) {
            return false;
        }

        ReservationVO reservation = selected.get();
        // 이미 취소인 경우
        if ("취소".equals(reservation.getReservation_status())) {
            return false;
        }

        // 좌석 예약 삭제
        seatReservationDao.deleteByResId(reservationId);
        // 상태만 취소로 변경
        reservationDao.cancelReservation(reservationId);

        return true;
    }
}
