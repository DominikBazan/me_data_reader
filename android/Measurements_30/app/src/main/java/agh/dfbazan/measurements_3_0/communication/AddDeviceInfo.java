package agh.dfbazan.measurements_3_0.communication;

import agh.dfbazan.measurements_3_0.crypto.SymetricCoder;


public class AddDeviceInfo {

    private String userName;
    private String password;
    private String deviceName;
    private String deviceUnit;

    public AddDeviceInfo() {
    }

    public AddDeviceInfo(String userName, String password, String deviceName, String deviceUnit) {
        this.userName = userName;
        this.password = password;
        this.deviceName = deviceName;
        this.deviceUnit = deviceUnit;
    }


    public AddDeviceInfo encrypt() {
        SymetricCoder coder = new SymetricCoder();

        AddDeviceInfo info = new AddDeviceInfo();

        info.setUserName(coder.encrypt(userName));
        info.setPassword(coder.encrypt(password));
        info.setDeviceName(coder.encrypt(deviceName));
        info.setDeviceUnit(coder.encrypt(deviceUnit));

        return info;
    }

    public AddDeviceInfo decrypt() {
        SymetricCoder coder = new SymetricCoder();

        AddDeviceInfo info = new AddDeviceInfo();

        info.setUserName(coder.decrypt(userName));
        info.setPassword(coder.decrypt(password));
        info.setDeviceName(coder.decrypt(deviceName));
        info.setDeviceUnit(coder.decrypt(deviceUnit));

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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUnit() {
        return deviceUnit;
    }

    public void setDeviceUnit(String deviceUnit) {
        this.deviceUnit = deviceUnit;
    }


}

