package Utils;


public class ModelPerson {
    private String fname,lname,dob;
    private int id;

    public ModelPerson(String fname, String lname, String dob, int id) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.id = id;
    }

    public ModelPerson() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
