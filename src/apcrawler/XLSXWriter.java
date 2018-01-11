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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public void createSheet(String sheetName, String subject, List<ArrayList<String>> cell) {
        Sheet sheet = wb.createSheet(sheetName);
        Row head = sheet.createRow(0);
        head.createCell(0).setCellValue(subject);
        for (int i = 2; i < cell.size() + 2; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < cell.get(i - 2).size(); j++) {
                Cell xcell = row.createCell(j);
                String data = cell.get(i - 2).get(j);
                if (isNumeric(data)) {
                    double so = Double.parseDouble(data);
                    xcell.setCellValue(so);
                }else{
                    xcell.setCellValue(data);
                }
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
            Element department_id = doc.selectFirst("#department_id").selectFirst("option[selected]");
            String boMon = null;
            if (department_id != null) {
                boMon = department_id.text();
            }
            Element course_id = doc.selectFirst("#course_id").selectFirst("option[selected]");
            String khoaHoc = null;
            if (course_id != null) {
                khoaHoc = course_id.text();
            }
            String tenLop = rows.get(3).select("td").get(1).selectFirst("input").attr("value");
            return "Bộ môn: " + boMon + " - Khóa học: " + khoaHoc + " - Tên lớp: " + tenLop;
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
                String subject = getSubjectName(i);
                createSheet(sheetName, subject, content);
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
