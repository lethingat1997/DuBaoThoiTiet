package ngatle.com.dubaothoitiet;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText Timkiem;
    ImageButton Seach;
    TextView BTngaytieptheo;
    TextView TVtp,TVqg,TVnt,TVtt,TVmay,TVdo_am,TVgio,TVncn;
    ImageView imgIcon,Black ;
    String City="";
    String tenthanhpho = "";
    ListView Lv;


    CustomAdapter customAdapter;//
    ArrayList<ThoiTiet> mangthoitiet;//
    //    Locale mMyLocal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Anhxa();


        GetCurrenWeatherData("Ninh Binh");
         Get7daysData("Ninh Binh");
        Seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city= Timkiem.getText().toString();
                if (city.equals("")){
                    City= "Ninh Binh";
                    GetCurrenWeatherData(City);
                    Get7daysData(City);
                }else {
                    City = city;
                    GetCurrenWeatherData(City);
                    Get7daysData(City);
                }

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        View header = navigationView.getHeaderView(0);
        TextView tvname = header.findViewById(R.id.tvname);
        TextView tvmail = header.findViewById(R.id.Tvmail);

        tvname.setText("Lê Thị Ngát");
        tvmail.setText("lengat123@gmail.com");
    }
    public void GetCurrenWeatherData (String data){
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url="https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=1070d8d081ca23848b87aa2dd165c7a5&lang=vi";
        Log.e("Test url current", url);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Test ok  ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            TVtp.setText("Thành Phố:"+name+",");

                            long l = Long.valueOf(day);
                            String Day= getDateOfTimeNumber(l*1000);

                            TVncn.setText(Day);
                            JSONArray jsonArrayweather= jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectweather= jsonArrayweather.getJSONObject(0);
                            String status= jsonObjectweather.getString("description");
                            String icon = jsonObjectweather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/"+ icon+".png").into(imgIcon);
                            TVtt.setText(status);

                            JSONObject jsonObjectMain= jsonObject.getJSONObject("main");
                            String nhietdo= jsonObjectMain.getString("temp");
                            String doam= jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String Nhietdo= String.valueOf(a.intValue());

                            TVnt.setText(nhietdo+"°C");
                            TVdo_am.setText(doam+"%");

                            JSONObject jsonObjectwind= jsonObject.getJSONObject("wind");
                            String gio= jsonObjectwind.getString("speed");
                            TVgio.setText(gio+"m/s");

                            JSONObject jsonObjectcloud = jsonObject.getJSONObject("clouds");
                            String may= jsonObjectcloud.getString("all");
                            TVmay.setText(may+"%");

                            JSONObject jsonObjectsys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectsys.getString("country");
                            TVqg.setText(""+country);
//                            Toast.makeText(MainActivity.this, "Loaded" +response , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(MainActivity.this, ""+e.toString()  , Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    private void Get7daysData(final String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&appid=1070d8d081ca23848b87aa2dd165c7a5&lang=vi";
        Log.e("Test url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        Log.e("Test OK", response);
                        try {

                            JSONObject jsonObjec = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObjec.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");


                            JSONArray jsonArraylist = jsonObjec.getJSONArray("list");
                            mangthoitiet.clear();
                            for (int i = 0; i < jsonArraylist.length(); i++) {
                                JSONObject jsonObjectlist = jsonArraylist.getJSONObject(i);
                                String ngay = jsonObjectlist.getString("dt");

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l*1000); //  String Day = getDateOfTimeNumber(l*1000);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM HH:mm");
                                String Day = simpleDateFormat.format(date);


                                JSONObject jsonObjectMain= jsonObjectlist.getJSONObject("main");
                                String max = jsonObjectMain.getString("temp_max");
                                String mix = jsonObjectMain.getString("temp_min");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(mix);
                                String Nhietdomax = String.valueOf(a.intValue());
                                String Nhietdomix = String.valueOf(b.intValue());

                                JSONArray jsonArrayweather = jsonObjectlist.getJSONArray("weather");
                                JSONObject jsonObjecweather = jsonArrayweather.getJSONObject(0);
                                String status = jsonObjecweather.getString("description");
                                String icon = jsonObjecweather.getString("icon");

                                ThoiTiet tt = new ThoiTiet(Day, status, icon, Nhietdomax, Nhietdomix);
                                Log.e("Test", tt.Day + "/" + tt.Max + "/" + tt.Min);
                                mangthoitiet.add(tt);
                            }

                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private String getDateOfTimeNumber(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
        String day = simpleDateFormat.format(calendar.getTime());
        return day;
    }

    private void Anhxa(){
        Black = (ImageView) findViewById(R.id.back);
        Timkiem = (EditText) findViewById(R.id.timkiem);
        Seach= (ImageButton) findViewById(R.id.seach);
        BTngaytieptheo = (TextView) findViewById(R.id.Btngaytieptheo);
        TVtp = (TextView)findViewById(R.id.Tvtp);
        TVqg = (TextView)findViewById(R.id.Tvqg);
        TVnt = (TextView)findViewById(R.id.Tvnt);
        TVtt = (TextView)findViewById(R.id.Tvtt);
        TVmay = (TextView)findViewById(R.id.Tvmay);
        TVdo_am = (TextView)findViewById(R.id.Tvdo_am);
        TVgio = (TextView)findViewById(R.id.Tvgio);
        TVncn = (TextView)findViewById(R.id.Tvncn);
        imgIcon= (ImageView)findViewById(R.id.IManh);

        Lv = (ListView) findViewById(R.id.Lvman2);
        mangthoitiet = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(MainActivity.this, mangthoitiet);
        Lv.setAdapter(customAdapter);

    }



    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_camera:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new man1_vn()).commit();
                break;
                case R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.nav_manage:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new man2_vn()).commit();
                break;
            case R.id.nav_slideshow:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new man3_vn()).commit();
                break;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
