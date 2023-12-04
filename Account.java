import java.io.Serializable;
//Class for Accounts
class Account implements Serializable {
    private String username;
    private String password;
    private String emailid;
    public Account(String username, String password, String emailid) {//COnstructor initialised using username, password and email id
        this.username = username;
        this.password = password;
        this.emailid = emailid;
    }
//Getters for username, password and email ID:
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail(){
       return emailid;
    }
}

