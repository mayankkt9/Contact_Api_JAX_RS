package com.mayank.demorest;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import com.mayank.model.Contact;
public class contactRepository {

	Connection con = null;
	
	
	public contactRepository() 
	{
		String url = "jdbc:mysql://localhost:3306/contact_db";
		String user = "root";
		String password = "Mayank@1881";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<Contact> getContacts()
	{
		List<Contact> contact = new ArrayList<>();
		String sql = "select * from contacts";
		System.out.println("show all contacts called");
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				Contact a = new Contact();
				a.setEmail(rs.getString(1));
				a.setName(rs.getString(2));
				a.setPhone_number(rs.getString(3));
				contact.add(a);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return contact;
		
	}
	
	
	
	public List<Contact> getContactsByName(String name)
	{
		
		String sql = "select * from contacts where name="+ "\"" +name + "\"";
		List<Contact> contact = new ArrayList<>();
		Statement st;
		
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				Contact a = new Contact();
				a.setEmail(rs.getString(1));
				a.setName(rs.getString(2));
				a.setPhone_number(rs.getString(3));
				contact.add(a);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return contact;
		
	}
	
	public List<Contact> getContactsByNamePagination(String name, String id)
	{
		
		
		List<Contact> contact = new ArrayList<>();
		contact = getContactsByName(name);
		int size=4;
		int id1=Integer.parseInt(id);
		int start = size*(id1-1);
		if(start+size>contact.size())
			return contact.subList(start, contact.size());
		
		
		
		return contact.subList(start, start+size);
		
	}
	
	
	
	
	
	
	
	
	public Contact getContactsByEmail(String email)
	{
		
		String sql = "select * from contacts where email="+ "\"" +email + "\"";
		int flag=0;
		Statement st;
		Contact a = new Contact();
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				
				a.setEmail(rs.getString(1));
				a.setName(rs.getString(2));
				a.setPhone_number(rs.getString(3));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		System.out.println("getContactByEmail - "+a);
		return a;
		
	}

	
	
	public int createContacts(Contact a1)
	{
		
		System.out.println("1");
		Contact check = getContactsByEmail(a1.getEmail());
		System.out.println("create "+check);
		if(check.getEmail()!="")
		{
			System.out.println("2");
			return 0;
		}
		
		String sql = "insert into contacts values(?,?,?)";
		
		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(1,a1.getEmail() );
			st.setString(2, a1.getName());
			st.setString(3, a1.getPhone_number());
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("3");
		return 1;
	}
	
	
	public void updateContacts(Contact a1)
	{
		
		
		String sql = "update contacts set name=?, phone_number=? where email=?";
		
		PreparedStatement st;
		try {
			st = con.prepareStatement(sql);
			st.setString(3,a1.getEmail() );
			st.setString(1, a1.getName());
			st.setString(2, a1.getPhone_number());
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		
		
	}
	
	public int deleteByEmail(String email)
	{	
		String sql = "delete from contacts where email=?";
		
		PreparedStatement st=null;
		int flag=-99;
		try {
			st = con.prepareStatement(sql);
			st.setString(1, email);
			System.out.println("delete by email "+email);
			flag=st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		System.out.println("ST  "+flag);
		return flag;
	}
	
	
}
