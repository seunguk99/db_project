package org.scoula.db.dao;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.MovieVO;
import org.scoula.db.domain.ScreeningInformationVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDaoImpl implements MovieDao {
    Connection conn = JDBCUtil.getConnection();
    private String MOVIE_LIST   = "select * from movie";
    private String MOVIE_GET
            = "select * from movie where movie_id = ?";
    private String MOVIE_INSERT = "insert into movie(title, rating, running_time, age_restriction, release_date, director, genre) values(?, ?, ?, ?, ?, ?, ?)";
    private String MOVIE_UPDATE = "update movie set title = ?, rating = ?, running_time = ?, age_restriction = ?, release_date = ?, director = ?, genre = ? where movie_id = ?";
    private String MOVIE_DELETE = "delete from movie where movie_id = ?";
    private String MOVIE_SELECT_BY_RATING =
            """
            SELECT title,rating FROM movie
            ORDER BY rating DESC
            """;
    @Override
    public int create(MovieVO movie) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(MOVIE_INSERT)) {
            stmt.setString(1, movie.getTitle());
            stmt.setDouble(2, movie.getRating());
            stmt.setInt(3, movie.getRunning_time());
            stmt.setInt(4, movie.getAge_restriction());
            stmt.setTimestamp(5, Timestamp.valueOf(movie.getRelease_date()));
            stmt.setString(6, movie.getDirector());
            stmt.setString(7, movie.getGenre());
            return stmt.executeUpdate();
        }
    }
    private MovieVO map(ResultSet rs) throws SQLException {
        MovieVO movie = new MovieVO();
        movie.setMovie_id(rs.getInt("movie_id"));
        movie.setTitle(rs.getString("title"));
        movie.setRating(rs.getDouble("rating"));
        movie.setRunning_time(rs.getInt("running_time"));
        movie.setAge_restriction(rs.getInt("age_restriction"));
        movie.setRelease_date(rs.getTimestamp("release_date").toLocalDateTime());
        movie.setDirector(rs.getString("director"));
        movie.setGenre(rs.getString("genre"));
        return movie;
    }
    @Override
    public MovieVO getMovieInformation(int movieId) throws SQLException{
        String sql = """
                SELECT title,rating,running_time,age_restriction,release_date,director,genre 
                FROM movie
                WHERE movie_id = ?
                """;
        MovieVO movie = new MovieVO();
        Connection conn = JDBCUtil.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,movieId);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    movie.setTitle(rs.getString("title"));
                    movie.setRating(rs.getDouble("rating"));
                    movie.setRunning_time(rs.getInt("running_time"));
                    movie.setAge_restriction(rs.getInt("age_restriction"));
                    movie.setRelease_date(rs.getTimestamp("release_date").toLocalDateTime());
                    movie.setDirector(rs.getString("director"));
                    movie.setGenre(rs.getString("genre"));
                }
            }
        }
        return movie;
    }
    @Override
    public List<MovieVO> getListByRating() throws SQLException{
        List<MovieVO> movieList = new ArrayList<>();
        Connection conn = JDBCUtil.getConnection();

        String MOVIE_SELECT_BY_RATING = """
            SELECT movie_id, title, rating FROM movie
            ORDER BY rating DESC
            """;

        try (PreparedStatement stmt = conn.prepareStatement(MOVIE_SELECT_BY_RATING);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MovieVO movie = new MovieVO();
                movie.setMovie_id(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setRating(rs.getDouble("rating"));
                movieList.add(movie);
            }
        }
        return movieList;
    }
    @Override
    public List<MovieVO> getList() throws SQLException {
        List<MovieVO> movieList = new ArrayList<MovieVO>();
        Connection conn = JDBCUtil.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(MOVIE_LIST);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MovieVO movie = new MovieVO();
                movie.setMovie_id(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setRating(rs.getDouble("rating"));
                movie.setRunning_time(rs.getInt("running_time"));
                movie.setAge_restriction(rs.getInt("age_restriction"));
                movie.setRelease_date(rs.getTimestamp("release_date").toLocalDateTime());
                movie.setDirector(rs.getString("director"));
                movie.setGenre(rs.getString("genre"));
                movieList.add(movie);
            }
        }

        return movieList;
    }
    @Override
    public Optional<MovieVO> get(int movieId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(MOVIE_GET)) {
            stmt.setInt(1, movieId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }

        return Optional.empty();
    }
    @Override
    public int update(MovieVO movie) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(MOVIE_UPDATE)) {
            stmt.setString(1, movie.getTitle());
            stmt.setDouble(2, movie.getRating());
            stmt.setInt(3, movie.getRunning_time());
            stmt.setInt(4, movie.getAge_restriction());
            stmt.setTimestamp(5, Timestamp.valueOf(movie.getRelease_date()));
            stmt.setString(6, movie.getDirector());
            stmt.setString(7, movie.getGenre());
            stmt.setInt(8, movie.getMovie_id());
            return stmt.executeUpdate();
        }
    }
    @Override
    public int delete(int movieId) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(MOVIE_DELETE)) {
            stmt.setInt(1, movieId);
            return stmt.executeUpdate();
        }
    }
}