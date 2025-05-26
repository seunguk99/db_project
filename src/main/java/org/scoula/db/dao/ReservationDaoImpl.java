package org.scoula.db.dao;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.ReservationVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl implements ReservationDao {
    Connection conn = JDBCUtil.getConnection();
    private String RESERV_LIST = "select * from reservation";
    private String RESERV_GET = "select * from reservation where reservation_id = ?";
    private String RESERV_INSERT = "insert into reservation values(?, ?, ?, ?, ?)";
    private String RESERV_UPDATE = "update reservation set reservation_status = ? where reservation_id = ?";
    private String RESERV_DELETE = "delete from reservation where reservation_id = ?";

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
    public Optional<ReservationVO> get(int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(RESERV_GET)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
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
}
