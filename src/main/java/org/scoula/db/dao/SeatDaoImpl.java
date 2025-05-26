package org.scoula.db.dao;

import org.scoula.db.common.JDBCUtil;
import org.scoula.db.domain.SeatVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatDaoImpl implements SeatDao {
    Connection conn = JDBCUtil.getConnection();

    private String SEAT_LIST = "SELECT * FROM seat";
    private String SEAT_GET = "SELECT * FROM seat WHERE seat_id = ?";
    private String SEAT_INSERT = "INSERT INTO seat(seat_column, seat_row) VALUES(?, ?)";
    private String SEAT_UPDATE = "UPDATE seat SET seat_row = ? WHERE seat_id = ?";
    private String SEAT_DELETE = "DELETE FROM seat WHERE seat_id = ?";


    @Override
    public int create(SeatVO seat) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(SEAT_INSERT)) {
            stmt.setInt(1, 1);
            stmt.setString(2, "E");
            return stmt.executeUpdate();
        }
    }
    private SeatVO map(ResultSet rs) throws SQLException {
        SeatVO seat = new SeatVO();
        seat.setSeat_id(rs.getInt("seat_id"));
        seat.setSeat_column(rs.getInt("seat_column"));

        String seatRowStr = rs.getString("seat_row");
        if (seatRowStr != null && seatRowStr.length() > 0) {
            seat.setSeat_row(String.valueOf(seatRowStr.charAt(0)));
        } else {
            seat.setSeat_row("\u0000");
        }
        return seat;
    }
    @Override
    public List<SeatVO> getList() throws SQLException{
        List<SeatVO> seatList = new ArrayList<SeatVO>();
        Connection conn = JDBCUtil.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(SEAT_LIST);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SeatVO seat = map(rs);
                seatList.add(seat);
            }
        }
        return seatList;
    }

    @Override
    public Optional<SeatVO> get(int id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(SEAT_GET)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int update(SeatVO seat) throws SQLException {
        Connection conn = JDBCUtil.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(SEAT_UPDATE)) {
            stmt.setString(1, seat.getSeat_row());
            stmt.setInt(2, seat.getSeat_id());
            return stmt.executeUpdate();
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(SEAT_DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}