package org.scoula.db.service;

import java.util.Set;


public interface MovieService {
    // 1. 전체 영화 별점순으로 내림차순 출력하기. input X
    void getAllMoviesByRatingDesc();
    // 2. 선택한 영화의 정보 출력하기. input - movie_id
    void getMovieById(int movieId);
    // 3. 선택한 영화의 날짜/시간 정보 오름차순 출력하기. input - movie_id
    Set<Integer> getMovieShowtimeByIdAsc(int movieId);
}
