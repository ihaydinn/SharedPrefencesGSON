package com.ismailhakkiaydin.sharedprefencesgson;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData= (TextView) findViewById(R.id.data);

    }

    public void saveCustomClassObject(View view) {

        Calisan calisan=calisanOlustur();

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson=new Gson();
        String jsonStr= gson.toJson(calisan, Calisan.class);

        editor.putString("calisan_key", jsonStr);

        editor.apply();

    }

    public void loadCustomClassObject(View view) {

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        String jsonStr= sharedPreferences.getString("calisan_key", "");

        Gson gson=new Gson();
        Calisan calisan=gson.fromJson(jsonStr, Calisan.class);

        calisanGoster(calisan);

    }

    private void calisanGoster(Calisan calisan) {

        String goster= calisan.getIsim()
                + "\n" + calisan.getMeslek()
                + "\n" + calisan.getId()
                + "\n" + (calisan.getAktif() ? "Çalışıyor" : "Çalışmıyor")
                + "\n" + calisan.getGorev().toString();

        tvData.setText(goster);


    }

    private Calisan calisanOlustur(){

        Calisan calisan=new Calisan();

        calisan.setIsim("İHA");
        calisan.setMeslek("Mühendis");
        calisan.setAktif(true);
        calisan.setId(150);
        calisan.setGorev(Arrays.asList("Yönetici", "Developer"));

        return calisan;


    }

    public void saveGenericType(View view) {

        Calisan yeniCalisan=calisanOlustur();
        TumPersonel<Calisan> personel=new TumPersonel<>();
        personel.setObject(yeniCalisan);

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson=new Gson();

        Type type=new TypeToken<TumPersonel<Calisan>>(){}.getType();

        String jsonStr=gson.toJson(personel, type);
        editor.putString("generic_type_key", jsonStr);

        editor.apply();

        Log.i("emre", "Kaydedilen :"+jsonStr);


    }

    public void loadGenericType(View view) {

        SharedPreferences sharedPreferences=getPreferences(MODE_PRIVATE);
        String jsonStr=sharedPreferences.getString("generic_type_key", "");

        Gson gson=new Gson();
        Type type=new TypeToken<TumPersonel<Calisan>>(){}.getType();
        TumPersonel<Calisan> calisan= gson.fromJson(jsonStr, type);
        Calisan calisanEleman=calisan.getObject();

        calisanGoster(calisanEleman);


    }
}