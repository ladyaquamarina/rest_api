package controller;

import model.Event;
import model.File;
import model.User;
import repository.EventRepository;
import repository.FileRepository;

import java.util.List;

public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(User user, File file) {
        Event event = new Event(user, file);
        return eventRepository.create(event);
    }

    public Event getById(int id) {
        if (id < 1)
            return null;
        return eventRepository.getById(id);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    public Event update(Event event, User user) {
        if (user == null)
            return null;
        event.setUser(user);
        return eventRepository.update(event);
    }

    public Event update(Event event, File file) {
        if (file == null)
            return null;
        event.setFile(file);
        return eventRepository.update(event);
    }

    public void deleteById(int id) {
        if (id < 1)
            return;
        eventRepository.deleteById(id);
    }
}
