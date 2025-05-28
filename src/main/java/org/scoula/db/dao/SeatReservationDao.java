package org.scoula.db.dao;

import java.util.List;
import java.util.Optional;
import org.scoula.db.domain.SeatReservationVO;

public interface SeatReservationDao {
    void insert(SeatReservationVO vo);
    Optional<SeatReservationVO> findById(int seatResId);
    List<SeatReservationVO> findAll();
    void update(SeatReservationVO vo);
    void delete(int seatResId);
    void deleteByResId(int resId);
}