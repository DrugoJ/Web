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
@WebServlet(name = "AdminController", urlPatterns = {
    "/addProduct",
    "/createProduct"})
public class AdminController extends HttpServlet {
@EJB private ProductFacade productFacade;
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
        if(session == null){
            request.setAttribute("info", "You need in sign in");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        User reqUser = (User) session.getAttribute("reqUser");
        if(reqUser == null){
            request.setAttribute("info", "You need in sign ins");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        RoleLogic rl = new RoleLogic();
        if(!rl.isRole("ADMINISTRATOR",reqUser)){
            request.setAttribute("info", "You have not permissions");
            request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
            return;
        }
        String path = request.getServletPath();
        switch (path) {
            case "/addProduct":
                request.getRequestDispatcher("/addProduct.jsp").forward(request, response);
                break;
            case "/createProduct":
                {
                    String name = request.getParameter("name");
                    String price = request.getParameter("price");
                    String amount = request.getParameter("amount");
                    Product product = new Product(null, name,new Integer(price), new Integer(amount));
                    productFacade.create(product);
                    request.setAttribute("info", product);
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
