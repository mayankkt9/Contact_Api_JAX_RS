package com.mayank.demorest;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import sun.util.locale.StringTokenIterator;



@Provider
public class SecurityFilter implements ContainerRequestFilter{
	
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URL_PREFIX = "contacts";
	

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("filter called");
		
		if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX))
		{
			List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if(authHeader != null && authHeader.size()>=0)
			{
				String authToken = authHeader.get(0);
				authToken = authToken.replaceAll(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString = org.glassfish.jersey.internal.util.Base64.decodeAsString(authToken);
				StringTokenizer tokenizer = new StringTokenizer(decodedString,":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if("mayank".equals(username) && "mayank123".equals(password))
				{
					return;
				}
				
			}
			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity("USER CANNOT ACCESS THE RESOURCE").build();
			requestContext.abortWith(unauthorizedStatus);
				
		}
	}
		
}
	

