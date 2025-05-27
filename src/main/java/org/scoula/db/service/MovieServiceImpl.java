package org.scoula.db.service;

import lombok.RequiredArgsConstructor;
import org.scoula.db.dao.MovieDao;
import org.scoula.db.dao.ScreeningInformationDao;
import org.scoula.db.domain.MovieVO;
import org.scoula.db.domain.ScreeningInformationVO;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    final MovieDao movieDao;
    final ScreeningInformationDao screeningDao;

    @Override
    public void getAllMoviesByRatingDesc() {
        try{
            List<MovieVO> movies = movieDao.getListByRating();
            System.out.println("=== 전체 영화 (별점순) ===");
            for(int i = 0; i<movies.size(); i++){
                MovieVO movie = movies.get(i);
                System.out.printf("%d. %s ⭐ %.1f%n",
                        i + 1, movie.getTitle(), movie.getRating());
            }
        }catch(SQLException e){
            System.err.println("영화 목록 조회 실패 : " + e.getMessage());
        }
    }

    @Override
    public void getMovieById(int movieId) {
        try {
            MovieVO movie = movieDao.getMovieInformation(movieId);
            if (movie != null && movie.getTitle() != null) {
                System.out.println("\n================= 영화 상세 정보 =================");
                System.out.println("제목: " + movie.getTitle());
                System.out.println("평점: " + movie.getRating());
                System.out.println("상영시간: " + movie.getRunning_time() + "분");
                System.out.println("연령제한: " + movie.getAge_restriction());
                System.out.println("개봉일: " + movie.getRelease_date());
                System.out.println("감독: " + movie.getDirector());
                System.out.println("장르: " + movie.getGenre());
            } else {
                System.out.println("해당 영화를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("영화 정보 조회 실패: " + e.getMessage());
        }
    }

    @Override
    public void getMovieShowtimeByIdAsc(int movieId) {
        try {
            List<ScreeningInformationVO> screeningList = screeningDao.getScreeningInformationBymovieID(movieId);

            if (screeningList != null && !screeningList.isEmpty()) {
                System.out.println("\n============ 상영 시간표 =============");
                for (ScreeningInformationVO screening : screeningList) {
                    System.out.println("상영일시: " + screening.getDateTime());
                }
            } else {
                System.out.println("해당 영화의 상영 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("상영 정보 조회 실패: " + e.getMessage());
        }
    }
}
