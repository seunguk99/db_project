package org.scoula.db.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventVO {
    private int id;
    private String title;
    private String body;
    private Date startDate;
    private Date endDate;
}
