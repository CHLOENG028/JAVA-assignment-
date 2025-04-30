public class Staff extends Person {
    private char gender;
    private String icNo;
    private double salary;
    private String role; 

    public Staff() {
        super("","","");
        this.gender = 'U'; 
        this.icNo = "";
        this.salary = 0.0;
        this.role = "Unknown";
    }

    public Staff(String role,String id, String name, String phoneNo, char gender, String icNo, double salary ) {
        super(id,name,phoneNo);
        this.role = role;
        this.gender = gender;
        this.icNo = icNo;
        this.salary = salary;
    }

    public Staff(String id, String name, String phoneNo, char gender, String icNo, double salary ) {
        super(id,name,phoneNo);
        this.gender = gender;
        this.icNo = icNo;
        this.salary = salary;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


}
