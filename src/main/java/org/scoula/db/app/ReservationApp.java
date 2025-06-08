package org.scoula.db.app;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.*;
import org.scoula.db.domain.MovieVO;
import org.scoula.db.service.*;


import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ReservationApp {
    MovieService movieService;
    MovieDao movieDao;
    ScreeningInformationDao screeningDao;
    SelectNumberService selectNumberService;
    ReservationService reservationService;
    Scanner sc = new Scanner(System.in);
    Set<Integer> screeningIdSet;
    static int selectedMovieId = -1;
    static int selectedScreeningId = -1;

    public ReservationApp() {
        movieDao = new MovieDaoImpl();
        screeningDao = new ScreeningInformationImpl();
        movieService = new MovieServiceImpl(movieDao, screeningDao);
        selectNumberService = new SelectNumberServiceImpl();
        reservationService = new ReservationServiceImpl(sc);
    }

    public void showMovieSelection() {
        try {
            System.out.println("\n=== 영화 예매 시스템 ===");
            System.out.println("========================================");

            movieService.getAllMoviesByRatingDesc();

            List<MovieVO> movies = getMoviesFromService();

            System.out.println("----------------------------------------");

            // 사용자가 영화 선택
            System.out.print("관람하실 영화를 선택해주세요 (번호 입력): ");
            int choice = Integer.parseInt(sc.nextLine()) - 1;

            if (choice >= 0 && choice < movies.size()) {
                MovieVO selectedMovie = movies.get(choice);
                selectedMovieId = selectedMovie.getMovie_id();
                showDetailOptions();
            } else {
                System.out.println("잘못된 선택입니다.");
            }

        } catch (SQLException e) {
            System.err.println("영화 목록 조회 실패: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
        }
    }

    private List<MovieVO> getMoviesFromService() throws SQLException {
        return movieDao.getListByRating();
    }

    public void showMovieDetail(int movieId) {

        movieService.getMovieById(movieId);

        System.out.println("================================================");

        screeningIdSet=movieService.getMovieShowtimeByIdAsc(movieId);
        System.out.println("===================================");

    }

    public void showDetailOptions() {
        System.out.println("\n============ 메뉴 선택 =============");
        System.out.println("1. 다른 영화 선택");
        System.out.println("2. 회차 선택으로 넘어가기");
        System.out.println("3. 종료");
        System.out.println("===================================");
        System.out.print("선택하세요: ");

        try {
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    showMovieSelection();
                    break;
                case 2:
                    showMovieDetail(selectedMovieId);
                    selectTime();
                    makeReservation();
                    break;
                case 3:
                    exit();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    showDetailOptions();
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
            showDetailOptions();
        }
    }


    public void exit() {
        System.out.println("\n 영화 예매 시스템을 종료합니다.");
        sc.close();
        JDBCUtil.close();
        System.exit(0);
    }

    public void selectTime(){
        System.out.println("원하시는 상영 시간의 상영 ID를 입력해주세요(나가려면 0 입력)");
        System.out.print("상영 ID: ");
        while (true) {
            try {
                int selection = sc.nextInt();
                sc.nextLine();
                if (selection == 0 || screeningIdSet.contains(selection)) {
                    selectedScreeningId = selection;
                    return;
                } else {
                    System.out.println("존재하지 않는 상영 번호입니다. 다시 입력해주세요.");
                    System.out.print("상영 ID: ");
                }
            } catch (InputMismatchException e) {
                System.out.println("올바르지 않은 입력입니다. 숫자를 입력해주세요.");
            }
        }
    }

    public void makeReservation() {
        int num = selectNumberService.selectNOfPeople(selectedScreeningId);
        if(num==-1){
            System.out.println("매진된 영화입니다.");
            System.out.println("초기화면으로 돌아갑니다.");
            return;
        }
        reservationService.makeReservation(selectedScreeningId,num);
        System.out.println();
        for(int i =0; i<num; i++) {
            reservationService.printTheater(selectedScreeningId);
            reservationService.makeSeatReservation(selectedScreeningId);
        }

    }

    public void run() {
        showMovieSelection();
    }

    public static void main(String[] args) {
        ReservationApp app = new ReservationApp();
        app.run();
    }
}