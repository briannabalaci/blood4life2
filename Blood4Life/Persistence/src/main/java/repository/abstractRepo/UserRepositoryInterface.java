package repository.abstractRepo;

import domain.User;
import domain.enums.BloodType;
import domain.enums.Rh;

import java.util.List;

public interface UserRepositoryInterface extends RepositoryInterface<Long, User> {
    User findUserByCNP(String cnp);
    User findUserByEmail(String email);
    List<User> findUsersByBloodTypeAndRh(BloodType bloodType, Rh rh);
}
