/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Customer;
import entity.Role;
import entity.User;
import entity.UserRoles;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.RoleFacade;
import session.UserFacade;
import session.UserRolesFacade;

/**
 *
 * @author User
 */
@WebServlet(name = "LoginController",loadOnStartup = 1, urlPatterns = {
    "/showLogin",
    "/login",
    "/logout",
    "/showRegistration",
    "/registration"})
public class LoginController extends HttpServlet {

    @EJB private CustomerFacade customerFacade;
    @EJB private UserFacade userFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private UserRolesFacade userRolesFacade;

    @Override
    public void init() throws ServletException {
        super.init(); 
        List <User> listUsers = userFacade.findAll();
        if(!listUsers.isEmpty()){
            return;
        }
        Customer customer = new Customer(0, "Admin", "Admin", 0, "email@email.com");
        customerFacade.create(customer);
        User user = new User("admin", "admin", customer);
        userFacade.create(user);
        Role role = new Role();
        role.setName("ADMINISTRATOR");
        roleFacade.create(role);
        UserRoles ur = new UserRoles();
        ur.setRole(role);
        ur.setUser(user);
        userRolesFacade.create(ur);
        role.setName("USER");
        roleFacade.create(role);
        ur.setRole(role);
        userRolesFacade.create(ur);
        
        
    }
    
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();
        if (path != null) {
            switch (path) {
                case "/showLogin":
                {
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                }
                    break;

                case "/login":
                {
                    String login = request.getParameter("login");
                    String password = request.getParameter("password");
                    User reqUser = userFacade.findByLogin(login);
                    if (reqUser==null){
                        request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                        request.setAttribute("info", "is not existed User");
                        break;
                    }
                    if(!password.equals(reqUser.getPassword())){
                        request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                        request.setAttribute("info", "Login or Password is incorrect");
                        break;
                    }
                    session = request.getSession(true);
                    session.setAttribute("reqUser", reqUser);
                    request.setAttribute("info", "Sign in");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
                }

                case "/logout":
                {
                    if(session != null){
                        session.invalidate();
                    }
                    request.setAttribute("info", "Sign out");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
                    
                case "/showRegistration": {
                    request.getRequestDispatcher("/showRegistration.jsp").forward(request, response);
                    break;
                }

                case "/registration": {
                    String name = request.getParameter("name");
                    String surname = request.getParameter("surname");
                    String money = request.getParameter("money");
                    String email = request.getParameter("email");
                    String login = request.getParameter("login");
                    String password1 = request.getParameter("password1");
                    String password2 = request.getParameter("password2");
                    
                    if (!password1.equals(password2)) {
                        request.setAttribute("info", "Your passwords are not the same");
                        request.setAttribute("name", name);
                        request.setAttribute("surname", surname);
                        request.setAttribute("email", email);
                        request.setAttribute("login", login);
                        request.setAttribute("money", money);
                        request.getRequestDispatcher("/showRegistration.jsp").forward(request, response);
                        break;
                    }
                    
                    Customer customer = new Customer(1L, name, surname, new Integer(money), email);
                    customerFacade.create(customer);                  
                    User reqUser = new User(login, password1, customer);
                    userFacade.create(reqUser);
                    
                    Role role = roleFacade.getRole("USER");
                    UserRoles ur = new UserRoles();
                    ur.setRole(role);
                    ur.setUser(reqUser);
                    userRolesFacade.create(ur);
                    
                    request.setAttribute("info", "Created user login is "+reqUser.getLogin());
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
                }

                default:
                    break;

            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
