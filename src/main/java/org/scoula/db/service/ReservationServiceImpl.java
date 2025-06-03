package org.scoula.db.service;

import org.scoula.db.dao.ReservationDao;
import org.scoula.db.dao.ReservationDaoImpl;
import org.scoula.db.dao.SeatReservationDaoImpl;
import org.scoula.db.domain.ReservationVO;
import org.scoula.db.domain.SeatReservationVO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservationServiceImpl implements ReservationService {
    ReservationDao dao = new ReservationDaoImpl();
    SeatReservationDaoImpl seatDao = new SeatReservationDaoImpl();
    List<Integer> reservedSeats;
    boolean[][] isReserved;
    char[] rowLabels = {'A', 'B', 'C', 'D'};
    int createdId = -1;
    Scanner scanner;

    public ReservationServiceImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void printTheater(int screeningID) {
        isReserved = new boolean[4][4];
        try{
            reservedSeats=null;
            reservedSeats=dao.getReservedSeats(screeningID);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        for(int seatId : reservedSeats){
            isReserved[seatId/4][seatId%4] = true;
        }
        System.out.println("   1     2     3     4");
        for (int i = 0; i < isReserved.length; i++) {
            System.out.print(rowLabels[i] + "  ");
            for (int j = 0; j < isReserved[i].length; j++) {
                System.out.print((isReserved[i][j] ? "■" : "□") + "     ");
            }
            System.out.println();
        }
    }

    @Override
    public void makeReservation(int screeningID, int num) {
        ReservationVO reservationVO = ReservationVO.builder()
                .reservation_date(LocalDateTime.now())
                .reservation_status("확정")
                .number_of_people(num)
                .screening_id(screeningID)
                .build();
        try{
            createdId=dao.create(reservationVO);
            System.out.println("생성된 예약 ID: " + createdId);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void makeSeatReservation(int screeningID) {

        while(true){
            System.out.print("좌석을 입력하세요 (예: A2): ");
            String seatName = scanner.nextLine();

            if (seatName.matches("^(A|B|C|D)[1-4]$")) {
                int row = seatName.charAt(0) - 'A';
                int col = Character.getNumericValue(seatName.charAt(1)) - 1;
                int seatId = row * 4 + col + 1;
                if(!isReserved[row][col]){
                    SeatReservationVO seatReservation = SeatReservationVO.builder()
                            .seatId(seatId)
                            .reservationId(createdId)
                            .build();

                    seatDao.insert(seatReservation);
                    System.out.println("예약이 완료되었습니다: " + seatName);
                    break;
                }
                else{
                    System.out.println("예약된 좌석입니다. 다시 시도해주세요.");
                }
            } else {
                System.out.println("잘못된 좌석 입력입니다. 다시 시도해주세요.");
            }
        }
    }

//    public static void main(String[] args) {
//        ReservationServiceImpl reservationService = new ReservationServiceImpl();
//        reservationService.printTheater(1);
//        reservationService.makeReservation(1,2);
//        reservationService.makeSeatReservation(1);
//    }
}