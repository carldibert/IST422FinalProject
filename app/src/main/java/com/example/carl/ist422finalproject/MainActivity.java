package com.example.carl.ist422finalproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button funShitButton = (Button) findViewById(R.id.funShitButton);
        funShitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new internet().execute();
            }
        });
    }

    public void changeTextOnTest(){

        new internet().execute();

    }

    public class internet extends AsyncTask<Void, Void, Void> {

        String words;

        @Override
        protected Void doInBackground(Void... params){

            try{
                Document doc = Jsoup.connect("http://www.mangareader.net/")
                        .followRedirects(false)
                        .get();

                words = doc.title();
            } catch(Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView text = (TextView) findViewById(R.id.funShitText);
            text.setText(words);
        }
    }
}
