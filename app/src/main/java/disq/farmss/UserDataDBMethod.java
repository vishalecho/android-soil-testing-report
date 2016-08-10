package disq.farmss;

/**
 * Created by vishal on 20/7/16.
 */
public class UserDataDBMethod {
    String mobile;
    String name,pass;

    public void setMobile(String mobile){
        this.mobile= mobile;
    }
    public String getMobile(){
        return this.mobile;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setPass(String pass) { this.pass = pass; }
    public String getPass(){
        return this.pass;
    }
}
