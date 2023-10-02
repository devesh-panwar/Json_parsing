package com.training.json_parsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String url="https://anwikdevesh.000webhostapp.com/getdata.php";

    ListView listView;
    ArrayList<String>arrayList;
    ArrayAdapter<String>arrayAdapter;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);
        arrayList=new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                   /* arrayList.add(response);
                    arrayAdapter.notifyDataSetChanged();*/

                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("result");

                    for(int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        int id=obj.getInt("id");
                        String name=obj.getString("name");
                        int salary=obj.getInt("salary");
                        String email=obj.getString("email");
                        String pass=obj.getString("password");

                        arrayList.add("Id:"+id+"\nName: "+name+"\nsalary:"+salary+"\nemail:"+email+"\npassword:"+pass);
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
                catch (Exception exception){

                    Toast.makeText(MainActivity.this, " Excetion caught "+exception, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}