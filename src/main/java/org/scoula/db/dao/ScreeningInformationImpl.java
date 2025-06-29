package org.scoula.db.dao;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.ScreeningInformationVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreeningInformationImpl implements ScreeningInformationDao {
    Connection conn = JDBCUtil.getConnection();

    @Override
    public void insert(ScreeningInformationVO screeningInformation) {
        String sql = "INSERT INTO screening_information(date_time, movie_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(screeningInformation.getDateTime()));
            pstmt.setInt(2, screeningInformation.getMovieId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Insert 실패", e);
        }
    }
    @Override
    public List<ScreeningInformationVO> getScreeningInformationBymovieID(int movieId){
        String sql = """
        SELECT screening_id, date_time, movie_id
        FROM screening_information
        WHERE movie_id = ?
        ORDER BY date_time asc
        """;

        List<ScreeningInformationVO> screeningList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {  // while로 변경하여 모든 결과 가져오기
                    ScreeningInformationVO screening = ScreeningInformationVO.builder()
                            .screeningId(rs.getInt("screening_id"))
                            .dateTime(rs.getTimestamp("date_time").toLocalDateTime())
                            .movieId(rs.getInt("movie_id"))
                            .build();
                    screeningList.add(screening);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }

        return screeningList;
    }

    @Override
    public ScreeningInformationVO get(int screeningId) {
        String sql = "SELECT * FROM screening_information WHERE screening_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, screeningId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return ScreeningInformationVO.builder()
                        .screeningId(rs.getInt("screening_id"))
                        .dateTime(rs.getTimestamp("date_time").toLocalDateTime())
                        .movieId(rs.getInt("movie_id"))
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
        return null;
    }
    @Override
    public List<ScreeningInformationVO> getList() {
        String sql = "SELECT * FROM screening_information";
        List<ScreeningInformationVO> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ScreeningInformationVO vo = ScreeningInformationVO.builder()
                        .screeningId(rs.getInt("screening_id"))
                        .dateTime(rs.getTimestamp("date_time").toLocalDateTime())
                        .movieId(rs.getInt("movie_id"))
                        .build();
                list.add(vo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("전체 조회 실패", e);
        }
        return list;
    }

    @Override
    public void update(ScreeningInformationVO screeningInformation) {
        String sql = "UPDATE screening_information SET date_time = ?, movie_id = ? WHERE screening_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(screeningInformation.getDateTime()));
            pstmt.setInt(2, screeningInformation.getMovieId());
            pstmt.setInt(3, screeningInformation.getScreeningId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("수정 실패", e);
        }
    }

    @Override
    public void delete(int screeningId) {
        String sql = "DELETE FROM screening_information WHERE screening_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, screeningId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("삭제 실패", e);
        }
    }

    @Override
    public int getRemainTicket(int screeningId) {
        int remainTicket = -1;
        String sql =
                """
                        SELECT
                            SUM(r.number_of_people) AS reserved_seats
                        FROM
                            reservation r
                        WHERE
                            r.screening_id = ? AND r.reservation_status IN ('확정', '대기');
                """;
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, screeningId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                remainTicket = rs.getInt("reserved_seats");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("여석 수 가져오기 실패", e);
        }
        return remainTicket;
    }
}
