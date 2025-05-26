import org.junit.jupiter.api.*;
import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.SeatDao;
import org.scoula.db.dao.SeatDaoImpl;
import org.scoula.db.domain.SeatVO;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeatDaoImplTest {
    SeatDao dao = new SeatDaoImpl();
    private static int createdSeatId;

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @DisplayName("seat를 등록합니다.")
    @Order(1)
    void create() throws SQLException {
        SeatVO seat = new SeatVO();
        seat.setSeat_column(1);
        seat.setSeat_row("E");

        System.out.println("=== 좌석 삽입 ===");
        System.out.println("삽입할 좌석: " + seat.getSeat_row() + seat.getSeat_column());

        int count = dao.create(seat);
        Assertions.assertEquals(1, count);

        List<SeatVO> list = dao.getList();
        for(SeatVO s : list) {
            if("E".equals(s.getSeat_row()) && s.getSeat_column() == 1) {
                createdSeatId = s.getSeat_id();
                break;
            }
        }

        System.out.println("삽입 완료: " + count + "개 행이 추가되었습니다. (ID: " + createdSeatId + ")");
        System.out.println();
    }

    @Test
    @DisplayName("seat 목록을 추출합니다.")
    @Order(2)
    void getList() throws SQLException {
        System.out.println("=== 전체 좌석 목록 조회 ===");
        List<SeatVO> list = dao.getList();
        for(SeatVO seat : list) {
            System.out.println("조회된 좌석: " + seat);
        }
        System.out.println("총 " + list.size() + "개의 좌석이 조회되었습니다.");
        System.out.println();
    }

    @Test
    @DisplayName("특정 seat 1건을 추출합니다.")
    @Order(3)
    void get() throws SQLException {
        System.out.println("=== 특정 좌석 조회 ===");
        System.out.println("ID " + createdSeatId + "번 좌석을 조회합니다.");

        SeatVO seat = dao.get(createdSeatId).orElseThrow(NoSuchElementException::new);
        System.out.println("조회된 좌석: " + seat);
        Assertions.assertNotNull(seat);
        System.out.println();
    }

    @Test
    @DisplayName("seat의 정보를 수정합니다.")
    @Order(4)
    void update() throws SQLException {
        System.out.println("=== 좌석 정보 수정 ===");
        SeatVO seat = dao.get(createdSeatId).orElseThrow(NoSuchElementException::new);
        System.out.println("수정 전 행: " + seat.getSeat_row());

        seat.setSeat_row("F");
        System.out.println("수정 후 행: " + seat.getSeat_row());

        int count = dao.update(seat);
        Assertions.assertEquals(1, count);

        System.out.println("수정 완료: " + count + "개 행이 수정되었습니다.");
        System.out.println();
    }

    @Test
    @DisplayName("seat를 삭제합니다.")
    @Order(5)
    void delete() throws SQLException {
        System.out.println("=== 좌석 삭제 ===");
        System.out.println("ID " + createdSeatId + "번 좌석을 삭제합니다.");

        int count = dao.delete(createdSeatId);
        Assertions.assertEquals(1, count);

        System.out.println("삭제 완료: " + count + "개 행이 삭제되었습니다.");
        System.out.println();
    }
}
