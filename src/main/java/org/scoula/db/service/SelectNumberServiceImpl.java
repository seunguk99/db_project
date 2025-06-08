package org.scoula.db.service;

import org.scoula.db.dao.ScreeningInformationDao;
import org.scoula.db.dao.ScreeningInformationImpl;

import java.util.Scanner;

public class SelectNumberServiceImpl implements SelectNumberService {
    private final static int MAX_SEATS = 16;
    @Override
    public int selectNOfPeople(int reservationId) {
        ScreeningInformationDao dao = new ScreeningInformationImpl();
        int reservedSeats = dao.getRemainTicket(reservationId);
        System.out.println(reservedSeats);
        int availableSeats = MAX_SEATS - reservedSeats;
        if(availableSeats==0){
            return -1;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("==========================");
        System.out.println("      인원 선택 화면      ");
        System.out.println("==========================");
        System.out.println();
        System.out.println("현재 여석은 (" + availableSeats + ") 석입니다.");
        System.out.println();
        System.out.print("예약할 인원을 입력하세요: ");
        int seats = scanner.nextInt();
        scanner.nextLine();
        while (true) {
            if (seats > availableSeats || seats < 0) {
                System.out.println("잘못된 인원 수 입력입니다.");
                System.out.print("다시 입력해주세요: ");
                seats = scanner.nextInt();
            }
            else{
                return seats;
            }
        }
    }

//    public static void main(String[] args) {
//        SelectNumberService service = new SelectNumberServiceImpl();
//        service.selectNOfPeople(4);
//    }
}
