package agh.dfbazan.measurements_3_0.communication;

import agh.dfbazan.measurements_3_0.crypto.SymetricCoder;

public class StringInfo {

    private String message;

    public StringInfo() {
    }

    public StringInfo(String message) {
        this.message = message;
    }


    public StringInfo encrypt() {
        SymetricCoder coder = new SymetricCoder();

        StringInfo info = new StringInfo();

        info.setMessage(coder.encrypt(message));

        return info;
    }

    public StringInfo decrypt() {
        SymetricCoder coder = new SymetricCoder();

        StringInfo info = new StringInfo();

        info.setMessage(coder.decrypt(message));

        return info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}