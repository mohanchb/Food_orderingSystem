package com.food;

import java.sql.*;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

/**
 * Servlet implementation class loginCheck
 */
@WebServlet("/loginCheck")
public class loginCheck extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    String emailID, password;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginCheck() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emaild = request.getParameter("email");
        String password = request.getParameter("password"); 
        
        try {  
            // Load MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");  
            
            // Establish a connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/OnlineFoodOrderDB", "root", "Cmohan@123");  
            Statement stmt = con.createStatement();
            
            // Execute the query to check user credentials
            ResultSet rs = stmt.executeQuery("select * from customer where customer_emaillD='" + emaild + "'"); 
            
            // Check if the result set is empty (no user found) or password does not match
            if (rs.next()) { // If the user is found
                if (rs.getString("customer_password").equals(password)) { // Check the password
                    RequestDispatcher rd = request.getRequestDispatcher("Welcome.jsp");  
                    rd.forward(request, response);
                } else { 
                    // If password does not match, redirect to loginError.html
                    RequestDispatcher rd = request.getRequestDispatcher("LoginError.html");  
                    rd.include(request, response);  
                }
            } else { 
                // If no user is found, redirect to loginError.html
                RequestDispatcher rd = request.getRequestDispatcher("LoginError.html");  
                rd.include(request, response);  
            }

            // Close the connection
            con.close();
        } catch (Exception e) {  
            e.printStackTrace();
        }
    }
}
