package org.scoula.db.app;

import org.scoula.db.dao.*;
import org.scoula.db.service.ReservationListService;
import org.scoula.db.service.ReservationListServiceImpl;

public class ReservationListApp {
    final ReservationDao reservationDao = new ReservationDaoImpl();
    final ScreeningInformationDao screeningDao = new ScreeningInformationImpl();
    final MovieDao movieDao = new MovieDaoImpl();
    final ReservationListService reservationListService = new ReservationListServiceImpl(
            reservationDao, screeningDao, movieDao
    );
    public void showList(){
        System.out.println("=========현재 예매 내역 출력=========");
        reservationListService.printReservationList();
    }

    public static void main(String[] args) {
        ReservationListApp app = new ReservationListApp();
        app.showList();
    }
}
