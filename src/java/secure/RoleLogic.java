/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure;


import entity.User;
import entity.UserRoles;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.UserRolesFacade;


/**
 *
 * @author User
 */
public class RoleLogic {
    private UserRolesFacade userRolesFacade;

    public RoleLogic() {
        Context context;
        try {
            context = new InitialContext();
            this.userRolesFacade = (UserRolesFacade) context.lookup("java:module/UserRolesFacade");
        } catch (NamingException e) {
            Logger.getLogger(RoleLogic.class.getName()).log(Level.SEVERE, "Can't find the bin",e);
        }
    }
    
    public boolean isRole(String roleName, User user){
        List <UserRoles> listUserRoles = userRolesFacade.findUserRoles(user);
        List <String> listRoleNamesByUser = new ArrayList<>();
        for(UserRoles ur : listUserRoles){
            listRoleNamesByUser.add(ur.getRole().getName());
        }
        return listRoleNamesByUser.contains(roleName);
    }
}
