package org.scoula.db.app;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.*;
import org.scoula.db.service.MovieServiceImpl;
import org.scoula.db.service.ReservationService;
import org.scoula.db.service.ReservationServiceImpl;

public class Main {
    public static void main(String[] args) {
        // 1. 영화 선택 및 상세 보기 실행 (MovieApp 흐름)
        MovieApp movieApp = new MovieApp();
        movieApp.run();  // 사용자에게 영화 목록 보여주고 선택하게 함

        // 2. 영화 흐름 끝나고 예매 내역 전체 출력
        System.out.println("\n================ 예매 내역 출력 ================\n");

        // ReservationService 생성
        ReservationDao reservationDao = new ReservationDaoImpl();
        ScreeningInformationDao screeningDao = new ScreeningInformationImpl();
        MovieDao movieDao = new MovieDaoImpl();

        ReservationService reservationService = new ReservationServiceImpl(
                reservationDao, screeningDao, movieDao
        );

        // 예매 내역 출력
        reservationService.printReservationList();

        // DB 연결 종료
        JDBCUtil.close();
    }
}
