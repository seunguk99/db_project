package org.scoula.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatReservationVO {
    private Long seatResId;
    private Long seatId;
    private Long reservationId;
}