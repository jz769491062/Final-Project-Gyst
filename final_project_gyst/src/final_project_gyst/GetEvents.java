package final_project_gyst;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetEvents
 */
@WebServlet("/GetEvents")
public class GetEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEvents() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Account currentUser = (Account) session.getAttribute("user");
		// now return as json all of the events

		/**
			Event Format for reference:
			title: title of event
			allDay: determines if event spans an entire day, or if it starts/ends at set time
			className: 5 different types, determines the color of the event
				-> default(transparent), important(red), chill(pink), success(green), info(blue)
				-> for our case, just use info color (blue)
			start: new Date(year, month, day, hour, minute) --> need to output as Date() object
			end: new Date(year, month, day, hour, minute)
			url: onclick function(?)

			CALENDAR DISPLAYS WITH CURRENT WEEK

			TODO: Add events feed (using JSON array) for entering into calendar
					--> NEED TO MAKE FUNCTION FOR AJAX CALL TO SERVLET (/GetEvents)

		**/
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String jsonOutput = "";
		for (Event e: currentUser.getEvents()) {
			// TODO: print json array format for events
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
