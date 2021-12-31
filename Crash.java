import java.time.LocalDate;
import java.time.LocalTime;
public class Crash {
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String operator;
    private String flight;
    private String route;
    private String type;
    private String registration;
    private String cn_In;
    private int aboard;
    private int fatalities;
    private int ground;
    private int survivors;
    private double survivalRate;
    private String summary;
    private String clustID;
    
    public Crash(LocalDate date, LocalTime time, String location, String operator, String flight, String route, String type,
            String registration, String cn_In, int aboard, int fatalities, int ground, int survivors,
            double survivalRate, String summary, String clustID) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.operator = operator;
        this.flight = flight;
        this.route = route;
        this.type = type;
        this.registration = registration;
        this.cn_In = cn_In;
        this.aboard = aboard;
        this.fatalities = fatalities;
        this.ground = ground;
        this.survivors = survivors;
        this.survivalRate = survivalRate;
        this.summary = summary;
        this.clustID = clustID;
    }
    
    public LocalDate getLocalDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getLocation() {
        return location;
    }
    public String getOperator() {
        return operator;
    }
    public String getFlight() {
        return flight;
    }
    public String getRoute() {
        return route;
    }
    public String getType() {
        return type;
    }
    public String getRegistration() {
        return registration;
    }
    public String getCn_In() {
        return cn_In;
    }
    public int getAboard() {
        return aboard;
    }
    public int getFatalities() {
        return fatalities;
    }
    public int getGround() {
        return ground;
    }
    public int getSurvivors() {
        return survivors;
    }
    public double getSurvivalRate() {
        return survivalRate;
    }
    public String getSummary() {
        return summary;
    }
    public String getClustID() {
        return clustID;
    }

    public String fieldValueAsString(String fieldName){
        switch(fieldName){
            case "date":
                return String.valueOf(this.date);
        
            case "time":
                return String.valueOf(this.time);
            
            case "location":
                return this.location;
        
            case "operator":
                return this.operator;
        
            case "flight":
                return this.flight;
        
            case "route":
                return this.route;
        
            case "type":
                return this.type;
            case "registration":
                return this.registration;
        
            case "cn_In":
                return this.cn_In;
        
            case "aboard":
                return String.valueOf(this.aboard);
        
            case "fatalities":
                return String.valueOf(this.fatalities);
            
            case "ground": 
                return String.valueOf(this.ground);
        
            case "survivors":
                return String.valueOf(this.survivors);
        
            case "survivalRate":
                return String.valueOf(this.survivalRate);
        
            case "summary":
                return String.valueOf(this.summary);
        
            case "clustId":
                return this.clustID;

            default:
                return " ";
        }
    }

    @Override
    public String toString() {
        return "[date: " + date+ ", time: " + time + ", location: " + location + ", operator: " + operator + ", flight: " + flight + ", route: " + route
        + ", type: " + type + ", registration: " + registration + ", cn_In: " + cn_In + ", aboard: " + aboard + ", fatalities: " + fatalities
        + ", ground: " + ground + ", survivors: " + survivors + ", survivalRate: " + survivalRate + ", summary: " + summary + ", clustID: " + clustID + "]";
    }
    
}
