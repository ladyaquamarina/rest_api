package controller;

import model.Event;
import model.User;
import repository.UserRepository;

import java.util.List;

public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String name) {
        if (name.isEmpty())
            return null;
        User user = new User(name);
        return userRepository.create(user);
    }

    public User getById(int id) {
        if (id < 1)
            return null;
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User update(User user, String newName) {
        if ((newName.isEmpty()) || (getById(user.getId()) == null))
            return null;
        user.setName(newName);
        return userRepository.update(user);
    }

    public User addEvent(User user, Event event) {
        if (getById(user.getId()) == null)
            return null;
        user.addEvent(event);
        return userRepository.update(user);
    }

    public User deleteEvent(User user, int id) {
        if ((id < 1) || (getById(user.getId()) == null))
            return null;
        user.deleteEvent(id);
        return userRepository.update(user);
    }

    public void deleteById(int id) {
        if ((id < 1) || (getById(id) == null))
            return;
        userRepository.deleteById(id);
    }
}
