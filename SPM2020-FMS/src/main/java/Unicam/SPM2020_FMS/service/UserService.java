package Unicam.SPM2020_FMS.service;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;

public interface UserService {

  int register(User user);

  User validateUser(Login login);

User update(User user);
}
