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
public class APCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
//        for (int i = 6940; i <= 7061; i++) {
//            boolean check = CSVWriter.writeToCSV(i, "dpiqkfivv7vjgf0nenusg1ss87");
//            if(check){
//                System.out.println(i + " done!");
//            }else{
//                System.out.println(i + " fail!");
//            }
//            Thread.sleep(3000);
//        }
        XLSXWriter writer = new XLSXWriter(7051, 7061, "k8u89rq45ooc5u0kd35vlvd4k6");
        writer.exportXLSX();
        System.out.println("All done!");
    }
}
