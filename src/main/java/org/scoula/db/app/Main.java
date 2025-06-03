package org.scoula.db.app;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ReservationApp reservationApp = new ReservationApp();
        EventApp eventApp = new EventApp();
        ReservationManageApp reservationManageApp = new ReservationManageApp();

        List<MenuItem> menu = List.of(
                new MenuItem("영화 예매", reservationApp::run),
                new MenuItem("진행 중인 이벤트", eventApp::run),
                new MenuItem("예매 관리하기", () -> {
                    try {
                        reservationManageApp.run();
                    } catch (Exception e) {
                        System.out.println("예매 관리 실행 중 오류가 발생했습니다: " + e.getMessage());
                    }
                }),
                new MenuItem("종료", () -> System.exit(0))
        );
        while (true) {
            System.out.println("\n===== 메뉴 =====");
            for (int i = 0; i < menu.size(); i++) {
                System.out.println((i + 1) + ". " + menu.get(i).getTitle());
            }
            System.out.print("선택: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice >= 1 && choice <= menu.size()) {
                menu.get(choice - 1).getCommand().run(); // 선택된 항목 실행
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
