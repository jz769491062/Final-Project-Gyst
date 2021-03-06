package final_project_gyst;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/account_servlet")
public class account_servlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;  
    public account_servlet() 
    {
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();  

		String registering = request.getParameter("registering");
		if(registering != null)
		{
			String responsetext = "";
			if(registering.equals("true"))
			{
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String confirmpassword = request.getParameter("confirmpassword");
				
				DatabaseAccess am = new DatabaseAccess();
				
				//that username already exists
				if(am.userExists(username) == true)
				{
					responsetext = responsetext + "This username is already taken. ";
				}
				
				if(!password.equals(confirmpassword))
				{
					responsetext = responsetext + "The passwords do not match.";
				}
				
				//if no errors, create account and login
				if(am.userExists(username) == false && password.equals(confirmpassword))
				{
					Account created_account = am.createAccount(username, password);
					session.setAttribute("currentuser", username);
				}
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(responsetext);
			}
		}
		
		String login = request.getParameter("login");
		if(login != null)
		{
			String responsetext = "";
			if(login.equals("true"))
			{
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				DatabaseAccess am = new DatabaseAccess();
				
				//that username doesn't exist
				if(am.userExists(username) == false)
				{
					System.out.println("hi3");
					responsetext = responsetext + "This user does not exist. ";
				}
				//username exists, wrong
				else if(am.validate(username, password) == false)
				{
					responsetext = responsetext + "Incorrect password.";
				}
				//successful login, then set the session currentuser variable
				else
				{
					session.setAttribute("currentuser", username);
				}
				
				System.out.println(session.getAttribute("currentuser"));

				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(responsetext);
			}
		}
		
		String guestlogin = request.getParameter("guestlogin");
		if(guestlogin != null)
		{
			if(guestlogin.trim().equals("true"))
			{
				//let session know currentuser is a guest, do not create an account
				session.setAttribute("currentuser", "guest");
				System.out.println(session.getAttribute("currentuser"));
			}
		}
		
		String logout = request.getParameter("logout");
		if(logout != null)
		{
			if(logout.trim().equals("true"))
			{
				//let session know currentuser is a guest, do not create an account
				DatabaseAccess da = new DatabaseAccess();
				//delete all of the guest's stuff
				da.removeForGuestLogout();
				session.setAttribute("currentuser", "");
			}
			System.out.println(session.getAttribute("currentuser"));
		}
		
		String getcurrentID = request.getParameter("getcurrentID");
		if(getcurrentID != null)
		{
			if(getcurrentID.trim().equals("true"))
			{
				DatabaseAccess am = new DatabaseAccess();
				int userID = am.getIDFromUsername((String)session.getAttribute("currentuser"));
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(userID);
			}
		}
		
		String getcurrentusername = request.getParameter("getcurrentusername");
		if(getcurrentusername != null)
		{
			if(getcurrentusername.trim().equals("true"))
			{
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println((String)session.getAttribute("currentuser"));
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
