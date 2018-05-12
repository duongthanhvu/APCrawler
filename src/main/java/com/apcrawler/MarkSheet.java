/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apcrawler;

import java.util.List;

/**
 *
 * @author vudtpk0074
 */
public class MarkSheet {

    private String hocKy;
    private String boMon;
    private String khoaHoc;
    private String tenLop;
    private List<MarkRow> danhSachSV;
    private String[] dauDiem;

    public MarkSheet() {
    }

    public MarkSheet(String hocKy, String boMon, String khoaHoc, String tenLop, List<MarkRow> marks, String[] dauDiem) {
        this.hocKy = hocKy;
        this.boMon = boMon;
        this.khoaHoc = khoaHoc;
        this.tenLop = tenLop;
        this.danhSachSV = marks;
        this.dauDiem = dauDiem;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public String getBoMon() {
        return boMon;
    }

    public void setBoMon(String boMon) {
        this.boMon = boMon;
    }

    public String getKhoaHoc() {
        return khoaHoc;
    }

    public void setKhoaHoc(String khoaHoc) {
        this.khoaHoc = khoaHoc;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public List<MarkRow> getDanhSachSV() {
        return danhSachSV;
    }

    public void setDanhSachSV(List<MarkRow> danhSachSV) {
        this.danhSachSV = danhSachSV;
    }

    public String[] getDauDiem() {
        return dauDiem;
    }

    public void setDauDiem(String[] dauDiem) {
        this.dauDiem = dauDiem;
    }

    class MarkRow {

        private String maSV;
        private String tenSV;
        private String[] diem;
        private String tongDiem;
        private String trangThai;

        public MarkRow() {
        }

        public MarkRow(String maSV, String tenSV, String[] diem, String tongDiem, String trangThai) {
            this.maSV = maSV;
            this.tenSV = tenSV;
            this.diem = diem;
            this.tongDiem = tongDiem;
            this.trangThai = trangThai;
        }

        public String getMaSV() {
            return maSV;
        }

        public void setMaSV(String maSV) {
            this.maSV = maSV;
        }

        public String getTenSV() {
            return tenSV;
        }

        public void setTenSV(String tenSV) {
            this.tenSV = tenSV;
        }

        public String[] getDiem() {
            return diem;
        }

        public void setDiem(String[] diem) {
            this.diem = diem;
        }

        public String getTongDiem() {
            return tongDiem;
        }

        public void setTongDiem(String tongDiem) {
            this.tongDiem = tongDiem;
        }

        public String getTrangThai() {
            return trangThai;
        }

        public void setTrangThai(String trangThai) {
            this.trangThai = trangThai;
        }

    }
}
