/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Customer;
import entity.Product;
import entity.Purchase;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import secure.RoleLogic;
import session.CustomerFacade;
import session.ProductFacade;
import session.PurchaseFacade;

/**
 *
 * @author User
 */
@WebServlet(name = "UserController", urlPatterns = {
    "/addPurchase",
    "/showaddPurchase",
    "/showAddMoney",
    "/addMoney"})
public class UserController extends HttpServlet {

    @EJB
    private ProductFacade productFacade;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private PurchaseFacade purchaseFacade;

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
        if (session == null) {
            request.setAttribute("info", "You need in sign in");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        User reqUser = (User) session.getAttribute("reqUser");
        if (reqUser == null) {
            request.setAttribute("info", "You need in sign ins");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        RoleLogic rl = new RoleLogic();
        if (!rl.isRole("USER", reqUser)) {
            request.setAttribute("info", "You have not permissions");
            request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
            return;
        }
        String path = request.getServletPath();

        switch (path) {
            case "/showaddPurchase": {
                List<Product> listProducts = productFacade.findAll();
                request.setAttribute("listProducts", listProducts);
                List<Customer> listCustomers = customerFacade.findAll();
                //request.setAttribute("listCustomers", listCustomers);
                request.getRequestDispatcher("/addPurchase.jsp").forward(request, response);
                break;
            }
            case "/addPurchase": {
                String productId = request.getParameter("productId");
                Product product = productFacade.find(Long.parseLong(productId));
                //String customerId = request.getParameter("customerId");
                //Customer customer = customerFacade.find(Long.parseLong(customerId));
                Customer customer = reqUser.getCustomer();
                String quantity = request.getParameter("quantity");
                Calendar c = new GregorianCalendar();
                if (customer.getMoney() - (product.getPrice() * Integer.parseInt(quantity)) < 0) {
                    request.setAttribute("info", customer.getSurname() + " - �� ������� �����");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
                }
                if (product.getAmount() - Integer.parseInt(quantity) < 0) {
                    request.setAttribute("info", customer.getSurname() + " - �� ������� ������!");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
                }
                customer.setMoney(customer.getMoney() - product.getPrice() * Integer.parseInt(quantity));
                customerFacade.edit(customer);
                product.setAmount(product.getAmount() - Integer.parseInt(quantity));
                productFacade.edit(product);

                Purchase purchase = new Purchase(null, customer, product, c.getTime(), Integer.parseInt(quantity));
                purchaseFacade.create(purchase);
                request.setAttribute("info", customer.getSurname() + " ����� " + " " + quantity + " " + product.getName());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            }
            case "/showAddMoney":{
                request.getRequestDispatcher("/WEB-INF/addMoney.jsp").forward(request, response);
                break;
            }
            case "/addMoney":{
                String money = request.getParameter("money");
                Customer customer = reqUser.getCustomer();
                customer.setMoney(customer.getMoney()+Integer.parseInt(money));
                customerFacade.edit(customer);
                request.setAttribute("info", "User " + reqUser.getLogin()+" add "+money+ " money ");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            }
            default:
                request.setAttribute("info", "This page doesn't exist");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
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
