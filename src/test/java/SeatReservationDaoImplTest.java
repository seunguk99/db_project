import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.SeatReservationDao;
import org.scoula.db.dao.SeatReservationDaoImpl;
import org.scoula.db.domain.SeatReservationVO;

import java.util.List;
import java.util.Optional;

public class SeatReservationDaoImplTest {
    public static void main(String[] args) {
        SeatReservationDao dao = new SeatReservationDaoImpl();

        // 1. INSERT
        SeatReservationVO vo = SeatReservationVO.builder()
                .seatId(1)
                .reservationId(1)
                .build();
        dao.insert(vo);
        System.out.println("좌석 예약 등록 완료");

        // 2. SELECT ALL
        List<SeatReservationVO> all = dao.findAll();
        System.out.println("\n현재 좌석 예약 전체 목록:");
        all.forEach(System.out::println);

        // 3. SELECT BY ID
        int lastId = all.get(all.size() - 1).getSeatResId(); // 방금 삽입한 마지막 ID 기준
        Optional<SeatReservationVO> opt = dao.findById(lastId);
        opt.ifPresent(res -> System.out.println("\n특정 좌석 예약 조회: " + res));

        // 4. UPDATE
        SeatReservationVO updated = SeatReservationVO.builder()
                .seatResId(lastId)
                .seatId(2)
                .reservationId(3)
                .build();
        dao.update(updated);
        System.out.println("\n좌석 예약 정보 수정 완료");
        System.out.println("수정된 예약 정보: " + dao.findById(lastId).orElse(null));

        // 5. DELETE
        dao.delete(lastId);
        System.out.println("\n좌석 예약 취소 완료");

        // 6. 최종 목록 확인
        System.out.println("\n최종 좌석 예약 목록:");
        dao.findAll().forEach(System.out::println);

        JDBCUtil.close();
    }
}