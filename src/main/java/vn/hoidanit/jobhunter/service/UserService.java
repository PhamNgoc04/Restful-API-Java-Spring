package vn.hoidanit.jobhunter.service;
import java.util.Optional;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserByID(long id) {
        //Cách 1
        // return this.userRepository.findById(id).orElse(null);
    
        //Cách 2
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public Iterable<User> fetchAllUser() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User user) {
        Optional<User> existingUserOptional = this.userRepository.findById(user.getId());

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Cập nhật các trường (ví dụ):
            if (user.getName() != null && !user.getName().equals(existingUser.getName())) {
                existingUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }

            return this.userRepository.save(existingUser);
        }

        return null;
    }

}
