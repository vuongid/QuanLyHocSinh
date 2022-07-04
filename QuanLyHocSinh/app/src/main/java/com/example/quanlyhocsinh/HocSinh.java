package com.example.quanlyhocsinh;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HocSinh implements Serializable {
    private int id;
    private int idLop;
    private String hoTen;
    private boolean gioiTinh;
    private String NgaySinh;
    private String diaChi;
    private float toan;
    private float van;
    private float anh;

    @Override
    public String toString() {
        return "HocSinh{" +
                "id=" + id +
                ", idLop=" + idLop +
                ", hoTen='" + hoTen + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", NgaySinh='" + NgaySinh + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", toan=" + toan +
                ", van=" + van +
                ", anh=" + anh +
                '}';
    }

    public String Xeploai(){
        String loai = "";
        float diemTB = (getToan() + getVan() + getAnh())/3;
        if (diemTB < 5){
            loai = "Yếu";
        } else if (diemTB < 6.5){
            loai = "Trung Bình";
        } else if (diemTB < 8){
            loai = "Khá";
        } else {
            loai = "Giỏi";
        }
        return loai;
    }

    public String GioiTinh(){
        String gioiTinh;
        if (isGioiTinh()){
            gioiTinh = "Nữ";
        } else {
            gioiTinh = "Nam";
        }
        return gioiTinh;
    }

    public String FormatNgay(){
        String ngay = getNgaySinh();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(ngay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date day = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ngay = formatter.format(date);
        return ngay;
    }

    public HocSinh(int id, int idLop, String hoTen, boolean gioiTinh, String ngaySinh, String diaChi, float toan, float van, float anh) {
        this.id = id;
        this.idLop = idLop;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        NgaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.toan = toan;
        this.van = van;
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLop() {
        return idLop;
    }

    public void setIdLop(int idLop) {
        this.idLop = idLop;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public float getToan() {
        return toan;
    }

    public void setToan(float toan) {
        this.toan = toan;
    }

    public float getVan() {
        return van;
    }

    public void setVan(float van) {
        this.van = van;
    }

    public float getAnh() {
        return anh;
    }

    public void setAnh(float anh) {
        this.anh = anh;
    }
}
