import org.junit.jupiter.api.*;
import org.scoula.db.common.JDBCUtil;
import org.scoula.db.dao.MovieDao;
import org.scoula.db.dao.MovieDaoImpl;
import org.scoula.db.domain.MovieVO;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieDaoImplTest {
    MovieDao dao = new MovieDaoImpl();
    private static int createdMovieId;

    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    @Test
    @DisplayName("Movie를 등록합니다.")
    @Order(1)
    void create() throws SQLException {
        MovieVO movie = new MovieVO();
        movie.setTitle("Interstellar");
        movie.setRating(8.6);
        movie.setRunning_time(169);
        movie.setAge_restriction(12);
        movie.setRelease_date(LocalDateTime.of(2014, 11, 7, 0, 0));
        movie.setDirector("Christopher Nolan");
        movie.setGenre("Sci-Fi");

        System.out.println("=== 영화 삽입 ===");
        System.out.println("삽입할 영화: " + movie.getTitle() + " (감독: " + movie.getDirector() + ")");

        int count = dao.create(movie);
        Assertions.assertEquals(1, count);


        List<MovieVO> list = dao.getList();
        for(MovieVO m : list) {
            if("Interstellar".equals(m.getTitle())) {
                createdMovieId = m.getMovie_id();
                break;
            }
        }

        System.out.println("삽입 완료: " + count + "개 행이 추가되었습니다. (ID: " + createdMovieId + ")");
        System.out.println();
    }

    @Test
    @DisplayName("Movie 목록을 추출합니다.")
    @Order(2)
    void getList() throws SQLException {
        System.out.println("=== 전체 영화 목록 조회 ===");
        List<MovieVO> list = dao.getList();
        for(MovieVO movie : list) {
            System.out.println("조회된 영화: " + movie);
        }
        System.out.println("총 " + list.size() + "개의 영화가 조회되었습니다.");
        System.out.println();
    }

    @Test
    @DisplayName("특정 Movie 1건을 추출합니다.")
    @Order(3)
    void get() throws SQLException {
        System.out.println("=== 특정 영화 조회 ===");
        System.out.println("ID " + createdMovieId + "번 영화를 조회합니다.");

        MovieVO movie = dao.get(createdMovieId).orElseThrow(NoSuchElementException::new);
        System.out.println("조회된 영화: " + movie);
        Assertions.assertNotNull(movie);
        System.out.println();
    }

    @Test
    @DisplayName("Movie의 정보를 수정합니다.")
    @Order(4)
    void update() throws SQLException {
        System.out.println("=== 영화 정보 수정 ===");
        MovieVO movie = dao.get(createdMovieId).orElseThrow(NoSuchElementException::new);
        System.out.println("수정 전 제목: " + movie.getTitle());

        movie.setTitle("RED NOTICE");
        System.out.println("수정 후 제목: " + movie.getTitle());

        int count = dao.update(movie);
        Assertions.assertEquals(1, count);

        System.out.println("수정 완료: " + count + "개 행이 수정되었습니다.");
        System.out.println();
    }

    @Test
    @DisplayName("Movie를 삭제합니다.")
    @Order(5)
    void delete() throws SQLException {
        System.out.println("=== 영화 삭제 ===");
        System.out.println("ID " + createdMovieId + "번 영화를 삭제합니다.");

        int count = dao.delete(createdMovieId);
        Assertions.assertEquals(1, count);

        System.out.println("삭제 완료: " + count + "개 행이 삭제되었습니다.");
        System.out.println();
    }
}
