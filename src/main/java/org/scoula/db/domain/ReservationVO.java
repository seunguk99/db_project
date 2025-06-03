package org.scoula.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ReservationVO {
    private int reservation_id;
    private int number_of_people;
    private LocalDateTime reservation_date;
    private String reservation_status;
    private int screening_id;
}