package CustomerManagement;

public class Staff{
    private String id;
    private String name;
    private String phoneNo;
    private char gender;
    private String icNo;
    private double salary;
    
    public Staff(String id, String name, String phoneNo, char gender, String icNo, double salary){
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.icNo = icNo;
        this.salary = salary;
    }

    public Staff() {
        this.id = "";
        this.name = "";
        this.phoneNo = "";
        this.gender = ' ';
        this.icNo = "";
        this.salary = 0.0;
    }

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

    public char getGender(){
        return gender;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

    public String getIcNo(){
        return icNo;
    }

    public void setIcNo(String icNo){
        this.icNo = icNo;
    }


    public double getSalary(){
        return salary;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }
}
