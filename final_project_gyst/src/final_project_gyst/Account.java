package final_project_gyst;

import java.util.ArrayList;

import org.apache.tomcat.jni.Thread;

public class Account extends Thread{
    // TODO: Create a map for accounts in Server class --> should be map<username, account object>

    private long userID;
    private String username;
    private String password;
    private ArrayList<Event> hosted_events;

    public Account(String u, String p){
        username = u; password = p;
        hosted_events = new ArrayList<Event>();
    }
    
    public ArrayList<Event> getEvents(){
    	return hosted_events;
    }

    public void addEvent(Event e){
        hosted_events.add(e);
    }

    public void removeEvent(Event e){
        hosted_events.remove(e);
    }

    public void setUserId(long id){
        userID = id;
    }

    public long getUserId(){
        return userID;
    }

    // TODO: Thread.Run() method implementation 
    public void run(){
        //populate events to the calendar
        Connection conn = null;
		PreparedStatement ps =null;
        ResultSet rs = null;
        
    }
}