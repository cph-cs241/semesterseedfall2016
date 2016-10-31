package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import security.IUser;

@Entity
public class Person implements IUser, Serializable{
  
        private static final long serialVersionUID = 1L;
        
  private String password;  //Pleeeeease dont store me in plain text
  private String userName;

  
    @ManyToMany(cascade = CascadeType.PERSIST)//just many to many annotation will work
            @JoinTable(name="person_roles",
        joinColumns=
            @JoinColumn(name="persons_ID", referencedColumnName="ID"),
        inverseJoinColumns=
            @JoinColumn(name="roles_ID", referencedColumnName="ID")
        )
    List<Roles> roles = new ArrayList();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    

    public Person() {
    }

    
    //make the password hash from here and throw the exception
  public Person(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }
  
  //not necessary to add this constructor
  public Person(String userName, String password,List<Roles> roles) {
    this.userName = userName;
    this.password = password;
    this.roles = roles;
  }
  
  public void addRole(Roles role){
    roles.add(role);
  }
    
  @Override
  public List<String> getRolesAsStrings() {
      List<String> myRoles = new ArrayList();
      for (Roles role : roles) {
          String r = role.getName();
          myRoles.add(r);
      }
   return myRoles;
  }
 
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

 
          
}
