package org.scoula.db.dao;

import org.scoula.db.domain.MovieVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MovieDao {
    int create(MovieVO movie) throws SQLException;
    List<MovieVO> getList() throws SQLException;
    Optional<MovieVO> get(int id) throws SQLException;
    int update(MovieVO movie) throws SQLException;
    int delete(int id) throws SQLException;
    List<MovieVO> getListByRating() throws SQLException;
    MovieVO getMovieInformation(int movieId) throws SQLException;
}
