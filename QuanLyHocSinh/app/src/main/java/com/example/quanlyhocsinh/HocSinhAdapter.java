package com.example.quanlyhocsinh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HocSinhAdapter extends BaseAdapter {
    private HocSinhActivity context;
    private int layout;
    private ArrayList<HocSinh> arrayList;

    public HocSinhAdapter(HocSinhActivity context, int layout, ArrayList<HocSinh> arrayList) {
        this.context    = context;
        this.layout     = layout;
        this.arrayList  = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtHoten,txtNgaySinh,txtGioiTinh,txtXepLoai;
        ImageView imgXoa,imgSua;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_hocsinh,null);
            holder = new ViewHolder();
            holder.txtHoten     = (TextView) convertView.findViewById(R.id.txtHoten);
            holder.txtNgaySinh  = (TextView) convertView.findViewById(R.id.txtNgaySinh);
            holder.txtGioiTinh  = (TextView) convertView.findViewById(R.id.txtGioiTinh);
            holder.txtXepLoai   = (TextView) convertView.findViewById(R.id.txtXepLoai);
            holder.imgSua       = (ImageView)convertView.findViewById(R.id.imgSua);
            holder.imgXoa       = (ImageView)convertView.findViewById(R.id.imgXoa);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HocSinh hocSinh = arrayList.get(position);
        String xepLoai  = hocSinh.Xeploai();
        String gioiTinh = hocSinh.GioiTinh();
        String ngaySinh = hocSinh.FormatNgay();
        holder.txtHoten.setText(hocSinh.getHoTen());
        holder.txtNgaySinh.setText(ngaySinh);
        holder.txtGioiTinh.setText("Giới tính: "+gioiTinh);
        holder.txtXepLoai.setText("Xếp loại: "+xepLoai);
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogUpdate(hocSinh);
            }
        });
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(hocSinh.getHoTen(),hocSinh.getId());
            }
        });
        return convertView;
    }
    private void XacNhanXoa(String hoTen, int id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Thông báo !");
        alertDialog.setMessage("Bạn có muốn xoá " +hoTen);
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteHS(id);
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();

    }
}
