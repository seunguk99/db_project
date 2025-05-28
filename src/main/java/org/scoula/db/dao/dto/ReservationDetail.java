package org.scoula.db.dao.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationDetail {
    private int id;
    private LocalDateTime reservationDate;
    private String movieTitle;
    private String seats;
}
