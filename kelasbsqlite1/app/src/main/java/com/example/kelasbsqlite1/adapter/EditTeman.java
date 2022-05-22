package com.example.kelasbsqlite1.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kelasbsqlite1.App.AppController;
import com.example.kelasbsqlite1.MainActivity;
import com.example.kelasbsqlite1.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class EditTeman extends AppCompatActivity {
    TextView idText;
    EditText Nama, Telepon;
    Button Save;
    String nma,tlp,id,namaEd,telponEd;
    int sukses;

    private static String url_update="http://127.0.0.1/umyTI/updatetm.php";
    private static final String TAG = EditTeman.class.getSimpleName();
    private static final String TAG_SUCCES="success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        idText = findViewById(R.id.textId);
        Nama = findViewById(R.id.edNama);
        Telepon = findViewById(R.id.edTelp);
        Save = findViewById(R.id.simpanBTN);

        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("kunci1");
        nma=bundle.getString("kunci2");
        tlp=bundle.getString("kunci3");

        idText.setText("id: "+id);
        Nama.setText(nma);
        Telepon.setText(tlp);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });
    }
    public void EditData()
    {
        namaEd = Nama.getText().toString();
        telponEd = Telepon.getText().toString();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringReq=new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respon: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses = jObj.getInt(TAG_SUCCES);
                    if (sukses == 1) {
                        Toast.makeText(EditTeman.this, "Sukses mengedit data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditTeman.this, "Gaga;", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error: "+error.getMessage());
                Toast.makeText(EditTeman.this, "Gagal Edit data", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("id",id);
                params.put("nama",namaEd);
                params.put("telpon",telponEd);
                return params;
            }
        };
        requestQueue.add(stringReq);
        callHome();
    }
    public void callHome(){
        Intent i = new Intent(EditTeman.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
