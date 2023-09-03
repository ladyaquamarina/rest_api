package service;

import model.EventEntity;
import model.FileEntity;
import model.UserEntity;
import repository.EventRepository;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventEntity create(UserEntity userEntity, FileEntity fileEntity) {
        EventEntity eventEntity = new EventEntity(userEntity, fileEntity);
        return eventRepository.create(eventEntity);
    }

    public EventEntity getById(int id) {
        if (id < 1)
            return null;
        return eventRepository.getById(id);
    }

    public List<EventEntity> getAll() {
        return eventRepository.getAll();
    }

    public EventEntity update(EventEntity eventEntity, UserEntity userEntity) {
        if (userEntity == null)
            return null;
        eventEntity.setUser(userEntity);
        return eventRepository.update(eventEntity);
    }

    public EventEntity update(EventEntity eventEntity, FileEntity fileEntity) {
        if (fileEntity == null)
            return null;
        eventEntity.setFile(fileEntity);
        return eventRepository.update(eventEntity);
    }

    public void deleteById(int id) {
        if (id < 1)
            return;
        eventRepository.deleteById(id);
    }
}
