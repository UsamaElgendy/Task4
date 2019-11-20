package model;

public class Register {
    private int id;
    private String studentName;
    private String email;
    private String password ;
    private String phone ;
    private String facultyName ;
    private String departmentName ;

    public Register(int id, String studentName, String email, String password, String phone, String facultyName, String departmentName) {
        this.id = id;
        this.studentName = studentName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.facultyName = facultyName;
        this.departmentName = departmentName;
    }

    public Register() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
