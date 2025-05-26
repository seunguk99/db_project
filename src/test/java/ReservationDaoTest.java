import org.junit.jupiter.api.*;
import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.ReservationDao;
import org.scoula.db.dao.ReservationDaoImpl;
import org.scoula.db.domain.ReservationVO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationDaoTest {
    ReservationDao dao = new ReservationDaoImpl();
    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }
    @Test
    @DisplayName("예약 등록")
    @Order(1)
    void create() throws SQLException {
        ReservationVO reservation = new ReservationVO(7, 1, LocalDateTime.now(), "대기", 1);
        int count = dao.create(reservation);
        Assertions.assertEquals(1, count);
    }

    @Test
    @DisplayName("예약 목록 추출")
    @Order(2)
    void getList() throws SQLException{
        List<ReservationVO> reservationList = dao.getList();
        for (ReservationVO reservation : reservationList) {
            System.out.println(reservation);
        }
    }

    @Test
    @DisplayName("특정 예약 추출")
    @Order(3)
    void get() throws SQLException{
        ReservationVO reservation = dao.get(7).orElseThrow(NoSuchElementException::new);
        Assertions.assertNotNull(reservation);
    }

    @Test
    @DisplayName("예약 정보 수정")
    @Order(4)
    void update() throws SQLException{
        ReservationVO reservation = dao.get(7).orElseThrow(NoSuchElementException::new);
        reservation.setReservation_status("확정");
        int count = dao.update(reservation);
        Assertions.assertEquals(1, count);
    }

    @Test
    @DisplayName("예약 삭제")
    @Order(5)
    void delete() throws SQLException{
        int count = dao.delete(7);
        Assertions.assertEquals(1, count);
    }
}