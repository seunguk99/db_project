package org.scoula.db.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SeatVO {
    private int seat_id;
    private int seat_column;
    private String seat_row;
}