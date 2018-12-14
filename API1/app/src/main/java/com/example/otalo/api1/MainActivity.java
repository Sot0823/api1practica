package com.example.otalo.api1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//se importan las librerías del tipo Widget
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//se importa la utilidad de Lista
import java.util.List;

//Se importan las librerías específicas que se utilizarán del paquete de retrofit2
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listViewHeroes);

        //Se llama al método que deslpliega a los 'Heroes'
        getHeroes();
    }

    private void getHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Aquí usamos el GsonConverterFactory para convertir directamente la data en formato JSON a Objetos
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Hero>> call = api.getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();

                //Se crea un Array de tipo String para el ListView
                String[] heroes = new String[heroList.size()];

                //Proceso cíclico de los heroes que los inserta dentro del array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                }


                //Despliega el Arreglo de tipo String en la ListView
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
}