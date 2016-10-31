package security;

import entity.Roles;
import java.util.List;

public interface IUser {
  List<String>  getRolesAsStrings();
  String getUserName();
  String getPassword();
}
