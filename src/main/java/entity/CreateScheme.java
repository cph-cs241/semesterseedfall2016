/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import facades.UserFacade;
import javax.persistence.Persistence;

/**
 *
 * @author Cherry Rose Semeña
 */
public class CreateScheme {
    public static void main(String[] args) {
        
//        Persistence.generateSchema("pu", null);
        UserFacade f = new UserFacade();
        
        Roles r1 = new Roles("Admin");
        Roles r2 = new Roles("User");
        Person p1 = new Person("user", "test");
        p1.addRole(r2);
        f.addUser(p1);
        Person p2 = new Person("admin", "test");
        p2.addRole(r1);
        f.addUser(p2);
        Person p3 = new Person("admin_user", "test");
        p3.addRole(r1);
        p3.addRole(r2);
        f.addUser(p3);
        
    }
}
