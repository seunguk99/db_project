package org.scoula.db.app;

import org.scoula.db.dao.EventDao;
import org.scoula.db.dao.EventDaoImpl;
import org.scoula.db.service.EventService;
import org.scoula.db.service.EventServiceImpl;

import java.util.Scanner;

public class EventApp {
    EventService service;
    private final Scanner sc = new Scanner(System.in);

    public EventApp() {
        EventDao dao = new EventDaoImpl();
        service = new EventServiceImpl(dao);
    }

    private void showAllEvents() {
        System.out.println("\n====== 현재 진행 중인 이벤트 목록 ======");
        service.printEvents();
    }

    private void showEventDetail(int id) {
        System.out.println("\n====== 이벤트 상세정보 ======");
        service.printEvent(id);
        System.out.println("=============================");
    }

    public void run() {
        while (true) {
            showAllEvents();
            System.out.print("상세보기할 ID를 입력하세요 (q: 뒤로가기): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("q")) break;

            try {
                int id = Integer.parseInt(input);
                showEventDetail(id);

                System.out.print("q를 누르면 목록으로 돌아갑니다: ");
                while (!sc.nextLine().trim().equalsIgnoreCase("q")) {
                    System.out.print("q를 누르세요: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자 ID 또는 q만 입력 가능합니다.");
            }
        }
    }

    public static void main(String[] args) {
        EventApp app = new EventApp();
        app.run();
    }
}