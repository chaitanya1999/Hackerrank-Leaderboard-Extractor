/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrankextractor;

/**
 *
 * @author Chaitanya V
 */
public class Participant {

    String username;
    String fullname;    //////////
    double score;
    int rank;
    String time;
    public Participant(){}
    
    public Participant(String uname, double sc,int rk,String tm) {
        setValues(uname,sc,rk,tm);
    }
    public void setValues(String uname, double sc,int rk,String tm) {
        username = uname;
        score = sc;
        rank=rk;
        time=tm;
    }
}
