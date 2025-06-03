//ScreeningInformationDao.java
package org.scoula.db.dao;


import org.scoula.db.domain.ScreeningInformationVO;

import java.util.List;

public interface ScreeningInformationDao {
    void insert(ScreeningInformationVO screeningInformation);
    ScreeningInformationVO get(int screeningId);
    List<ScreeningInformationVO> getList();
    void update(ScreeningInformationVO screeningInformation);
    void delete(int screeningId);
    List<ScreeningInformationVO> getScreeningInformationBymovieID(int movieId);
    int getRemainTicket(int screeningId);
}