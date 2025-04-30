public class Person {
    private String id;
    private String name;
    private String phoneNo;

    public Person(String id, String name, String phoneNo){
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public Person(){}
    
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNo(){
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }
}
