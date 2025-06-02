package org.scoula.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.SeatReservationVO;

public class SeatReservationDaoImpl implements SeatReservationDao {

    private final Connection conn = JDBCUtil.getConnection();

    private SeatReservationVO map(ResultSet rs) throws SQLException {
        return SeatReservationVO.builder()
                .seatResId(rs.getInt("seat_res_id"))
                .seatId(rs.getInt("seat_id"))
                .reservationId(rs.getInt("reservation_id"))
                .build();
    }

    @Override
    public void insert(SeatReservationVO vo) {
        String sql = "INSERT INTO seat_reservation(seat_id, reservation_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, vo.getSeatId());
            pstmt.setLong(2, vo.getReservationId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SeatReservationVO> findById(int seatResId) {
        String sql = "SELECT * FROM seat_reservation WHERE seat_res_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, seatResId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<SeatReservationVO> findAll() {
        List<SeatReservationVO> list = new ArrayList<>();
        String sql = "SELECT * FROM seat_reservation";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void update(SeatReservationVO vo) {
        String sql = "UPDATE seat_reservation SET seat_id = ?, reservation_id = ? WHERE seat_res_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, vo.getSeatId());
            pstmt.setLong(2, vo.getReservationId());
            pstmt.setLong(3, vo.getSeatResId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int seatResId) {
        String sql = "DELETE FROM seat_reservation WHERE seat_res_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, seatResId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByResId(int resId) {
        String sql = "DELETE FROM seat_reservation WHERE reservation_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, resId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
