package final_project_gyst;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.*;
import com.oracle.jrockit.jfr.EventInfo;

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
		
		//Account currentUser = new Account("abc", "123");
		// generate pre-made events to test servlet functionality
		//Event a = new Event((long) 0, "event1", "2019-04-09T12:30:00", "2019-04-09T13:30:00", "note1", "loc1", currentUser, false);
		//Event b = new Event((long) 1, "event2", "2019-04-08T10:30:00", "2019-04-08T12:30:00", "note2", "loc2", currentUser, false);
		// now return as json all of the events
		
		// currentUser.addHostedEvent(a);
		// System.out.println("added a");	
		// currentUser.addHostedEvent(b);
		// System.out.println("added b");
		ArrayList<Event> AllEvents = new ArrayList<Event>();
		//TODO: get all events in the database
		DatabaseAccess d = new DatabaseAccess();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ArrayList <EventInfo> events_to_send = d.getEvents(currentUser.getUserName());//these are the events that this user got shared
		ArrayList<CalendarEvent> eventsToSend = new ArrayList<CalendarEvent>();
		for (int i=0;i<events_to_send.size();i++) {
			
			Event tempevent = new Event(events_to_send.get(i).user_ID,
					events_to_send.get(i).eventname,
					events_to_send.get(i).start,
					events_to_send.get(i).end,
					events_to_send.get(i).note,
					events_to_send.get(i).location,
					currentUser , false//FIXME:I set it to false, but the event could be allday! Need an allday boolean in EventInfo class as well
					);
			CalendarEvent toSend = new CalendarEvent(tempevent); // generate new calendar formatted event to send to frontend
			eventsToSend.add(toSend);
		}
		
		// for (Event e: currentUser.getHostedEvents()) {
		// 	CalendarEvent toSend = new CalendarEvent(e); // generate new calendar formatted event to send to frontend
		// 	eventsToSend.add(toSend);
		// }
		
		//the host must be in the people_shared arraylist too, so just go over this arraylist is enough
//		for (Event e: AllEvents) {
//			for(int i =0; i< e.people_shared.size();i++){
//				if(e.people_shared.elementData(i).getUserName()==currentUser.getUserName()){
//					CalendarEvent toSend = new CalendarEvent(e); // generate new calendar formatted event to send to frontend
//					eventsToSend.add(toSend);
//				}
//			}
//			
//		}

		
		// create json array from CalendarEvent class
		Gson gson = new Gson();
		String jsonOutput = gson.toJson(eventsToSend);
		out.println(jsonOutput);
		out.flush();

		request.setAttribute("eventsToSend", jsonOutput);
		request.getRequestDispatcher("fullcalendar.html").forward(request, response);
		//TODO: in fullcalendar.html, get the eventsToSend parameter and populate the events on the calendar page
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
