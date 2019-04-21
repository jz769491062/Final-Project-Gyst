package final_project_gyst;

import java.util.ArrayList;

public class Account extends Thread{
    // TODO: Create a map for accounts in Server class --> should be map<username, account object>
    //FIXME: Zihan: I dont think the Account needs to be a thread any more. We use thread only in chatroom

    private long userID;
    private String username;
    private String password;
    private ArrayList<Event> hosted_events;
    private ArrayList<ToDoEvent> todo_events;

    public Account(String u, String p){
        username = u; password = p;
        hosted_events = new ArrayList<Event>();
        todo_events = new ArrayList<ToDoEvent>();
    }
    
    public ArrayList<Event> getHostedEvents(){
    	return hosted_events;
    }

    public void addHostedEvent(Event e){
        hosted_events.add(e);
    }

    public void removeHostedEvent(Event e){
        hosted_events.remove(e);
    }

    public ArrayList<ToDoEvent> getToDoEvents(){
    	return todo_events;
    }

    public void addToDoEvent(ToDoEvent e){
    	todo_events.add(e);
    }

    public void removeToDoEvent(ToDoEvent e){
    	todo_events.remove(e);
    }
    
    public void setUserId(long id){
        userID = id;
    }

    public long getUserId(){
        return userID;
    }

    public String getUsername(){
        return username;
    }

    // TODO: Thread.Run() method implementation
    
}