package com.food;

import java.sql.*;
import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class addToCart
 */
@WebServlet("/addToCart")
public class addToCart extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static int FO_idCounter = 1;
    private static int Order_idCounter = 1;

    public static synchronized int FO_createID() {
        return FO_idCounter++;
    }

    public static synchronized int Order_createID() {
        return Order_idCounter++;
    }

    public addToCart() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int selectedID = 0;
        try {
            selectedID = Integer.parseInt(request.getParameter("p"));
        } catch (NumberFormatException ex) {
            System.err.println("Illegal input");
        }

        // Initialize DB variables
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3307/OnlineFoodOrderDB", "root", "Cmohan@123");

            HttpSession session = request.getSession();

            if (selectedID >= 1 && selectedID <= 9) {
                // Query for food
                ps = con.prepareStatement("SELECT * FROM food WHERE food_id = ?");
                ps.setInt(1, selectedID);
                rs = ps.executeQuery();

                if (rs.next()) {
                    int food_id = rs.getInt(1);
                    String food_name = rs.getString(2);
                    int food_price = rs.getInt(3);

                    session.setAttribute("food_id", food_id);
                    session.setAttribute("food_name", food_name);
                    session.setAttribute("food_price", food_price);

                    // Insert into foodOrder table
                    ps = con.prepareStatement("INSERT INTO foodOrder VALUES(?,?,?,?,?)");
                    int FO_id = FO_createID();
                    int order_id = Order_createID();
                    session.setAttribute("order_id", order_id);
                    int totalPrice = food_price;

                    ps.setInt(1, FO_id);
                    ps.setInt(2, order_id);
                    ps.setInt(3, food_id);
                    ps.setInt(4, 1);  // Assuming quantity = 1
                    ps.setInt(5, totalPrice);

                    ps.executeUpdate();
                }

                // Forward to welcome page after adding to cart
                RequestDispatcher rd = request.getRequestDispatcher("Welcome.jsp");
                rd.forward(request, response);

            } else if (selectedID >= 101 && selectedID <= 103) {
                // Query for drinks
                ps = con.prepareStatement("SELECT * FROM drinks WHERE drink_id = ?");
                ps.setInt(1, selectedID);
                rs = ps.executeQuery();

                if (rs.next()) {
                    int drink_id = rs.getInt(1);
                    String drink_name = rs.getString(2);
                    int drink_price = rs.getInt(3);

                    session.setAttribute("drink_id", drink_id);
                    session.setAttribute("drink_name", drink_name);
                    session.setAttribute("drink_price", drink_price);

                    // Insert into drinkOrder table
                    ps = con.prepareStatement("INSERT INTO drinkOrder VALUES(?,?,?,?,?)");
                    int DO_id = FO_createID();
                    int order_id = Order_createID();
                    session.setAttribute("order_id", order_id);
                    int totalPrice = drink_price;

                    ps.setInt(1, DO_id);
                    ps.setInt(2, order_id);
                    ps.setInt(3, drink_id);
                    ps.setInt(4, 1);  // Assuming quantity = 1
                    ps.setInt(5, totalPrice);

                    ps.executeUpdate();
                }

                // Forward to welcome page after adding to cart
                RequestDispatcher rd = request.getRequestDispatcher("Welcome.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
