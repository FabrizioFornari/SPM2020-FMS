package Unicam.SPM2020_FMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;
import Unicam.SPM2020_FMS.dao.UserDao;

public class UserServiceImpl implements UserService {

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public UserDao userDao;

  public int register(User user) {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userDao.register(user);
  }

  public User validateUser(Login login) {
	
	
    return userDao.validateUser(login);
  }


public int update(User user) {
	
	return userDao.update(user);
	
}

}
