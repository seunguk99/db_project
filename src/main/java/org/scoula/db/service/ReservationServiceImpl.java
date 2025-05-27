package org.scoula.db.service;

import lombok.RequiredArgsConstructor;
import org.scoula.db.dao.*;
import org.scoula.db.domain.*;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final ScreeningInformationDao screeningDao;
    private final MovieDao movieDao;

    @Override
    public void printReservationList() {
        List<ReservationVO> reservations;
        try {
            reservations = reservationDao.getList();
        } catch (SQLException e) {
            throw new RuntimeException("예매 내역을 불러오는 데 실패했습니다.", e);
        }

        System.out.println("===== 예매 내역 전체 리스트 =====");
        for (ReservationVO r : reservations) {
            ScreeningInformationVO screening = screeningDao.get(r.getScreening_id());

            MovieVO movie = null;
            if (screening != null) {
                try {
                    movie = movieDao.get(screening.getMovieId()).orElse(null);
                } catch (SQLException e) {
                    throw new RuntimeException("영화 정보를 가져오는 중 오류 발생", e);
                }
            }

            System.out.printf("예매번호: %d | 인원: %d명 | 상태: %s | 예매일: %s\n",
                    r.getReservation_id(),
                    r.getNumber_of_people(),
                    r.getReservation_status(),
                    r.getReservation_date());

            if (movie != null && screening != null) {
                System.out.printf(" └ 영화: %s (%s) | 상영시간: %s\n\n",
                        movie.getTitle(), movie.getGenre(), screening.getDateTime());
            }
        }
    }

}
