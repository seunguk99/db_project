package org.scoula.db.service;

public interface ReservationService {
    void printTheater(int screeningID);
    void makeSeatReservation(int screeningID);
    void makeReservation(int screeningID, int num);
}