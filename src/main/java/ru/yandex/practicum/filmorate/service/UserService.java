package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User findUser(int id) {
        return userStorage.findUser(id);
    }

    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        validate(user);
        userStorage.createUser(user);
        return user;
    }

    public User updateUser(User user) {
        validate(user);
        userStorage.updateUser(user);
        return user;
    }

    public void deleteUser(int id) {
        User userToDelete = userStorage.findUser(id);
        if (userToDelete != null) {
            for (Long friendId : userToDelete.getFriends()) {
                User friend = userStorage.findUser(friendId);
                if (friend != null) {
                    friend.getFriends().removeIf(tempId -> tempId == id);
                }
            }
            userStorage.deleteUser(id);
        }
    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.findUser(id);
        User friend = userStorage.findUser(friendId);

        if (user != null && friend != null) {
            user.getFriends().add((long) friendId);
            friend.getFriends().add((long) id);
            log.info("Пользователи с id: {} и {} стали друзьями.", id, friendId);
        }
    }

    public void deleteFriend(int id, int friendId) {
        User user = userStorage.findUser(id);
        User friend = userStorage.findUser(friendId);

        if (user != null && friend != null) {
            user.getFriends().remove(user.getId());
            friend.getFriends().remove(user.getId());
            log.info("Пользователь удален из списка друзей.");
        }
    }

    public List<User> getFriends(int id) {
        User user = userStorage.findUser(id);
        List<User> userFriends = findAllUsers().stream()
                .filter(u -> user.getFriends().contains(u.getId()))
                .collect(Collectors.toList());

        log.info("Количество друзей у пользователя {}: {}.", user.getName(), userFriends.size());
        return userFriends;
    }

    public List<User> getCommonFriends(int id, int otherId) {
        List<User> userFriends = getFriends(id);
        List<User> otherUserFriends = getFriends(otherId);
        List<User> commonFriends = userFriends.stream()
                .filter(otherUserFriends::contains)
                .collect(Collectors.toList());
        log.info("Количество общих друзей: {}.", commonFriends.size());
        return commonFriends;
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            log.info("Логин не может содержать пробелы: {}.", user.getLogin());
            throw new ValidationException("Логин не может содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Автоматически добавлено имя пользователя: {}.", user.getName());
        }
    }
}
