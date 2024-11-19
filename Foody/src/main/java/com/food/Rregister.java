package com.food;
import java.sql.*;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.mysql.jdbc.Driver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Rregister
 */
@WebServlet("/Rregister")
public class Rregister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rregister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();  
		          
		String n=request.getParameter("name");  
		String p=request.getParameter("password");  
		String e=request.getParameter("email");  
		String a=request.getParameter("address");  
		          out.print("YPPPP");
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		out.print('a');
		Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3307/OnlineFoodOrderDB","root","Cmohan@123");  
		   
		PreparedStatement ps=con.prepareStatement(  
		"insert into customer values(?,?,?,?,?)");  
		  int in=1;
		ps.setInt(1,in);  
		ps.setString(2,n);  
		ps.setString(3,e);  
		ps.setString(4,p); 
		ps.setString(5,a);  
		          
		int i=ps.executeUpdate();  
		if(i>0)  
		out.print("You are successfully registered...");  
		      
		          
		}catch (Exception e2) {System.out.println(e2);}  
		          
		out.close();  
		}  
	}


