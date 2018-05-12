/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apcrawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author vudtpk0074
 */
public class XLSXWriter {

    private Workbook wb;

    public XLSXWriter() {
        wb = new XSSFWorkbook();
    }

//    public static boolean isNumeric(String str) {
//        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
//    }
    public void addSheet(MarkSheet markSheet) {
        //Excel giới hạn tên worksheet dưới 31 ký tự
        String sheetName = markSheet.getKhoaHoc().split(" ")[0] + " " + markSheet.getTenLop();
        Sheet sheet = wb.createSheet(sheetName);
        int numOfTitle = 6; //offset
        sheet.createRow(0).createCell(1).setCellValue("Học kỳ: " + markSheet.getHocKy());
        sheet.createRow(1).createCell(1).setCellValue("Bộ môn: " + markSheet.getBoMon());
        sheet.createRow(2).createCell(1).setCellValue("Khóa học: " + markSheet.getKhoaHoc());
        sheet.createRow(3).createCell(1).setCellValue("Lớp: " + markSheet.getTenLop());
        Row tableHeader = sheet.createRow(5);
        tableHeader.createCell(0).setCellValue("Mã SV");
        tableHeader.createCell(1).setCellValue("Họ tên");
        for (int i = 0; i < markSheet.getDauDiem().length; i++) {
            Cell cell = tableHeader.createCell(i+2);
            cell.setCellValue(markSheet.getDauDiem()[i]);
        }
        tableHeader.createCell(markSheet.getDauDiem().length + 2).setCellValue("Tổng");
        tableHeader.createCell(markSheet.getDauDiem().length + 3).setCellValue("Trạng thái");
        for (int i = numOfTitle; i < markSheet.getDanhSachSV().size() + numOfTitle; i++) {
            Row row = sheet.createRow(i);
            row.createCell(0).setCellValue(markSheet.getDanhSachSV().get(i - numOfTitle).getMaSV());
            row.createCell(1).setCellValue(markSheet.getDanhSachSV().get(i - numOfTitle).getTenSV());
            for (int j = 2; j < markSheet.getDanhSachSV().get(0).getDiem().length + 2; j++) {
            	String diemchu = markSheet.getDanhSachSV().get(i - numOfTitle).getDiem()[j - 2];
            	double diem;
            	if(diemchu.equals("")) {
            		diem = 0;
            	}else{
            		diem = Double.parseDouble(diemchu);
            	}
                row.createCell(j).setCellValue(diem);
            }
            row.createCell(markSheet.getDanhSachSV().get(0).getDiem().length + 2)
                    .setCellValue(Double.parseDouble(markSheet.getDanhSachSV().get(i - numOfTitle).getTongDiem()));
            row.createCell(markSheet.getDanhSachSV().get(0).getDiem().length + 3)
                    .setCellValue(markSheet.getDanhSachSV().get(i - numOfTitle).getTrangThai());
        }
    }

    public void exportXLSX() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("workbook.xlsx");
            wb.write(out);
            System.out.println("Write to xlsx successful!");
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
