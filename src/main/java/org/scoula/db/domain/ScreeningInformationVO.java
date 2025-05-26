package org.scoula.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningInformationVO {
    private int screeningId;
    private LocalDateTime dateTime;
    private int movieId;
}
