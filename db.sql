DROP DATABASE IF EXISTS movie;

CREATE DATABASE movie;

USE movie;

CREATE TABLE movie (
                       movie_id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       rating DECIMAL(3,1) NOT NULL DEFAULT 0.0,
                       running_time INT NOT NULL,
                       age_restriction INT NOT NULL,
                       release_date DATETIME NOT NULL,
                       director VARCHAR(50) NOT NULL,
                       genre VARCHAR(20) NOT NULL
);

CREATE TABLE screening_information (
                                       screening_id INT AUTO_INCREMENT PRIMARY KEY,
                                       date_time DATETIME NOT NULL,
                                       movie_id INT NOT NULL,
                                       FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
);

CREATE TABLE reservation (
                             reservation_id INT AUTO_INCREMENT PRIMARY KEY,
                             number_of_people INT NOT NULL,
                             reservation_date DATETIME NOT NULL,
                             reservation_status VARCHAR(20) NOT NULL,
                             screening_id INT NOT NULL,
                             FOREIGN KEY (screening_id) REFERENCES screening_information(screening_id)
);

CREATE TABLE seat (
                      seat_id INT AUTO_INCREMENT PRIMARY KEY,
                      seat_column INT NOT NULL,
                      seat_row CHAR(1) NOT NULL
);

CREATE TABLE seat_reservation (
                                  seat_res_id INT AUTO_INCREMENT PRIMARY KEY,
                                  seat_id INT NOT NULL,
                                  reservation_id INT NOT NULL,
                                  FOREIGN KEY (seat_id) REFERENCES seat(seat_id),
                                  FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id)
);

CREATE TABLE event (
                       event_id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(100) NOT NULL,
                       body TEXT NOT NULL,
                       start_date DATE NOT NULL,
                       end_date DATE NOT NULL
);

USE movie;

INSERT INTO movie (title, rating, running_time, age_restriction, release_date, director, genre)
VALUES
    ('인터스텔라', 9.2, 169, 12, '2014-11-06 00:00:00', '크리스토퍼 놀란', 'SF'),
    ('어벤져스: 엔드게임', 8.5, 181, 12, '2019-04-24 00:00:00', '앤서니 루소', '액션'),
    ('기생충', 8.9, 132, 15, '2019-05-30 00:00:00', '봉준호', '드라마'),
    ('겨울왕국 2', 7.5, 103, 0, '2019-11-21 00:00:00', '크리스 벅', '애니메이션'),
    ('미션 임파서블: 데드 레코닝', 8.0, 163, 15, '2023-07-12 00:00:00', '크리스토퍼 맥쿼리', '액션');

INSERT INTO screening_information (date_time, movie_id)
VALUES
    ('2025-05-21 10:30:00', 1),
    ('2025-05-21 13:00:00', 2),
    ('2025-05-21 15:30:00', 3),
    ('2025-05-21 18:00:00', 4),
    ('2025-05-21 20:30:00', 5),
    ('2025-05-22 11:00:00', 1),
    ('2025-05-22 14:00:00', 2);

INSERT INTO seat (seat_column, seat_row)
VALUES
    (1, 'A'),
    (2, 'A'),
    (3, 'A'),
    (4, 'A'),
    (1, 'B'),
    (2, 'B'),
    (3, 'B'),
    (4, 'B'),
    (1, 'C'),
    (2, 'C'),
    (3, 'C'),
    (4, 'C'),
    (1, 'D'),
    (2, 'D'),
    (3, 'D'),
    (4, 'D');

INSERT INTO reservation (number_of_people, reservation_date, reservation_status, screening_id)
VALUES
    (2, '2025-05-19 09:15:00', '확정', 1),
    (4, '2025-05-19 14:30:00', '확정', 2),
    (1, '2025-05-20 10:45:00', '대기', 3),
    (3, '2025-05-20 16:20:00', '확정', 4),
    (2, '2025-05-20 18:45:00', '취소', 5);

INSERT INTO seat_reservation (seat_id, reservation_id)
VALUES
    (1, 1),
    (2, 1),
    (6, 2),
    (7, 2),
    (8, 2),
    (9, 2),
    (11, 3),
    (3, 4),
    (4, 4),
    (5, 4);

INSERT INTO event (title, body, start_date, end_date)
VALUES
    ('여름 영화제', '올여름 최고의 영화들을 할인된 가격으로 즐겨보세요!', '2025-07-01', '2025-07-31'),
    ('키즈 애니메이션 페스티벌', '어린이를 위한 특별 상영회와 이벤트', '2025-06-01', '2025-06-15'),
    ('밤새도록 영화 마라톤', '한 감독의 작품을 밤새도록 연속 상영합니다', '2025-05-25', '2025-05-26');
