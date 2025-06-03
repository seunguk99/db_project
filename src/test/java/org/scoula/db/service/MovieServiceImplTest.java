package org.scoula.db.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoula.db.dao.MovieDao;
import org.scoula.db.dao.MovieDaoImpl;
import org.scoula.db.dao.ScreeningInformationDao;
import org.scoula.db.dao.ScreeningInformationImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceImplTest {

    private MovieService movieService;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        // DAO 구현체들 생성
        MovieDao movieDao = new MovieDaoImpl();
        ScreeningInformationDao screeningDao = new ScreeningInformationImpl();

        // Service 생성
        movieService = new MovieServiceImpl(movieDao, screeningDao);

        // System.out 캡처를 위한 설정
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void getAllMoviesByRatingDesc() {
        // when
        movieService.getAllMoviesByRatingDesc();

        // then
        String output = outputStream.toString();
        assertTrue(output.contains("=== 전체 영화 (별점순) ==="));

        System.setOut(originalOut);
        System.out.println("테스트 결과:\n" + output);
    }

    @Test
    void getMovieById() {
        // when - 존재하는 영화 ID로 테스트 (DB에 movie_id = 1이 있다고 가정)
        movieService.getMovieById(1);

        // then
        String output = outputStream.toString();
        assertTrue(output.contains("=== 영화 정보 ===") ||
                output.contains("해당 영화를 찾을 수 없습니다."));

        System.setOut(originalOut);
        System.out.println("테스트 결과:\n" + output);
    }

//    @Test
//    void getMovieShowtimeByIdAsc() {
//        movieService.getMovieShowtimeByIdAsc(1);
//
//        String output = outputStream.toString();
//        assertTrue(output.contains("=== 상영 시간 정보 ===") ||
//                output.contains("해당 영화의 상영 정보를 찾을 수 없습니다."));
//
//        System.setOut(originalOut);
//        System.out.println("테스트 결과:\n" + output);
//    }

}