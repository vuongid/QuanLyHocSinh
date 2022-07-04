package com.example.quanlyhocsinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HocSinhActivity extends AppCompatActivity {
    String urlGetHS     = "http://192.168.1.180/QLHocSinh/getHS.php";
    String urlInsertHS  = "http://192.168.1.180/QLHocSinh/insertHS.php";
    String urlUpdateHS  = "http://192.168.1.180/QLHocSinh/updateHS.php";
    String urlDeleteHS  = "http://192.168.1.180/QLHocSinh/deleteHS.php";
    ListView lvHocSinh;
    ArrayList<HocSinh> arrayHocSinh;
    HocSinhAdapter adapter;
    int idLop,getIdLop,getGioiTinh;
    ArrayList arraySpinner;
    ArrayAdapter adtLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_sinh);

        Intent intent = getIntent();
        idLop = intent.getIntExtra("idLop",0);
        String tenLop = intent.getStringExtra("tenLop");
        TextView txtTT = (TextView) findViewById(R.id.txtTT);
        txtTT.setText("DANH SÁCH LỚP "+tenLop);
        lvHocSinh = (ListView) findViewById(R.id.lvHocSinh);
        arrayHocSinh = new ArrayList<>();
        GetHS();
        arraySpinner = new ArrayList();
        adtLop = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,arraySpinner);
        for (int i = 0; i < LopActivity.arrayLop.size();i++){
            arraySpinner.add(LopActivity.arrayLop.get(i).getTenLop());
        }
        adapter = new HocSinhAdapter(this,R.layout.row_hocsinh,arrayHocSinh);
        lvHocSinh.setAdapter(adapter);
        lvHocSinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(HocSinhActivity.this,ThongTinHSActivity.class);
                intent1.putExtra("dataHS",arrayHocSinh.get(position));
                startActivity(intent1);
            }
        });

    }

    public void GetHS(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetHS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean gioiTinh;
                        arrayHocSinh.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                gioiTinh = object.getInt("GIOITINH") != 0;
                                arrayHocSinh.add(new HocSinh(
                                        object.getInt("ID"),
                                        object.getInt("IDLOP"),
                                        object.getString("HOTEN"),
                                        gioiTinh,
                                        object.getString("NGAYSINH"),
                                        object.getString("DIACHI"),
                                        Float.valueOf(object.getString("TOAN")),
                                        Float.valueOf(object.getString("VAN")),
                                        Float.valueOf(object.getString("ANH"))
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HocSinhActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parmas = new HashMap<>();
                parmas.put("IDLOP",String.valueOf(idLop));
                return parmas;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void InsertHS(int idLop, String hoTen, int gioiTinh, String ngaySinh, String diaChi, String toan, String van, String anh){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertHS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")){
                            Toast.makeText(HocSinhActivity.this, "Thêm học sinh thành công", Toast.LENGTH_SHORT).show();
                            GetHS();
                        } else {
                            Toast.makeText(HocSinhActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("IdLop",String.valueOf(idLop));
                params.put("HoTen",hoTen);
                params.put("GioiTinh",String.valueOf(getGioiTinh));
                params.put("NgaySinh",ngaySinh);
                params.put("DiaChi",diaChi);
                params.put("Toan",toan);
                params.put("Van",van);
                params.put("Anh",anh);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void UpdateHS(int id, int idLop, String hoTen, int gioiTinh, String ngaySinh, String diaChi, String toan, String van, String anh){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateHS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")){
                            Toast.makeText(HocSinhActivity.this, "Sửa thông tin học sinh thành công", Toast.LENGTH_SHORT).show();
                            GetHS();
                        } else {
                            Toast.makeText(HocSinhActivity.this, "Sửa thông tin học sinh thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HocSinhActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ID",String.valueOf(id));
                params.put("IDLOP",String.valueOf(idLop));
                params.put("HOTEN",hoTen);
                params.put("GIOITINH",String.valueOf(gioiTinh));
                params.put("NGAYSINH",ngaySinh);
                params.put("DIACHI",diaChi);
                params.put("TOAN",toan);
                params.put("VAN",van);
                params.put("ANH",anh);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void DeleteHS(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteHS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")){
                            Toast.makeText(HocSinhActivity.this, "Xoá học sinh thành công", Toast.LENGTH_SHORT).show();
                            GetHS();
                        } else {
                            Toast.makeText(HocSinhActivity.this, "lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HocSinhActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  params = new HashMap<>();
                params.put("ID",String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_hocsinh,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DialogAdd();
        return super.onOptionsItemSelected(item);
    }

    private void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_hs);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        EditText edtHoTen       = (EditText) dialog.findViewById(R.id.edtHoTen);
        EditText edtNgaySinh    = (EditText) dialog.findViewById(R.id.edtNgaySinh);
        EditText edtDiachi      = (EditText) dialog.findViewById(R.id.edtDiaChi);
        EditText edtToan        = (EditText) dialog.findViewById(R.id.edtToan);
        EditText edtVan         = (EditText) dialog.findViewById(R.id.edtVan);
        EditText edtAnh         = (EditText) dialog.findViewById(R.id.edtAnh);
        RadioGroup rdigroupGT   = (RadioGroup) dialog.findViewById(R.id.rdigroupGT);
        Button btnHuy           = (Button) dialog.findViewById(R.id.btnHuy);
        Spinner snLop           = (Spinner) dialog.findViewById(R.id.snLop);
        Button btnThem          = (Button) dialog.findViewById(R.id.btnThemHS);
        snLop.setAdapter(adtLop);
        snLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getIdLop = LopActivity.arrayLop.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay    = calendar.get(Calendar.DATE);
                int thang   = calendar.get(Calendar.MONTH);
                int nam     = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(dialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam,thang,ngay);
                pickerDialog.show();
            }
        });
        rdigroupGT.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioNam:
                        getGioiTinh = 0;
                        break;
                    case R.id.radioNu:
                        getGioiTinh = 1;
                        break;
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen    = edtHoTen.getText().toString().trim();
                String ngaySinh = edtNgaySinh.getText().toString().trim();
                String diaChi   = edtDiachi.getText().toString().trim();
                String toan     = edtToan.getText().toString().trim();
                String van      = edtVan.getText().toString().trim();
                String anh      = edtAnh.getText().toString().trim();

                SimpleDateFormat ngayParse = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = ngayParse.parse(ngaySinh);
                    SimpleDateFormat ngayFormat = new SimpleDateFormat("yyyy/MM/dd");
                    ngaySinh = ngayFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(hoTen.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || toan.isEmpty() || van.isEmpty() || anh.isEmpty()){
                    Toast.makeText(HocSinhActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((Float.valueOf(toan) < 0 || Float.valueOf(toan) > 10) || (Float.valueOf(van) < 0 || Float.valueOf(van) > 10) || (Float.valueOf(anh) < 0 && Float.valueOf(anh) > 10) ){
                    Toast.makeText(HocSinhActivity.this, "Nhập điểm từ 0 -> 10", Toast.LENGTH_SHORT).show();
                } else {
                    InsertHS(getIdLop,hoTen,getGioiTinh,ngaySinh,diaChi,toan,van,anh);
                    dialog.cancel();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public void DialogUpdate(HocSinh hocSinh){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_hs);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        EditText edtHoTen       = (EditText) dialog.findViewById(R.id.edtHoTen);
        EditText edtNgaySinh    = (EditText) dialog.findViewById(R.id.edtNgaySinh);
        EditText edtDiachi      = (EditText) dialog.findViewById(R.id.edtDiaChi);
        EditText edtToan        = (EditText) dialog.findViewById(R.id.edtToan);
        EditText edtVan         = (EditText) dialog.findViewById(R.id.edtVan);
        EditText edtAnh         = (EditText) dialog.findViewById(R.id.edtAnh);
        RadioGroup rdigroupGT   = (RadioGroup) dialog.findViewById(R.id.rdigroupGT);
        Button btnHuy           = (Button) dialog.findViewById(R.id.btnHuy);
        Spinner snLop           = (Spinner) dialog.findViewById(R.id.snLop);
        RadioButton rdiNam      = (RadioButton) dialog.findViewById(R.id.radioNam);
        RadioButton rdiNu       = (RadioButton) dialog.findViewById(R.id.radioNu);
        Button btnSua           = (Button) dialog.findViewById(R.id.btnSuaHS);
        snLop.setAdapter(adtLop);
        edtHoTen.setText(hocSinh.getHoTen());
        edtNgaySinh.setText(hocSinh.FormatNgay());
        edtDiachi.setText(hocSinh.getDiaChi());
        edtToan.setText(String.valueOf(hocSinh.getToan()));
        edtVan.setText(String.valueOf(hocSinh.getVan()));
        edtAnh.setText(String.valueOf(hocSinh.getAnh()));

        if (hocSinh.isGioiTinh()){
            rdiNu.setChecked(true);
        } else {
            rdiNam.setChecked(true);
        }

        // truyền tên lớp
        int getPosition = -1;
        for (int i = 0; i < LopActivity.arrayLop.size(); i++){
            if (LopActivity.arrayLop.get(i).getId() == hocSinh.getIdLop()){
                getPosition = i;
            }
        }
        snLop.setSelection(getPosition);

        snLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getIdLop = LopActivity.arrayLop.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay    = calendar.get(Calendar.DATE);
                int thang   = calendar.get(Calendar.MONTH);
                int nam     = calendar.get(Calendar.YEAR);
                DatePickerDialog pickerDialog = new DatePickerDialog(dialog.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam,thang,ngay);
                pickerDialog.show();
            }
        });
        rdigroupGT.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioNam:
                        getGioiTinh = 0;
                        break;
                    case R.id.radioNu:
                        getGioiTinh = 1;
                        break;
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen    = edtHoTen.getText().toString().trim();
                String ngaySinh = edtNgaySinh.getText().toString().trim();
                String diaChi   = edtDiachi.getText().toString().trim();
                String toan     = edtToan.getText().toString().trim();
                String van      = edtVan.getText().toString().trim();
                String anh      = edtAnh.getText().toString().trim();

                SimpleDateFormat ngayParse = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = ngayParse.parse(ngaySinh);
                    SimpleDateFormat ngayFormat = new SimpleDateFormat("yyyy/MM/dd");
                    ngaySinh = ngayFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(hoTen.isEmpty() || ngaySinh.isEmpty() || diaChi.isEmpty() || toan.isEmpty() || van.isEmpty() || anh.isEmpty()){
                    Toast.makeText(HocSinhActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((Float.valueOf(toan) < 0 || Float.valueOf(toan) > 10) || (Float.valueOf(van) < 0 || Float.valueOf(van) > 10) || (Float.valueOf(anh) < 0 && Float.valueOf(anh) > 10) ){
                    Toast.makeText(HocSinhActivity.this, "Nhập điểm từ 0 -> 10", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateHS(hocSinh.getId(),getIdLop,hoTen,getGioiTinh,ngaySinh,diaChi,toan,van,anh);
                    dialog.cancel();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}