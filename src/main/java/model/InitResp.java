package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A response to the initialisation of the database
 */
@XmlRootElement
public class InitResp {
    String result;

    public InitResp(){}

    public InitResp(String res){
        this.result = res;
    }

    public String getResult(){
        return this.result;
    }

    public void setResult(String res){
        this.result = res;
    }

}
