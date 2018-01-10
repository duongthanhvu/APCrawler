/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apcrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vudtpk0074
 */
public class CSVWriter {
    public static boolean writeToCSV(int id, String ssid){
        BufferedWriter writer = null;
        String mark = Mark.getMark(id,ssid);
        if(mark == null){
            return false;
        }
        try {
            writer = new BufferedWriter(new FileWriter(String.valueOf(id)+".csv"));
            writer.write(mark);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(APCrawler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(APCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
