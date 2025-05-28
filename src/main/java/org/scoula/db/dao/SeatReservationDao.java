package org.scoula.db.dao;

import org.scoula.db.domain.SeatReservationVO;

import java.util.List;
import java.util.Optional;

public interface SeatReservationDao {
    void insert(SeatReservationVO vo);
    Optional<SeatReservationVO> findById(int seatResId);
    List<SeatReservationVO> findAll();
    void update(SeatReservationVO vo);
    void delete(int seatResId);
}