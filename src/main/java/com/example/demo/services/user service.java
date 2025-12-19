public class UserService {
private final UserRepository repo;
private final PasswordEncoder encoder;


public UserService(UserRepository repo, PasswordEncoder encoder) {
this.repo = repo;
this.encoder = encoder;
}


public User register(User user) {
user.setPassword(encoder.encode(user.getPassword()));
return repo.save(user);
}


public User findByEmail(String email) {
return repo.findByEmail(email)
.orElseThrow(() -> new ApiException("user not found"));
}
}