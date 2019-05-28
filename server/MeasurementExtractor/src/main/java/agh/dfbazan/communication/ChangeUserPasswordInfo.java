package agh.dfbazan.communication;

import agh.dfbazan.crypto.SymetricCoder;


public class ChangeUserPasswordInfo {
    
    private String userName;
    private String password;
    private String newPassword;

    public ChangeUserPasswordInfo(){}
    
    public ChangeUserPasswordInfo(String userName, String password, String newPassword) {
        this.userName = userName;
        this.password = password;
        this.newPassword = newPassword;
    }
    
    public ChangeUserPasswordInfo encrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        ChangeUserPasswordInfo info = new ChangeUserPasswordInfo();
        
        info.setUserName(coder.encrypt(userName));
        info.setPassword(coder.encrypt(password));
        info.setNewPassword(coder.encrypt(newPassword));
        
        return info;
    }
    
    public ChangeUserPasswordInfo decrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        ChangeUserPasswordInfo info = new ChangeUserPasswordInfo();
        
        info.setUserName(coder.decrypt(userName));
        info.setPassword(coder.decrypt(password));
        info.setNewPassword(coder.decrypt(newPassword));
        
        return info;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    
      
    
}


