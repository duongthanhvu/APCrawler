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

    public static String getMark(int id, String ssid) {
        try {
            Document doc = Jsoup.connect("http://ap.poly.edu.vn/grade_view/grade_tab.php?id=" + id).cookie("PHPSESSID", ssid).get();
            String value = "";
            Element table = doc.selectFirst("table");
            Elements rows = table.select("tr");
            for (int i = 0; i < rows.size() - 2; i++) {
                Element row = rows.get(i);
                Elements tds = row.select("td");
                if (tds.size() != 0) {
                    value += tds.get(0).text() + ",";
                    value += tds.get(1).text() + ",";
                    value += tds.get(tds.size()-2).text();
                    value += "\n";
                }
            }
            if(value.equals("")){
                return null;
            }
            return value;
        } catch (IOException ex) {
            Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
