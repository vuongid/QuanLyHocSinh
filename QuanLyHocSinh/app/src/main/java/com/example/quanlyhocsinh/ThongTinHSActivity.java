package com.example.quanlyhocsinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ThongTinHSActivity extends AppCompatActivity {
    String tenLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_hsactivity);

        Intent intent = getIntent();
        HocSinh hocSinh         = (HocSinh) intent.getSerializableExtra("dataHS");
        TextView txthoTen       = (TextView) findViewById(R.id.txtHoten);
        TextView txtngaySinh    = (TextView) findViewById(R.id.txtNgaySinh);
        TextView txtLop         = (TextView) findViewById(R.id.txtTenLop);
        TextView txtGioiTinh    = (TextView) findViewById(R.id.txtGioiTinh);
        TextView txtDiaChi      = (TextView) findViewById(R.id.txtDiaChi);
        TextView txtToan        = (TextView) findViewById(R.id.txtToan);
        TextView txtVan         = (TextView) findViewById(R.id.txtVan);
        TextView txtAnh         = (TextView) findViewById(R.id.txtAnh);
        TextView txtXepLoai     = (TextView) findViewById(R.id.txtXepLoai);
        txthoTen.setText("Họ tên: "+hocSinh.getHoTen());
        txtngaySinh.setText("Ngày sinh: "+hocSinh.FormatNgay());
        for (int i = 0; i < LopActivity.arrayLop.size(); i++){
            if (LopActivity.arrayLop.get(i).getId() == hocSinh.getIdLop()){
                tenLop = LopActivity.arrayLop.get(i).getTenLop();
            }
        }
        txtLop.setText("Lớp: "+tenLop);
        txtGioiTinh.setText("Giới tính:" +hocSinh.GioiTinh());
        txtDiaChi.setText("Địa chỉ: "+hocSinh.getDiaChi());
        txtToan.setText("Toán: "+hocSinh.getToan());
        txtVan.setText("Văn: "+hocSinh.getVan());
        txtAnh.setText("Anh: "+hocSinh.getAnh());
        txtXepLoai.setText("Xếp loại: "+hocSinh.Xeploai());
    }
}