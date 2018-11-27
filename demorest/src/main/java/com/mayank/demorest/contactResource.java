package com.mayank.demorest;

import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mayank.model.Contact;

@Path("contacts")
public class contactResource {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	contactRepository repo = new contactRepository();
	
	@GET
	@Path("showall")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public List<Contact> getContact()
	{
		System.out.println("getContact Called");
		return repo.getContacts();
	}
	
	@GET
	@Path("searchByEmail/{email}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Contact getContactByEmail(@PathParam("email") String email)
	{
		System.out.println("getContact email Called");
		return repo.getContactsByEmail(email);
	}
	
	@GET
	@Path("searchByName/{name}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public List<Contact> getContactByName(@PathParam("name") String name)
	{
		System.out.println("getContact phone Called");
		return repo.getContactsByName(name);
	}
	
	
	@GET
	@Path("searchByNamePagination/{name}/{id}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public List<Contact> getContactByNamePagination(@PathParam("name") String name, @PathParam("id") String id)
	{
		System.out.println("getContact phone Called");
		return repo.getContactsByNamePagination(name,id);
	}
	
	
	
	
	
	@POST
	@Path("create")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response createContact(Contact a1)
	{
		
		System.out.println(a1.getEmail().isEmpty());
		if(a1.getEmail().isEmpty() == true)
			return Response.status(Response.Status.CONFLICT).entity("Email Id Not Entered").build();
		if(VALID_EMAIL_ADDRESS_REGEX .matcher(a1.getEmail()).find() == false)
			return Response.status(Response.Status.CONFLICT).entity("Not a valid Email").build();
		if(a1.getPhone_number().length() != 10)
			return Response.status(Response.Status.CONFLICT).entity("Phone Number must be of 10 digit").build();
		int flag = repo.createContacts(a1);
		if(flag==0)
		return Response.status(Response.Status.CONFLICT).entity("Email Id Already Present").build();
		else
		return Response.status(Response.Status.CREATED).entity("Contact Created").build();
	}
	@PUT
	@Path("update")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Contact updateContact(Contact a1)
	{
		System.out.println(a1);
		repo.updateContacts(a1);
		return a1;
	}
	
	
	@DELETE
	@Path("deleteByEmail/{email}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response deleteByEmail(@PathParam("email") String email)
	{
		System.out.println("getContact email Called");
		if(repo.deleteByEmail(email)==1)
		{
			return Response.status(Response.Status.ACCEPTED).entity("Entry Deleted").build();
		}
		else
		{
			return Response.status(Response.Status.CONFLICT).entity("Email Id Not Present").build();
		}
	}
	
	
	@GET
	@Path("auth")
	@Produces(MediaType.TEXT_PLAIN)
	public String securedMethod()
	{
		return "THE API IS SECURED";
	}
	
	

}
