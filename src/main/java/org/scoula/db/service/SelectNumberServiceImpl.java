package org.scoula.db.service;

import org.scoula.db.dao.ScreeningInformationDao;
import org.scoula.db.dao.ScreeningInformationDaoImpl;

import java.util.Scanner;

public class SelectNumberServiceImpl implements SelectNumberService {
    private final static int MAX_SEATS = 16;
    @Override
    public int selectNOfPeople(int reservationId) {
        ScreeningInformationDao dao = new ScreeningInformationDaoImpl();
        int numOfPeople = 0;
        int reservedSeats = dao.getRemainTicket(reservationId);
        int availableSeats = MAX_SEATS - reservedSeats;
        Scanner scanner = new Scanner(System.in);

       while (true) {
           System.out.println("==========================");
           System.out.println();
           System.out.println("      인원 선택 화면      ");
           System.out.println("==========================");
           System.out.println();
           System.out.println("현재 여석은 (" + availableSeats + ") 석입니다.");
           System.out.println();
           System.out.print("예약할 인원을 입력하세요: ");
           int seats = scanner.nextInt();
           if (seats > availableSeats || seats < 0) {
               System.out.println("잘못된 인원 수 입력입니다.");
               System.out.println("다시 입력해주세요.");
               System.out.println();
               try{
                Thread.sleep(1500);
               }
               catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           else{
               scanner.close();
               return numOfPeople;
           }
       }
    }
    public static void main(String[] args) {
        SelectNumberService service = new SelectNumberServiceImpl();
        service.selectNOfPeople(4);
    }
}
