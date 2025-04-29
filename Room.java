
public class Room {
    private String id;
    private String floor;
    private int capacity;
    private String type;
    private String description;
    private double price;
    private String status;
    

    public Room(String id, String floor, int capacity, String type, String description, double price, String status){
        this.id = id;
        this.floor = floor;
        this.capacity = capacity;
        this.type = type;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFloor(){
        return floor;
    }

    public void setFloor(String floor){
        this.floor = floor;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
