package com.food;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;




public class connect   {
	
	private static Scanner in;

	public static void main(String[] args)
	{	
	Connection con;
	
	
	
	try{  

		Class.forName("com.mysql.jdbc.Driver");  
		
		Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3307/OnlineFoodOrderDB","root","Cmohan@123");  
		//Statement stmt=con.createStatement()
		System.out.println("Connection established");
		Statement statement=con1.createStatement();
		Scanner scanner=new Scanner (System.in);
		PreparedStatement pstmt=con1.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?)");  
			 
			System.out.println("enter id");
			int Customer_id=in.nextInt();
			
			pstmt.setInt(1, Customer_id); 
			System.out.println("enter name");
			String name=in.next();
			pstmt.setString(2, name);
			System.out.println("enter email");
			String email=in.next();
	  		pstmt.setString(3, email);
	  		System.out.println("enter password");
			String password=in.next();
	  		pstmt.setString(4, password);
	  		System.out.println("enter address");
			String address=in.next();
	  		pstmt.setString(5, address);
					 
			pstmt.executeUpdate();
			
			con1.close();
			
		}catch(Exception e){  e.printStackTrace();}

	}

	}