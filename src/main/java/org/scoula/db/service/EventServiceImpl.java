package org.scoula.db.service;

import lombok.RequiredArgsConstructor;
import org.scoula.db.dao.EventDao;
import org.scoula.db.domain.EventVO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    final EventDao dao;

    private void printEvents(List<EventVO> events) {
        System.out.println("ID  | 시작일       | 마감일       | 제목");
        System.out.println("---------------------------------------------");
        for (EventVO event : events) {
            System.out.printf("%3d | %10s | %10s | %s\n",
                    event.getId(),
                    event.getStartDate(),
                    event.getEndDate(),
                    event.getTitle());
        }
        System.out.println("---------------------------------------------");
    }

    @Override
    public void printEvents() {
        List<EventVO> events = dao.getEvents();
        printEvents(events);
    }

    @Override
    public void printEvent(int id) {
        EventVO event = dao.getEvent(id);
        if (event == null) {
            throw new NoSuchElementException("해당 ID의 이벤트가 존재하지 않습니다.");
        }

        System.out.println("제목: " + event.getTitle());
        System.out.println("본문: " + event.getBody());
        System.out.println("시작일: " + event.getStartDate());
        System.out.println("마감일: " + event.getEndDate());
    }
}
