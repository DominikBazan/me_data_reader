package agh.dfbazan.communication;

import agh.dfbazan.crypto.SymetricCoder;


public class UserInfo {
    
    private String userName;
    private String password;

    public UserInfo(){}
    
    public UserInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserInfo encrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        UserInfo info = new UserInfo();
        
        info.setUserName(coder.encrypt(userName));
        info.setPassword(coder.encrypt(password));
        
        return info;
    }
    
    public UserInfo decrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        UserInfo info = new UserInfo();
        
        info.setUserName(coder.decrypt(userName));
        info.setPassword(coder.decrypt(password));
        
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

      
    
}


