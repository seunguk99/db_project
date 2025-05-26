package org.scoula.db.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovieVO {
    private int movie_id;
    private String title;
    private double rating;
    private int running_time;
    private int age_restriction;
    private LocalDateTime release_date;
    private String director;
    private String genre;

}
