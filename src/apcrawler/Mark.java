/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apcrawler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author vudtpk0074
 */
public class Mark {
    public static void getMark(int id){
        try {
            Document doc = Jsoup.connect("http://ap.poly.edu.vn/grade_view/grade_tab.php?id="+id).cookie("PHPSESSID", "dpiqkfivv7vjgf0nenusg1ss87").get();
            String value = "";
            Element table = doc.selectFirst("table");
            for(Element row : table.select("tr")){
                Elements tds = row.select("td");
                for(int i = 0; i < tds.size(); i++){
                    value += tds.get(i).text() + "|";
                }
                value += "\n";
            }
            System.out.println(value);
        } catch (IOException ex) {
            Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
}
