/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apcrawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author vudtpk0074
 */
public class XLSXWriter {

    Workbook wb = new XSSFWorkbook();
    int begin, end;
    String ssid;

    public XLSXWriter(int begin, int end, String ssid) {
        this.ssid = ssid;
        this.begin = begin;
        this.end = end;
    }

    public void createSheet(String sheetName, List<ArrayList<String>> cell) {
        Sheet sheet = wb.createSheet(sheetName);
        for (int i = 0; i < cell.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < cell.get(i).size(); j++) {
                row.createCell(j).setCellValue(cell.get(i).get(j));
            }
        }
    }

    public List<ArrayList<String>> scrapContent(int id) {
        try {
            Thread.sleep(3000); //Mỗi request cách nhau 3 giây
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            Document doc = Jsoup.connect("http://ap.poly.edu.vn/grade_view/grade_tab.php?id=" + id).cookie("PHPSESSID", ssid).get();
            Element table = doc.selectFirst("table");
            Elements rows = table.select("tr");
            for (int i = 0; i < rows.size() - 2; i++) {
                ArrayList<String> rowData = new ArrayList<>();
                Element row = rows.get(i);
                Elements tds = row.select("td");
                for (Element cell : tds) {
                    rowData.add(cell.text());
                }
                if (rowData.size() > 0) {
                    data.add(rowData);
                }
            }
            if (data.size() > 0) {
                return data;
            }
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return null;
        } catch (InterruptedException ex) {
            Logger.getLogger(XLSXWriter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getSubjectName(int id) {
        try {
            Document doc = Jsoup.connect("http://ap.poly.edu.vn/group/group_add.php?id=" + id).cookie("PHPSESSID", ssid).get();
            String value = "";
            Element table = doc.selectFirst("table");
            Elements rows = table.select("tr");
            for (int i = 0; i < rows.size() - 2; i++) {
                Element row = rows.get(i);
                Elements tds = row.select("td");
                if (tds.size() != 0) {
                    value += tds.get(0).text() + ",";
                    value += tds.get(1).text() + ",";
                    value += tds.get(tds.size() - 2).text();
                    value += "\n";
                }
            }
            if (value.equals("")) {
                return null;
            }
            return value;
        } catch (IOException ex) {
            Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void exportXLSX() {
        for (int i = begin; i < end + 1; i++) {
            List<ArrayList<String>> content = scrapContent(i);
            String sheetName = String.valueOf(i);
            if (content != null) {
                createSheet(sheetName, content);
                System.out.println(i + "done!");
            } else {
                System.out.println("skiped " + i + "!");
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("workbook.xlsx");
            wb.write(out);
            System.out.println("Write successful!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XLSXWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XLSXWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(XLSXWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
