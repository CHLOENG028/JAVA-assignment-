public class Person {
    private String id;
    private String name;
    private String phoneNo;
    private char gender;
    private String icNo;

    public Person(String id, String name, String phoneNo, char gender, String icNo){
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.icNo = icNo;
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

    public String getIcNo(){
        return icNo;
    }

    public void setIcNo(String icNo){
        this.icNo = icNo;
    }

    public Character getGender(){
        return gender;
    }

    public void setGender(Character gender){
        this.gender = gender;
    }
}
