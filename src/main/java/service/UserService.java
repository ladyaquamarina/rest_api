package service;

import model.EventEntity;
import model.UserEntity;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(String name) {
        if (name.isEmpty())
            return null;
        UserEntity userEntity = new UserEntity(name);
        return userRepository.create(userEntity);
    }

    public UserEntity getById(int id) {
        if (id < 1)
            return null;
        return userRepository.getById(id);
    }

    public List<UserEntity> getAll() {
        return userRepository.getAll();
    }

    public UserEntity update(UserEntity userEntity, String newName) {
        if ((newName.isEmpty()) || (getById(userEntity.getId()) == null))
            return null;
        userEntity.setName(newName);
        return userRepository.update(userEntity);
    }

    public UserEntity addEvent(UserEntity userEntity, EventEntity eventEntity) {
        if (getById(userEntity.getId()) == null)
            return null;
        userEntity.addEvent(eventEntity);
        return userRepository.update(userEntity);
    }

    public UserEntity deleteEvent(UserEntity userEntity, int id) {
        if ((id < 1) || (getById(userEntity.getId()) == null))
            return null;
        userEntity.deleteEvent(id);
        return userRepository.update(userEntity);
    }

    public void deleteById(int id) {
        if ((id < 1) || (getById(id) == null))
            return;
        userRepository.deleteById(id);
    }
}
