import org.scoula.db.dao.ScreeningInformationDao;
import org.scoula.db.dao.ScreeningInformationDaoImpl;
import org.scoula.db.domain.ScreeningInformationVO;

import java.time.LocalDateTime;
import java.util.List;

public class ScreeningInformationDaoTest {
    public static void main(String[] args) {
        ScreeningInformationDao dao = new ScreeningInformationDaoImpl();

        ScreeningInformationVO newInfo = ScreeningInformationVO.builder()
                .dateTime(LocalDateTime.of(2025, 5, 23, 12, 0))
                .movieId(1)
                .build();
        dao.insert(newInfo);
        System.out.println("등록 완료");

        List<ScreeningInformationVO> list = dao.getList();
        System.out.println("현재 등록된 상영 정보 목록:");
        for (ScreeningInformationVO info : list) {
            System.out.println(info);
        }

        ScreeningInformationVO lastInserted = list.get(list.size() - 1);
        int lastId = lastInserted.getScreeningId();

        ScreeningInformationVO found = dao.get(lastId);
        System.out.println("조회된 상영 정보: " + found);

        found.setDateTime(LocalDateTime.of(2025, 5, 23, 14, 0));
        found.setMovieId(2);
        dao.update(found);
        System.out.println("수정 완료: " + dao.get(lastId));

        dao.delete(lastId);
        System.out.println("삭제 완료");

        List<ScreeningInformationVO> finalList = dao.getList();
        System.out.println("최종 상영 정보 목록:");
        for (ScreeningInformationVO info : finalList) {
            System.out.println(info);
        }
        int remainTicket = dao.getRemainTicket(1);
        System.out.println("상영 정보 1번의 여석 수: "+remainTicket);
    }
}