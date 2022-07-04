package com.example.quanlyhocsinh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LopActivity extends AppCompatActivity {
    String urlGetLop    = "http://192.168.1.180/QLHocSinh/getLop.php";
    String urlInsertLop = "http://192.168.1.180/QLHocSinh/insertLop.php";
    String urlUpdateLop = "http://192.168.1.180/QLHocSinh/updateLop.php";
    String urlDeleteLop = "http://192.168.1.180/QLHocSinh/deleteLop.php";

    ListView lvLop;
    static ArrayList<Lop> arrayLop;
    LopAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop);

        lvLop = (ListView) findViewById(R.id.lvLop);
        arrayLop = new ArrayList<>();
        GetLop();
        adapter = new LopAdapter(this,R.layout.row_lop,arrayLop);
        lvLop.setAdapter(adapter);
        lvLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LopActivity.this,HocSinhActivity.class);
                intent.putExtra("idLop",arrayLop.get(position).getId());
                intent.putExtra("tenLop",arrayLop.get(position).getTenLop());
                startActivity(intent);
            }
        });
    }
    public  void GetLop(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetLop, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayLop.clear();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayLop.add(new Lop(
                                        object.getInt("ID"),
                                        object.getString("TENLOP")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AAAA", error.toString());
                        Toast.makeText(LopActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(arrayRequest);
    }
    public void InsertLop(String tenLop){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertLop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")){
                            Toast.makeText(LopActivity.this, "Thêm lớp thành công", Toast.LENGTH_SHORT).show();
                            GetLop();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LopActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("TenLop",tenLop);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void UpdateLop(int id, String tenLop){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateLop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")){
                            Toast.makeText(LopActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            GetLop();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LopActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Id",String.valueOf(id));
                params.put("TenLop",tenLop);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void DeleteLop(int id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDeleteLop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("success")){
                            Toast.makeText(LopActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                            GetLop();
                        } else{
                            Toast.makeText(LopActivity.this, "Lớp còn học sinh, không thể xoá được !!!", Toast.LENGTH_SHORT).show();
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
                params.put("Id",String.valueOf(id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_lop,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DialogAdd();
        return super.onOptionsItemSelected(item);
    }

    private void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_lop);

        EditText edtTenLop = (EditText) dialog.findViewById(R.id.edtTenLop);
        Button btnThem = (Button) dialog.findViewById(R.id.btnThemLop);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuyLop);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLop = edtTenLop.getText().toString().trim();
                if (tenLop.isEmpty()){
                    Toast.makeText(LopActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    InsertLop(tenLop);
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
    }
    public void DialogUpdate(int id,String tenLop){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_lop);

        EditText edtTenLop = (EditText) dialog.findViewById(R.id.edtTenLop);
        Button btnSua = (Button) dialog.findViewById(R.id.btnSuaLop);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuyLop);
        edtTenLop.setText(tenLop);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLop = edtTenLop.getText().toString().trim();
                if (tenLop.isEmpty()){
                    Toast.makeText(LopActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateLop(id,tenLop);
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
    }

}