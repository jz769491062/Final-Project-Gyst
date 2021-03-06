package final_project_gyst;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import javafx.util.Pair;

public class DatabaseAccess 
{
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	
	public DatabaseAccess()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Gyst?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=password123");
		}
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		
		if(!userExists("guest"))
		{
			createAccount("guest", "impossiblestring123456789765432345654345434543");
		}
	}
	
	//validate
	public boolean validate(String username, String userPassword)
	{
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM UserInfo WHERE Username=? AND User_Password=?");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setString(1, username);
			ps.setString(2, userPassword);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			
			return matchExists;
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return false;
		}
	}
	
	public boolean userExists(String username)
	{
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM UserInfo WHERE Username=?");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setString(1, username);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			
			return matchExists;
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return false;
		}
	}
	
	public boolean ID_Exists(int ID)
	{
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM UserInfo WHERE User_ID=?");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setInt(1, ID);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			
			return matchExists;
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return false;
		}
	}
	
	public boolean EventID_Exists(int ID)
	{
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM User_Events WHERE Event_ID=?");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setInt(1, ID);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) > 0);
			}
			
			return matchExists;
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return false;
		}
	}
	
	public boolean ToDoEventID_Exists(int ID)
	{
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM ToDoEvents WHERE ToDoEvent_ID=?");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setInt(1, ID);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			
			return matchExists;
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return false;
		}
	}
	
	public String getUsernameFromID(int ID)
	{
		String result = null;
		//if false ID
		if(!ID_Exists(ID))
		{
			return result;
		}
		
		try
		{
			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE User_ID=?");
			ps.setInt(1, ID);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				result = rs.getString("Username");
			}
			return result;
		}
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return result;
		}
	}
	
	public int getIDFromUsername(String username)
	{
		int result = -1;
		//if false ID
		if(!userExists(username))
		{
			return result;
		}
		
		try
		{
			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE Username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				result = rs.getInt("User_ID");
			}
			return result;
		}
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return result;
		}
	}
	
	//create account
	public Account createAccount(String username, String password)
	{
		Account acc = null;
		try {
			//check username if it exists
			boolean check = false;
			ps = conn.prepareStatement("SELECT COUNT(*) FROM UserInfo WHERE Username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				check = (rs.getInt(1) == 0);

			}
			//if there is already a user with that username that exists, return false
			if(check == false) 
			{
				return null;
			}
			
			//check if we have made a valid ID
			Random rand = new Random();
			//random number between 0 and 5 million
			int ID_number = rand.nextInt(5000000);
			//keep generating till we find an ID number not in use, make a unique id number
			while(ID_Exists(ID_number))
			{
				ID_number = rand.nextInt(5000000);
			}
			
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("INSERT INTO UserInfo(User_ID, Username, User_Password) VALUES(?,?,?)");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setInt(1, ID_number);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
			
			acc = new Account(username);
			acc.setUserId((long)ID_number);
						
			return acc;
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return null;
		}
	}
	
	public int generateEvent_ID()
	{
		//check if we have made a valid ID
		Random rand = new Random();
		//random number between 0 and 5 million
		int ID_number = rand.nextInt(5000000);
		//keep generating till we find an ID number not in use, make a unique id number
		while(EventID_Exists(ID_number))
		{
			ID_number = rand.nextInt(5000000);
		}
		
		return ID_number;
	}
	
	public int generateToDoEvent_ID()
	{
		//check if we have made a valid ID
		Random rand = new Random();
		//random number between 0 and 5 million
		int ID_number = rand.nextInt(5000000);
		//keep generating till we find an ID number not in use, make a unique id number
		while(ToDoEventID_Exists(ID_number))
		{
			ID_number = rand.nextInt(5000000);
		}
		
		return ID_number;
	}
	
	//keep it this syntax because we will likely need to update the database without rewriting every time
	public void addEvent(int eventID, String username, String eventname, String location, String start, String end, String notes, String host)
	{
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("INSERT INTO User_Events(Event_ID, User_ID, Event_name, location, Start_time, End_time, notes, Host_ID) VALUES("+ eventID +","+getIDFromUsername(username)+",?,?,?,?,?,"+getIDFromUsername(host)+")");
			//replace first question mark with the firstName variable. question mark means a variable will go there
		
			/*
			ps.setInt(1, getIDFromUsername(username));
			*/
			
			ps.setString(1, eventname);
			ps.setString(2, location);
			ps.setString(3, start);
			ps.setString(4, end);			
			ps.setString(5, notes);			
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public void addToSharedEvents(int eventID, String username)
	{
		try {
			ps = conn.prepareStatement("INSERT INTO sharedevents(eventid, userid, username) VALUES("+ eventID +", "+getIDFromUsername(username)+", '"+username+"')");
			ps.executeUpdate();
		} 
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
//	public void removeOneEvent(Event event, String username)
//	{
//		int EventID = (int)event.getId();
//		if(!userExists(username))
//			return;
//		try {
//			//after ? comes the value user input 
//			//where not exists, only insert row
//			ps = conn.prepareStatement("DELETE FROM User_Events WHERE Event_ID="+EventID+" AND User_ID="+getIDFromUsername(username));
//			ps.executeUpdate();
//		}
//		
//		catch(SQLException sqle)
//		{
//			System.out.println("sqle: " + sqle.getMessage());
//		}
//	} 
	
	//removes all events with this event ID
	public void removeEvent(Event event)
	{
		int EventID = (int)event.getId();
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("DELETE FROM User_Events WHERE Event_ID="+EventID);
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	} 
	
	public void removeOneEvent(int eventID, String username)
	{
		try {
			ps = conn.prepareStatement("DELETE FROM User_Events WHERE Event_ID="+eventID+" AND User_ID="+getIDFromUsername(username));
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	//make sure you store before as before=this; then do whatever change then after=this
	/*
	public void changeEvent(String username, Event after)
	{
		try {
			ps = conn.prepareStatement("SELECT COUNT(*) FROM User_Events WHERE Event_ID="+after.getId());
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) > 0);
			}
			
			if(matchExists)
			{
				//delete all old instances of this
				removeEvent(after);
				//to update, add updated information
				addEvent(username, after.getEventName(), after.getLocation(), after.getStart(), after.getEnd(), after.getNotes(), username);
			}
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	*/
	
	public void changeEventInfo(String username, EventInfo after, int eventID)
	{
		username = username.trim();
		if(userExists(username) == false || EventID_Exists(eventID) == false)
		{
			return;
		}
		
		try {
			//delete
			ps = conn.prepareStatement("DELETE FROM User_Events WHERE Event_ID="+eventID+" AND User_ID="+getIDFromUsername(username));
			ps.executeUpdate();
			
			//then insert
			ps = conn.prepareStatement("INSERT INTO User_Events(Event_ID, User_ID, Event_name, location, Start_time, End_time, notes, Host_ID) VALUES("+eventID+","+getIDFromUsername(username)+",?,?,?,?,?,"+getIDFromUsername(username)+")");
			ps.setString(1, after.eventname);
			ps.setString(2, after.location);
			ps.setString(3, after.start);
			ps.setString(4, after.end);			
			ps.setString(5, after.notes);			
			ps.executeUpdate();
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public HashSet<EventInfo> getEvents(String username)
	{
		if(userExists(username) == false)
			return null;
		
		HashSet<EventInfo> result = new HashSet<EventInfo>(); 
		try {
			ps = conn.prepareStatement("SELECT * FROM User_Events WHERE User_ID="+getIDFromUsername(username));
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				int user_ID = rs.getInt("User_ID");
				String eventname = rs.getString("Event_name");
				String location = rs.getString("location");
				String start = rs.getString("Start_time");
				String end = rs.getString("End_time");
				String notes = rs.getString("notes");
				EventInfo newEventInfo = new EventInfo(user_ID, eventname, location, start, end, notes);
				result.add(newEventInfo);
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		return result;
	}
	
	public EventInfo getOneEvent(int eventID, String username)
	{
		username = username.trim();
		if(userExists(username) == false)
			return null;
		
		EventInfo result = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM User_Events WHERE User_ID="+getIDFromUsername(username)+" AND Event_ID="+eventID);
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				int user_ID = rs.getInt("User_ID");
				String eventname = rs.getString("Event_name");
				String location = rs.getString("location");
				String start = rs.getString("Start_time");
				String end = rs.getString("End_time");
				String notes = rs.getString("notes");
				EventInfo newEventInfo = new EventInfo(user_ID, eventname, location, start, end, notes);
				result = newEventInfo;
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		return result;
	}
	
	public EventInfo getEventWithIDUsername(int id, String username)
	{
		if(userExists(username) == false)
			return null;
		
		EventInfo result = new EventInfo(0,"","","","","");
		boolean initialized = false;
		try {
			ps = conn.prepareStatement("SELECT * FROM User_Events WHERE Event_ID="+id+" AND User_ID="+getIDFromUsername(username));
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				int user_ID = rs.getInt("User_ID");
				String eventname = rs.getString("Event_name");
				String location = rs.getString("location");
				String start = rs.getString("Start_time");
				String end = rs.getString("End_time");
				String notes = rs.getString("notes");
				EventInfo newEventInfo = new EventInfo(user_ID, eventname, location, start, end, notes);
				result = newEventInfo;
				initialized = true;
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		
		if(initialized == false)
		{
			return null;
		}
		return result;
	}
	
	
	public boolean isUserInEvent(int userID, int eventID)
	{
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM User_Events WHERE Event_ID=? AND User_ID="+userID);
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setInt(1, eventID);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			
			return matchExists;
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
			return false;
		}
	}
	
	public void addToDoEvent(int eventID, String username, String eventname, String location, String start, String end, boolean block, String notes)
	{
		if(!userExists(username))
			return;
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("INSERT INTO ToDoEvents(ToDoEvent_ID, User_ID, Event_name, location, Start_time, End_time, User_block, notes) VALUES("+eventID+","+getIDFromUsername(username)+",?,?,?,?,"+block+",?)");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			//ps.setInt(1, getIDFromUsername(username));
			ps.setString(1, eventname);
			ps.setString(2, location);
			ps.setString(3, start);
			ps.setString(4, end);
			//ps.setBoolean(5, block);
			ps.setString(5, notes);
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public void removeToDoEvent(String username, String eventname, String location, String start, String end, boolean block, String notes)
	{
		if(!userExists(username))
			return;
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("DELETE FROM ToDoEvents WHERE User_ID="+getIDFromUsername(username)+" AND Event_name=? AND location=? AND Start_time=? AND End_time=? AND User_block="+block+" AND notes=?");
			//replace first question mark with the firstName variable. question mark means a variable will go there
			//ps.setInt(1, getIDFromUsername(username));
			ps.setString(1, eventname);
			ps.setString(2, location);
			ps.setString(3, start);
			ps.setString(4, end);
			//ps.setBoolean(5, block);
			ps.setString(5, notes);
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public void removeOneToDoEvent(ToDoEventInfo event, String username)
	{
		username = username.trim();
		int EventID = event.event_ID;
		if(!userExists(username))
			return;
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("DELETE FROM ToDoEvents WHERE ToDoEvent_ID="+EventID+" AND User_ID="+getIDFromUsername(username));
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	} 
	
	public void removeOneToDoEvent_eventIDusername(int eventID, String username)
	{
		username = username.trim();
		int EventID = eventID;
		if(!userExists(username))
			return;
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("DELETE FROM ToDoEvents WHERE ToDoEvent_ID="+EventID+" AND User_ID="+getIDFromUsername(username));
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	} 
	
	//removes all events with this event ID
	public void removeAllToDoEvents(Event event)
	{
		int EventID = (int)event.getId();
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("DELETE FROM ToDoEvents WHERE ToDoEvent_ID="+EventID);
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	} 
	
	//removes all events with this event ID
	public void removeForGuestLogout()
	{
		int userID = getIDFromUsername("guest");
		try {
			//after ? comes the value user input 
			//where not exists, only insert row
			ps = conn.prepareStatement("DELETE FROM ToDoEvents WHERE User_ID="+userID);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("DELETE FROM User_Events WHERE User_ID="+userID);
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	} 
	
	public void changeToDoEvent(String username, ToDoEventInfo after)
	{
		username = username.trim();
		try {
			ps = conn.prepareStatement("SELECT COUNT(*) FROM ToDoEvents WHERE ToDoEvent_ID="+after.event_ID+" AND User_ID="+getIDFromUsername(username));
			rs = ps.executeQuery();			
				
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			
			if(matchExists)
			{
				ToDoEventInfo toremove = new ToDoEventInfo(after.event_ID, getIDFromUsername(username), after.eventname, after.location, after.start, after.end, after.block, after.notes);
				removeOneToDoEvent(toremove, username);
				addToDoEvent(after.event_ID, username, after.eventname, after.location, after.start, after.end, after.block, after.notes);
			}
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public ArrayList<ToDoEventInfo> getToDoEvents(String username)
	{
		if(userExists(username) == false)
			return null;
		
		ArrayList<ToDoEventInfo> result = new ArrayList<ToDoEventInfo>(); 
		try {
			ps = conn.prepareStatement("SELECT * FROM ToDoEvents WHERE User_ID="+getIDFromUsername(username)+ " ORDER BY End_time ASC");
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				int todoEventID = rs.getInt("ToDoEvent_ID");
				int user_ID = rs.getInt("User_ID");
				String eventname = rs.getString("Event_name");
				String location = rs.getString("location");
				String start = rs.getString("Start_time");
				String end = rs.getString("End_time");
				boolean block = rs.getBoolean("User_block");
				String notes = rs.getString("notes");
				ToDoEventInfo newEventInfo = new ToDoEventInfo(todoEventID, user_ID, eventname, location, start, end, block, notes);
				result.add(newEventInfo);
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		return result;
	}
	
	public void addUserBlock(String username, String website)
	{
		if(!userExists(username))
			return;
		
		try {
			boolean check = false;
			ps = conn.prepareStatement("SELECT COUNT(*) FROM UserBlock WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");
			ps.setString(1, website);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				check = (rs.getInt(1) != 0);
			}
			if(check) 
			{
				return;
			}		
			
			ps = conn.prepareStatement("INSERT INTO UserBlock(User_ID, Website_name) VALUES("+getIDFromUsername(username)+",?)");		
			ps.setString(1, website);	
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public void removeUserBlock(String username, String website)
	{
		if(!userExists(username))
			return;
		try {
			//after ? comes the value user input
			ps = conn.prepareStatement("SELECT COUNT(*) FROM UserBlock WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");		
			//replace first question mark with the firstName variable. question mark means a variable will go there
			ps.setString(1, website);
			rs = ps.executeQuery();			
				
			//as long as there are more rows, as select will return a table
			//the table is a count of how many successful username password combos there are, should be 1 or 0
			boolean matchExists = false;
			while(rs.next())
			{
				matchExists = (rs.getInt(1) == 1);
			}
			if(matchExists)
			{
				ps = conn.prepareStatement("DELETE FROM UserBlock WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");		
				ps.setString(1, website);
				ps.executeUpdate();
			}
		}
			
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public ArrayList<String> getUserBlocks(String username)
	{
		if(userExists(username) == false)
			return null;
		
		ArrayList<String> result = new ArrayList<String>(); 
		try {
			ps = conn.prepareStatement("SELECT * FROM UserBlock WHERE User_ID="+getIDFromUsername(username));
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				result.add(rs.getString("Website_name"));
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		return result;
	}
	
	
	public void addBrowseDaily(String username, String website, int timesvisited)
	{
		if(!userExists(username))
			return;
		
		try {
			boolean check = false;
			ps = conn.prepareStatement("SELECT COUNT(*) FROM Browse_Daily WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");
			ps.setString(1, website);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				check = (rs.getInt(1) != 0);
			}
			if(check) 
			{
				int oldTime = -100;
				ps = conn.prepareStatement("SELECT * FROM Browse_Daily WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");
				ps.setString(1, website);
				rs = ps.executeQuery();		
				while(rs.next())
				{
					oldTime = rs.getInt("times_visited");
				}
				
				if(timesvisited != oldTime)
				{
					//add new
					ps = conn.prepareStatement("INSERT INTO Browse_Daily(User_ID, Website_name, times_visited) VALUES("+getIDFromUsername(username)+",?,"+timesvisited+")");		
					ps.setString(1, website);	
					ps.executeUpdate();
					//delete old
					ps = conn.prepareStatement("DELETE FROM Browse_Daily WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=? AND times_visited="+oldTime);		
					ps.setString(1, website);
					ps.executeUpdate();
				}
				return;
			}		
			
			//add new
			ps = conn.prepareStatement("INSERT INTO Browse_Daily(User_ID, Website_name, times_visited) VALUES("+getIDFromUsername(username)+",?,"+timesvisited+")");		
			ps.setString(1, website);	
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	//returns an arrayList of pairs(Website, times_visited)
	public ArrayList<Pair<String, Integer>> getBrowseDaily(String username)
	{
		if(userExists(username) == false)
			return null;
		
		ArrayList<Pair<String,Integer>> result = new ArrayList<Pair<String,Integer>>(); 
		try {
			ps = conn.prepareStatement("SELECT * FROM Browse_Daily WHERE User_ID="+getIDFromUsername(username));
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				String websitename = rs.getString("Website_name");
				int timesvisited = rs.getInt("times_visited");
				Pair<String, Integer> p = new Pair<String, Integer>(websitename, timesvisited);
				result.add(p);
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		return result;
	}
	
	//for resetting at end of day
	public void clearBrowseDaily()
	{
		try {
			//delete all entries, since user_ID will never be negative
			ps = conn.prepareStatement("DELETE FROM Browse_Daily WHERE User_ID!=100");		
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	public void addBrowsePermanent(String username, String website, int timesvisited)
	{
		if(!userExists(username))
			return;
		
		try {
			boolean check = false;
			ps = conn.prepareStatement("SELECT COUNT(*) FROM Browse_permanent WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");
			ps.setString(1, website);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				check = (rs.getInt(1) != 0);
			}
			if(check) 
			{
				int oldTime = -100;
				ps = conn.prepareStatement("SELECT * FROM Browse_permanent WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=?");
				ps.setString(1, website);
				rs = ps.executeQuery();		
				while(rs.next())
				{
					oldTime = rs.getInt("times_visited");
				}
				if(timesvisited != oldTime)
				{
					//add new
					ps = conn.prepareStatement("INSERT INTO Browse_permanent(User_ID, Website_name, times_visited) VALUES("+getIDFromUsername(username)+",?,"+timesvisited+")");		
					ps.setString(1, website);	
					ps.executeUpdate();
					//delete old
					ps = conn.prepareStatement("DELETE FROM Browse_permanent WHERE User_ID="+getIDFromUsername(username)+" AND Website_name=? AND times_visited="+oldTime);		
					ps.setString(1, website);
					ps.executeUpdate();
				}
				return;
			}		
			
			//add new
			ps = conn.prepareStatement("INSERT INTO Browse_permanent(User_ID, Website_name, times_visited) VALUES("+getIDFromUsername(username)+",?,"+timesvisited+")");		
			ps.setString(1, website);	
			ps.executeUpdate();
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
	}
	
	//returns an arrayList of pairs(Website, times_visited)
	public ArrayList<Pair<String, Integer>> getBrowsePermanent(String username)
	{
		if(userExists(username) == false)
			return null;
		
		ArrayList<Pair<String,Integer>> result = new ArrayList<Pair<String,Integer>>(); 
		try {
			ps = conn.prepareStatement("SELECT * FROM Browse_permanent WHERE User_ID="+getIDFromUsername(username));
			rs = ps.executeQuery();			
			
			while(rs.next())
			{
				String websitename = rs.getString("Website_name");
				int timesvisited = rs.getInt("times_visited");
				Pair<String, Integer> p = new Pair<String, Integer>(websitename, timesvisited);
				result.add(p);
			}
		}
		
		catch(SQLException sqle)
		{
			System.out.println("sqle: " + sqle.getMessage());
		}
		return result;
	}
	
	//destructor of sorts
	protected void finalize() throws Throwable
	{
		try {
			if(rs != null)
				rs.close();
			if(ps != null)
				ps.close();
			if(conn != null)
				conn.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("sqle closing stuff: " + sqle.getMessage());
		}
	}
}
