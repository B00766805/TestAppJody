package jody.ie;

public class PersonalData {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String mobile_phone_no;
PersonalData(int id,String first_name, String last_name, String email, String mobile_phone_no ){
    this.id = id;
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.mobile_phone_no = mobile_phone_no;
}

public int getId(){
    return id;
}
public String getFirstName(){
    return first_name;
}
public String getLastName(){
    return last_name;
}  
public String getEmail(){
    return email;
}  
public String getMobilePhoneNo(){
    return mobile_phone_no;
}
}