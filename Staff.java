public class Staff extends Person{
    private double salary;
    
    public Staff(String id, String name, String phoneNo, char gender, String icNo, double salary){
        super(id,name,phoneNo,gender,icNo);
        this.salary = salary;
    }

    public Staff(){}

    public double getSalary(){
        return salary;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }
}
