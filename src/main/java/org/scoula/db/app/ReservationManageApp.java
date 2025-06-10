package org.scoula.db.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.scoula.db.dao.*;
import org.scoula.db.dao.dto.ReservationDetail;
import org.scoula.db.service.ReservationCancelService;
import org.scoula.db.service.ReservationCancelServiceImpl;
import org.scoula.db.service.ReservationListService;
import org.scoula.db.service.ReservationListServiceImpl;

public class ReservationManageApp {
    ReservationListService reservationListService;
    ReservationCancelService reservationCancelService;
    Scanner scanner = new Scanner(System.in);

    public ReservationManageApp() {
        ReservationDao reservationDao = new ReservationDaoImpl();
        SeatReservationDao seatReservationDao = new SeatReservationDaoImpl();
        MovieDao movieDao = new MovieDaoImpl();
        ScreeningInformationDao screeningInformationDao = new ScreeningInformationImpl();
        reservationCancelService = new ReservationCancelServiceImpl(reservationDao, seatReservationDao);
        reservationListService=new ReservationListServiceImpl(reservationDao, screeningInformationDao, movieDao);

    }

    public void run() throws SQLException {
        boolean flag = showList();
        while (flag) {
            System.out.println("===예매 취소하기===");
            System.out.println("====================================================================================");
            printReservationDetail();
            System.out.println("====================================================================================");

            System.out.print("\n취소할 예매 id를 입력해주세요. (메인 화면으로 돌아가려면 0 입력)\n→ ");
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) {
                    System.out.println("메인 화면으로 돌아갑니다...\n");
                    break; // TODO: 메인 화면으로 이동하도록 수정 필요
                }

                handleCancelReservation(choice);
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하세요.\n");
            } catch (SQLException e) {
                System.out.println("sql 오류: " + e.getMessage());
            }
            System.out.println("----------------------------------------");
            System.out.println();
        }

    }

    public void printReservationDetail() throws SQLException {
        List<ReservationDetail> details = reservationCancelService.getReservationDetail();
        for (ReservationDetail detail : details) {
            String formattedDate = detail.getReservationDate().toString().replace("T", " ");
            System.out.printf("[예매 id] %d | [영화] %s | [예매일시] %s | [좌석] %s%n",
                detail.getId(),
                detail.getMovieTitle(),
                formattedDate,
                detail.getSeats()
            );
        }
    }

    private void handleCancelReservation(int reservationId) throws SQLException {
        boolean cancelled = reservationCancelService.cancelReservation(reservationId);
        if (cancelled) {
            System.out.println("예매가 취소되었습니다.\n");
        } else {
            System.out.println("화면에 표시된 예매 id를 입력해주세요.\n");
        }
    }

    public boolean showList() {
        System.out.println("=========현재 예매 내역 출력=========");
        reservationListService.printReservationList();

        while (true) {
            System.out.print("예매 취소 메뉴로 진입하시겠습니까? (y/n) (메인 화면으로 돌아가려면 n 입력)\n→ ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("y")) {
                return true;  // 예매 취소 메뉴로 진입
            } else if (input.equalsIgnoreCase("n")) {
                return false; // 메인 화면으로 돌아감
            } else {
                System.out.println("잘못된 입력입니다. y 또는 n을 입력해주세요.");
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        ReservationManageApp app = new ReservationManageApp();
        app.run();

    }
}
