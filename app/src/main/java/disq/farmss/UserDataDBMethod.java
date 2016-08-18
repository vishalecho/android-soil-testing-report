package disq.farmss;

/**
 * Created by vishal on 20/7/16.
 */
public class UserDataDBMethod {
    String mobile;
    String name,pass;
    String district,tehsil,village,pincode;

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

    /*public void setState(String state){
        this.setState(state);
    }
    public String getState(){
        return this.getState();
    }*/

    public void setDistrict(String district){
        this.district = district;
    }
    public String getDistrict(){
        return this.district;
    }

    public void setTehsil(String tehsil){
        this.tehsil= tehsil;
    }
    public String getTehsil(){
        return this.tehsil;
    }

    public void setVillage(String village){
        this.village=village;
    }
    public String getVillage(){
        return this.village;
    }

    public void setPincode(String pincode){
        this.pincode=pincode;
    }
    public String getPincode(){
        return this.pincode;
    }
}
