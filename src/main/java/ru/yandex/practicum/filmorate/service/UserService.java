package ru.yandex.practicum.filmorate.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
//@RequiredArgsConstructor
@Setter
public class UserService extends Services<User> {

    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @Override
    public Set<User> getAllT() {
        return inMemoryUserStorage.getStorage();
    }

    @Override
    public User getT(long id) {
        if (!inMemoryUserStorage.getStorage().contains(User.builder().id(id).build())){
            log.warn("Попытка вернуть несуществующий объект");
            throw new NotFoundObjectException("Попытка вернуть несуществующий объект");
        }

        for (User user : inMemoryUserStorage.getStorage()) {
            if(user.equals(User.builder().id(id).build()))
                return user;
        }

        return null;
    }

    @Override
    public boolean putIdToSet(long idUser, long idFriend) {//Отправить запрос на добавление в друзья
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idFriend).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idFriend));

        for(User user: inMemoryUserStorage.getStorage()) {
            /*if (user.getId() == idUser)
                if (!user.getFriends().contains(idFriend))
                    user.getFriends().add(idFriend);*/
            if (user.getId() == idFriend) {
                if (user.getFriends().contains(idUser))
                    throw new ValidationException(String.format("Пользователю с id %s уже в друзьях", idFriend));

                if (!user.getRequestFriends().contains(idUser))
                    user.getRequestFriends().add(idUser);
                else throw new ValidationException(String.format("Запрос пользователю с id %s уже отправлен", idFriend));
            }

        }
        return true;
    }

    public boolean putAcceptOrRejectARequestToFriend(long idUser, long idFriend, String accept) {//Добавить в друзья
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idFriend).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idFriend));
        if(!(accept.equals("true") || accept.equals("t") || accept.equals("false") || accept.equals("f") ||
                accept.equals("yes") || accept.equals("y") || accept.equals("no") || accept.equals("n") ||
                accept.equals("on") || accept.equals("off") || accept.equals("1") || accept.equals("0")))
            throw new ValidationException(String.format("Указан невалидный флаг параметра accept"));

        User user = User.builder().id(idUser).build();
        User friend = User.builder().id(idFriend).build();

        for(User users : inMemoryUserStorage.getStorage()) {
            if (users.equals(user)) {
                if (!users.getRequestFriends().contains(idFriend))
                    throw new ValidationException(String.format("Запрос пользователю с id %s не был отправлен", idUser));
                else user = users;
            }

            if (users.equals(friend))
                friend = users;
        }

        if (accept.equals("false") || accept.equals("f") || accept.equals("no") ||
                accept.equals("n") || accept.equals("off") || accept.equals("0")) {
            user.getRequestFriends().remove(idFriend);
            return true;
        }

        if (!user.getFriends().contains(idFriend)) {
                user.getFriends().add(idFriend);
                user.getRequestFriends().remove(idFriend);
            }

        if (!friend.getFriends().contains(idUser))
            friend.getFriends().add(idUser);

        return true;
    }

    @Override
    public boolean deleteFromSet(long idUser, long idFriend) {//Удалить из друзей
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idFriend).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idFriend));

        for(User user: inMemoryUserStorage.getStorage()) {
            if (user.equals(User.builder().id(idUser).build()))
                if (user.getFriends().contains(idFriend))
                    user.getFriends().remove(idFriend);
            if (user.equals(User.builder().id(idFriend).build()))
                if (user.getFriends().contains(idUser))
                    user.getFriends().remove(idUser);

            return true;
        }
        return false;
    }

    public Set<User> getFriends(long idUser) {
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));

        Set<Long> friendsId = null;
        for(User user : inMemoryUserStorage.getStorage()) {
            if (user.getId() == idUser) {
                if (user.getFriends().isEmpty())
                    return Set.of();
                friendsId = user.getFriends();
                break;
            }
        }

        Set<User> friends = new HashSet<>();
        for (User user : inMemoryUserStorage.getStorage()) {
            if(friendsId.contains(user.getId())){
                friends.add(user);
            }
        }

        return friends;
    }

    public Set<User> getMutualFriends(long idUser, long otherId) {
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(idUser).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", idUser));
        if(!inMemoryUserStorage.getStorage().contains(User.builder().id(otherId).build()))
            throw new NotFoundObjectException(String.format("Пользователя с id %s нет в списке", otherId));

        Set<Long> mutualFriendsId = new HashSet<>();
        Set<User> mutualFriends = new HashSet<>();
        User firstUser = null;
        User secondUser = null;

        for (User user : inMemoryUserStorage.getStorage()) {
            if(user.getId() == idUser)
                firstUser = user;
            if (user.getId() == otherId)
                secondUser = user;
            if (firstUser != null && secondUser != null)
                break;
        }

        if (firstUser.getFriends().isEmpty() && secondUser.getFriends().isEmpty())
            return Set.of();

        for (Long id : firstUser.getFriends()) {
            if (secondUser.getFriends().contains(id))
                mutualFriendsId.add(id);
        }

        for (User user : inMemoryUserStorage.getStorage()) {
            if(mutualFriendsId.contains(user.getId())){
                mutualFriends.add(user);
            }
        }

        if (mutualFriends == null)
            return Set.of();

        return mutualFriends;
    }

    @Override
    public User postT(User user) {
        boolean isValid = !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now());
        if (user.getName().isEmpty())
            user.setName(user.getLogin());


        if (!isValid){
            log.warn("Произошла ошибка валидации при создании пользователя");
            throw new ValidationException("Произошла ошибка валидации при создании пользователя");
        }
        user.setId(generateId());
        inMemoryUserStorage.getStorage().add(user);
        user.setFriends(new HashSet<>());
        user.setRequestFriends(new HashSet<>());
        return user;
    }

    @Override
    public User putT(User user) {
        boolean isValid = !user.getLogin().contains(" ")
                && user.getBirthday().isBefore(LocalDate.now())
                && inMemoryUserStorage.getStorage().contains(user);
        if (user.getName().isEmpty())
            user.setName(user.getLogin());


        if (!isValid) {
            log.warn("Произошла ошибка валидации при обновлении данных пользователя");
            throw new NotFoundObjectException("Произошла ошибка валидации при обновлении данных пользователя");
        }

        User oldUser = null;
        for (User users : inMemoryUserStorage.getStorage()) {
            if (users.equals(user)) {
                oldUser = users;
                break;
            }
        }

        user.setFriends(oldUser.getFriends());
        user.setRequestFriends(oldUser.getRequestFriends());
        inMemoryUserStorage.getStorage().remove(user);
        inMemoryUserStorage.getStorage().add(user);

        return user;
    }
}
