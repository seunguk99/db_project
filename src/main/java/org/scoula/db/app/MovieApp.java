package org.scoula.db.app;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.*;
import org.scoula.db.domain.MovieVO;
import org.scoula.db.service.MovieService;
import org.scoula.db.service.MovieServiceImpl;
import org.scoula.db.service.ReservationService;
import org.scoula.db.service.ReservationServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MovieApp {
    MovieService service;
    MovieDao movieDao;
    ScreeningInformationDao screeningDao;
    Scanner sc = new Scanner(System.in);

    public MovieApp() {
        movieDao = new MovieDaoImpl();
        screeningDao = new ScreeningInformationImpl();
        service = new MovieServiceImpl(movieDao, screeningDao);
    }

    public void showMovieSelection() {
        try {
            System.out.println("\n=== 영화 예매 시스템 ===");
            System.out.println("========================================");

            service.getAllMoviesByRatingDesc();

            List<MovieVO> movies = getMoviesFromService();

            System.out.println("----------------------------------------");

            // 사용자가 영화 선택
            System.out.print("관람하실 영화를 선택해주세요 (번호 입력): ");
            int choice = Integer.parseInt(sc.nextLine()) - 1;

            if (choice >= 0 && choice < movies.size()) {
                MovieVO selectedMovie = movies.get(choice);
                showMovieDetail(selectedMovie.getMovie_id());
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

        service.getMovieById(movieId);

        System.out.println("================================================");

        service.getMovieShowtimeByIdAsc(movieId);
        System.out.println("======================================");

        showDetailOptions();
    }

    public void showDetailOptions() {
        System.out.println("\n============ 메뉴 선택 =============");
        System.out.println("1. 다른 영화 선택");
        System.out.println("2. 인원 선택 화면으로 넘어가기 + 현재 여석 수 보여주기");
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
                    //예매 내역 출력 후 다시 처음으로
                    ReservationDao reservationDao = new ReservationDaoImpl();
                    ScreeningInformationDao screeningDao = new ScreeningInformationImpl();
                    MovieDao movieDao = new MovieDaoImpl();
                    ReservationService reservationService = new ReservationServiceImpl(
                            reservationDao, screeningDao, movieDao
                    );

                    System.out.println("\n[📜 현재 예매 내역 출력 ]");
                    reservationService.printReservationList();

                    //다시 처음으로
                    showMovieSelection();
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

    public void run() {
        showMovieSelection();
    }

    public static void main(String[] args) {
        MovieApp app = new MovieApp();
        app.run();
    }
}