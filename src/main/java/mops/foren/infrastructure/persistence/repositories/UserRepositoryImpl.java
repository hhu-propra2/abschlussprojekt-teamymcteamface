package mops.foren.infrastructure.persistence.repositories;

import mops.foren.domain.model.Forum;
import mops.foren.domain.model.User;
import mops.foren.domain.repositoryabstraction.IUserRepository;
import mops.foren.infrastructure.persistence.dtos.ForumDTO;
import mops.foren.infrastructure.persistence.dtos.UserDTO;
import mops.foren.infrastructure.persistence.mapper.ForumMapper;
import mops.foren.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private UserJpaRepository userRepository;

    public UserRepositoryImpl(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method checks if the user is not in the db.
     *
     * @param user that is checked.
     * @return false if user is in the db.
     */
    @Override
    public Boolean isUserNotInDB(User user) {
        return !this.userRepository.findById(user.getName()).isPresent();
    }

    /**
     * This methods adds a new user in the db.
     *
     * @param user the user that should be added.
     */
    @Override
    public void addNewUserToDB(User user) {
        UserDTO userDTO = UserMapper.mapUserToUserDto(user);
        this.userRepository.save(userDTO);
    }


    /**
     * This method fetches the user from the db.
     *
     * @param user that should be fetched.
     * @return the new updated user from the db.
     */
    @Override
    public User getUserFromDB(User user) {
        Optional<UserDTO> userDTO = this.userRepository.findById(user.getName());
        return UserMapper.mapUserDtoToUser(userDTO.get());
    }


    /**
     * This method adds a new forum to an user.
     * Also the forum will be saved in the db.
     *
     * @param user  the user, who has a new forum.
     * @param forum the forum that should be added.
     */
    @Override
    public void addForumToUser(User user, Forum forum) {
        UserDTO userDTO = this.userRepository.findById(user.getName()).get();
        ForumDTO forumDTO = ForumMapper.mapForumToForumDTO(forum);
        userDTO.getForums().add(forumDTO);
        this.userRepository.save(userDTO);
    }


}
