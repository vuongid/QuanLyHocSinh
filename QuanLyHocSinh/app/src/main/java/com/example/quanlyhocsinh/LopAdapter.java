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

import java.util.ArrayList;

public class LopAdapter extends BaseAdapter {
    private LopActivity context;
    private int layout;
    private ArrayList<Lop> arrayList;

    public LopAdapter(LopActivity context, int layout, ArrayList<Lop> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
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
        TextView txtTenLop;
        ImageView imgSua,imgXoa;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder = new ViewHolder();
            holder.txtTenLop = (TextView) convertView.findViewById(R.id.txtTenLop);
            holder.imgSua = (ImageView) convertView.findViewById(R.id.imgSua);
            holder.imgXoa = (ImageView) convertView.findViewById(R.id.imgXoa);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Lop lop = arrayList.get(position);
        holder.txtTenLop.setText("Lớp: "+lop.getTenLop());
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogUpdate(lop.getId(),lop.getTenLop());
            }
        });
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(lop.getTenLop(),lop.getId());
            }
        });
        return convertView;
    }

    private void XacNhanXoa(String tenLop, int id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Thông báo !");
        alertDialog.setMessage("Bạn có muốn xoá" +tenLop);
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteLop(id);
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
