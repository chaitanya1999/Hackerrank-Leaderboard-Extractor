/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackerrankextractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.scripts.JO;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Chaitanya V
 */
public class Extractor {
    
    static volatile int progress=0;
    static WebDriver driver = null;

    public static ArrayList<Participant> extract(String leaderboardUrl, int pages,double minScore) throws Exception {
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        progress=0;
        ArrayList<Participant> ret = new ArrayList<>();

        try{
            driver = new ChromeDriver();
            for (int i = 1; i <= pages; i++) {
                String page = leaderboardUrl + "/" + i;
                driver.get(page);
                try{Thread.sleep(3000);}
                catch(InterruptedException ex){}
                ArrayList<WebElement> arr = new ArrayList<>(driver.findElements(By.className("leaderboard-list-view")));
                for (WebElement ele : arr) {
                    Scanner sc = new Scanner(ele.getText());
                    Participant p = new Participant();
                    int rank = Integer.parseInt(sc.nextLine());
                    String username = sc.nextLine();
                    double score = Double.parseDouble(sc.nextLine());
                    String time = sc.nextLine();
                    
                    if(score>minScore){
                        p.setValues(username, score, rank, time);
                        ret.add(p);
                        
                        
                        Connection c = Jsoup.connect("http://www.hackerrank.com/" + p.username);
                        c.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
                        c.followRedirects(true);
                        Document doc = c.get();
                        p.fullname = doc.getElementsByClass("profile-heading").get(0).text();
                        progress += 1;
                    }
                }
                
                System.out.println("Page Accumulation Complete");
            }
        } catch(Exception e){
            throw e;
        } finally{
            driver.quit();
        }

        return ret;
    }

    
    /*for testing*/
    public static void main(String[] args) {
        try {
            ArrayList<Participant> arr = extract("http://www.hackerrank.com/contests/getsetcode-1-0/leaderboard", 4,0.0);
            System.out.println("# " + arr.size());
            for(Participant p:arr){
                System.out.println(p.fullname);
                System.out.println(p.username);
                System.out.println(p.rank);
                System.out.println(p.score);
                System.out.println(p.time);
                System.out.println("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }
}
