package facades;

import security.PasswordStorage;
import security.IUserFacade;
import entity.Person;
import entity.Roles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import security.IUser;

public class UserFacade implements IUserFacade {
  /*When implementing your own database for this seed, you should NOT touch any of the classes in the security folder
    Make sure your new facade implements IUserFacade and keeps the name UserFacade, and that your Entity Person class implements 
    IUser interface, then security should work "out of the box" with users and roles stored in your database */
  
  private final  Map<String, IUser> users = new HashMap<>();
  
  EntityManagerFactory emf;

    public UserFacade() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        insertData();
        getAllUsers();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    private void insertData(){
        Roles r1 = new Roles("Admin");
        Roles r2 = new Roles("User");
        Person p1 = new Person("user", "test");
        p1.addRole(r2);
        addUser(p1);
        Person p2 = new Person("admin", "test");
        p2.addRole(r1);
        addUser(p2);
        Person p3 = new Person("admin_user", "test");
        p3.addRole(r1);
        p3.addRole(r2);
        addUser(p3);
    }
  
    @Override
  public IUser getUserByUserId(String id){
    return users.get(id);
  }
  /*
  Return the Roles if users could be authenticated, otherwise null
  */
    @Override
  public List<String> authenticateUser(String userName, String password){
    IUser user = users.get(userName);
    
    String hashPW;
    String wholeHash;
    
      try {
          hashPW = PasswordStorage.createHash(password);
          wholeHash = PasswordStorage.createHash(user.getPassword());
           if(PasswordStorage.verifyPassword(hashPW, wholeHash)){
              return user != null && user.getPassword().equals(password) ? user.getRolesAsStrings(): null;
           } 
      } catch (PasswordStorage.CannotPerformOperationException ex) {
          System.out.println("ERROR1:" + ex);
      } catch (PasswordStorage.InvalidHashException ex) {
          System.out.println("ERROR2:" + ex);
      }
    return user.getRolesAsStrings();
  }

    private void getAllUsers() {
        EntityManager em = getEntityManager();

        try {
            List<Person> persons = em.createQuery("Select p from Person p").getResultList();
            for (Person person : persons) {
                users.put(person.getUserName(), person);
                System.out.println(person.getUserName() + person.getPassword());
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            em.close();
        }
    }
    
     private List<Roles> getRoles() {
        EntityManager em = getEntityManager();
        
        try {
            List<Roles> roles = em.createQuery("Select r from Roles r").getResultList();
            return roles;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        } finally {
            em.close();
        }
    }
  
    
    public Person addUser(Person p) {
        EntityManager em = getEntityManager();
//        List<Roles> roles = getRoles();
        
        try {
            em.getTransaction().begin();
            String hashPW = PasswordStorage.createHash(p.getPassword());
            p.setPassword(hashPW);
            em.persist(p);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            
        } finally {
            em.close();
        }
            return p;
    }
}
