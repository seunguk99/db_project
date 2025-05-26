import org.junit.jupiter.api.*;
import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.EventDao;
import org.scoula.db.dao.EventDaoImpl;
import org.scoula.db.domain.EventVO;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EventDaoImplTest {

    static EventDao dao;
    static int createdEventId;

    @BeforeAll
    static void setUp() {
        dao = new EventDaoImpl();
    }

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @Order(1)
    @DisplayName("이벤트 삽입 테스트")
    public void insert() {
        EventVO newEvent = EventVO.builder()
                .title("new event")
                .body("body for new event")
                .startDate(Date.valueOf("2025-05-22"))
                .endDate(Date.valueOf("2025-05-29"))
                .build();

        dao.insert(newEvent);

        List<EventVO> events = dao.selectAll();
        EventVO lastEvent = events.get(events.size() - 1);
        createdEventId = lastEvent.getId();
        assertEquals("new event", lastEvent.getTitle());
        assertEquals("body for new event", lastEvent.getBody());
        assertEquals(Date.valueOf("2025-05-22"), lastEvent.getStartDate());
        assertEquals(Date.valueOf("2025-05-29"), lastEvent.getEndDate());
    }

    @Test
    @Order(2)
    @DisplayName("이벤트 조회 테스트")
    public void selectById() {
        EventVO event = dao.selectById(1);

        assertEquals("여름 영화제", event.getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("이벤트 업데이트 테스트")
    public void update() {
        EventVO updateEvent = EventVO.builder()
                .title("update event")
                .body("body for update event")
                .id(createdEventId)
                .startDate(Date.valueOf("2025-05-22"))
                .endDate(Date.valueOf("2025-05-29"))
                .build();

        dao.update(updateEvent);

        EventVO event = dao.selectById(createdEventId);
        assertEquals("update event", event.getTitle());
        assertEquals("body for update event", event.getBody());
        assertEquals(Date.valueOf("2025-05-22"), event.getStartDate());
        assertEquals(Date.valueOf("2025-05-29"), event.getEndDate());
    }

    @Test
    @Order(4)
    @DisplayName("이벤트 삭제 테스트")
    public void delete() {
        dao.delete(createdEventId);

        List<EventVO> events = dao.selectAll();
        assertEquals(3, events.size());
    }
}