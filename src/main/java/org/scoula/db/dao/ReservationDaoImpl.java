package org.scoula.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.dto.ReservationDetail;
import org.scoula.db.domain.ReservationVO;

public class ReservationDaoImpl implements ReservationDao {
    Connection conn = JDBCUtil.getConnection();
    private String RESERV_LIST = "select * from reservation";
    private String RESERV_GET = "select * from reservation where reservation_id = ?";
    private String RESERV_INSERT = "insert into reservation values(?, ?, ?, ?, ?)";
    private String RESERV_UPDATE = "update reservation set reservation_status = ? where reservation_id = ?";
    private String RESERV_DELETE = "delete from reservation where reservation_id = ?";
    private String RESERV_DETAIL = """
        select r.reservation_id, m.title, r.reservation_date, group_concat(concat(s.seat_row, s.seat_column) order by s.seat_row, s.seat_column) as seats
        from reservation r
                 inner join seat_reservation sr on r.reservation_id = sr.reservation_id
                 inner join seat s on sr.seat_id = s.seat_id
                 inner join screening_information si on r.screening_id = si.screening_id
                 inner join movie m on si.movie_id = m.movie_id
        where r.reservation_status != '취소'
        group by r.reservation_id, r.reservation_date, m.title;
        """;
    private String RESERV_CANCEL = "update reservation set reservation_status = '취소' WHERE reservation_id = ?";


    @Override
    public int create(ReservationVO reservation) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_INSERT)) {
            pstmt.setInt(1, reservation.getReservation_id());
            pstmt.setInt(2, reservation.getNumber_of_people());
            pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(4, reservation.getReservation_status());
            pstmt.setInt(5, reservation.getScreening_id());
            return pstmt.executeUpdate();
        }
    }

    private ReservationVO map(ResultSet rs) throws SQLException {
        ReservationVO reservation = new ReservationVO();
        reservation.setReservation_id(rs.getInt("reservation_id"));
        reservation.setNumber_of_people(rs.getInt("number_of_people"));
        reservation.setReservation_status(rs.getString("reservation_status"));
        reservation.setReservation_date(rs.getTimestamp("reservation_date").toLocalDateTime());
        reservation.setScreening_id(rs.getInt("screening_id"));
        return reservation;
    }

    @Override
    public List<ReservationVO> getList() throws SQLException {
        List<ReservationVO> reservationList = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_LIST)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ReservationVO reservation = map(rs);
                reservationList.add(reservation);
            }
        }
        return reservationList;
    }

    @Override
    public Optional<ReservationVO> get(int id) {
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_GET)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(ReservationVO reservation) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_UPDATE)) {
            pstmt.setString(1, reservation.getReservation_status());
            pstmt.setInt(2, reservation.getReservation_id());
            return pstmt.executeUpdate();
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_DELETE)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        }
    }

    @Override
    public List<ReservationDetail> getReservationDetail() {
        List<ReservationDetail> details = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_DETAIL);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ReservationDetail detail = ReservationDetail.builder()
                    .id(rs.getInt("reservation_id"))
                    .movieTitle(rs.getString("title"))
                    .reservationDate(rs.getTimestamp("reservation_date").toLocalDateTime())
                    .seats(rs.getString("seats"))
                    .build();
                details.add(detail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return details;
    }

    @Override
    public int cancelReservation(int reservationId) {
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_CANCEL)) {
            pstmt.setInt(1, reservationId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
