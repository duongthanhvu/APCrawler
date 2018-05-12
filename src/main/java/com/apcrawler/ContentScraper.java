/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class ContentScraper {

    String ssid;

    public ContentScraper(String cookie_PHPSESSID) {
        this.ssid = cookie_PHPSESSID;
    }

    public MarkSheet scrapContent(int id) {
        try {
            Thread.sleep(3000); //Mỗi request cách nhau 3 giây
            MarkSheet markSheet = new MarkSheet();
            Document doc = Jsoup.connect("http://ap.poly.edu.vn/group/group_add.php?id=" + id).cookie("PHPSESSID", ssid).get();
            Element hocKy = doc.selectFirst("#term_id").selectFirst("option[selected]");
            if (hocKy != null) {
                markSheet.setHocKy(hocKy.text());
            } else {
                return null;
            }
            Element boMon = doc.selectFirst("#department_id").selectFirst("option[selected]");
            if (boMon != null) {
                markSheet.setBoMon(boMon.text());
            }
            Element khoaHoc = doc.selectFirst("#course_id").selectFirst("option[selected]");
            if (khoaHoc != null) {
                markSheet.setKhoaHoc(khoaHoc.text());
            }
            Element table = doc.selectFirst("table");
            Elements rows = table.select("tr");
            String tenLop = rows.get(3).select("td").get(1).selectFirst("input").attr("value");
            markSheet.setTenLop(tenLop);
            //end of title scrapping
            doc = Jsoup.connect("http://ap.poly.edu.vn/grade_view/grade_tab.php?id=" + id).cookie("PHPSESSID", ssid).get();
            table = doc.selectFirst("table");
            Elements ths = table.selectFirst("thead").select("tr").get(1).select("th");
            String[] tenDauDiem = new String[ths.size()];
            for (int i = 0; i < ths.size(); i++) {
                tenDauDiem[i] = ths.get(i).text();
            }
            markSheet.setDauDiem(tenDauDiem);
            rows = table.select("tr");
            List<MarkSheet.MarkRow> markDetail = new ArrayList<>();
            for (int i = 0; i < rows.size() - 2; i++) {
                Element row = rows.get(i);
                Elements tds = row.select("td");
                if(tds.size() == 0){
                    continue;
                }
                MarkSheet.MarkRow markRow = markSheet.new MarkRow();
                markRow.setMaSV(tds.get(0).text());
                markRow.setTenSV(tds.get(1).text());
                markRow.setTrangThai(tds.get(tds.size() - 1).text());
                markRow.setTongDiem(tds.get(tds.size() - 2).text());
                String[] diems = new String[tds.size() - 4];
                for (int j = 2; j < tds.size() - 2; j++) {
                    diems[j - 2] = tds.get(j).text();
                }
                markRow.setDiem(diems);
                markDetail.add(markRow);
            }
            markSheet.setDanhSachSV(markDetail);
            return markSheet;
        } catch (IOException ex) {
            Logger.getLogger(XLSXWriter.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return null;
        } catch (InterruptedException ex) {
            Logger.getLogger(XLSXWriter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
