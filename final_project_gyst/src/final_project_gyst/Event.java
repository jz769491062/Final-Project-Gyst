package final_project_gyst;

public class Event {

    // CAN BE EDITED AND CHANGED PER EVENT

    // event identification
	
	// TODO: this needs to be UNIQUE FOR ALL EVENTS --> ACROSS ALL USERS (maybe use a hashset & while loop?)
	private long id; 
	
    private String eventName;

    // times
    private String start;
    private String end;
    private boolean allDay;

    // date --> might not be necessary, can represent date as ISO-8601 String
    // private int month;
    // private int day;
    // private int year;

    // misc
    private String notes;
    private String location;
    
    // private Notification notification; --> FIXME: this may not be necessary
    
    void sendNotification(){
        // needs implementation
    }

    // constructor with all variables already parsed
    public Event(long id, String name, String start, String end, String note, String loc, boolean all){
        this.id = id; eventName = name; this.start = start; this.end = end;
        notes = note; location = loc; allDay = all;
    }

    public void setEventName(String e){ 
        eventName = e;
    }
    
    public long getId(){
        return id;
    }

    public boolean getAllDay(){
        return allDay;
    }

    public void setId(int i){
        id = i;
    }
    public String getEventName(){
        return eventName;
    }

    public void setStart(String s){
        start = s;
    }

    public String getStart(){
        return start;
    }

    public void setEnd(String e){
        end = e;
    }

    public String getEnd(){
        return end;
    }

    // public void setMonth(int m){
    //     month = m;
    // }

    // public int getMonth(){
    //     return month;
    // }

    // public void setDay(int d){
    //     day = d;
    // }

    // public int getDay(){
    //     return day;
    // }

    // public void setYear(int y){
    //     year = y;
    // }
    
    // public int getYear(){
    //     return year;
    // }

    public void setNotes(String n){
        notes = n;
    }

    public String getNotes(){
        return notes;
    }

    public void setLocation(String l){
        location = l;
    }

    public String getLocation(){
        return location;
    }
}
