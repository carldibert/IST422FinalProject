package com.example.carl.ist422finalproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

/**
 * Created by Tyler Brown on 11/30/2017.
 */

public class ImageViewer extends AppCompatActivity {
String websiteURL = "";
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_viewer);
        websiteURL = getIntent().getStringExtra("WebsiteURL");
        new ImageResults().execute();

    }


    private class ImageResults extends AsyncTask<Void, Void, Void> {

        ArrayList<String> images = new ArrayList<>();
        ImageView mpi = (ImageView) findViewById(R.id.Manga_Page_Img);
        String pimage = "";
        String sam;
        String Table = "http://i999.mangareader.net/nanatsu-no-taizai/245/nanatsu-no-taizai-10103275.jpg";
        int f = 0;
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect(websiteURL.toLowerCase())
                        .followRedirects(false)
                        .get();

                Element totalChapters = doc.getElementById("selectpage");
                String[] pageSplit1 = totalChapters.toString().split("select");
                String[] pageSplit2 = pageSplit1[4].split("> of ");
                String[] pageSplit3 = pageSplit2[1].split("<");
                String totalPagesPerChapter = pageSplit3[0];


                ArrayList<String> totalPageURLPerChapter = new ArrayList<String>();
                StringBuilder same = new StringBuilder(3);
                for(int i=0; i<totalPagesPerChapter.toCharArray().length-1; i++) {
                    same.append(totalPagesPerChapter.toCharArray()[i]);
                }
                String notHavingFun = same.toString();
                int pagesPerChapter = Integer.parseInt(notHavingFun);

                for(int i=1; i<pagesPerChapter+1; i++) {
                    String sam = "";
                    if(i == 1) {
                        sam = websiteURL;
                    } else {
                        sam = websiteURL + "/" + i;
                    }
                    totalPageURLPerChapter.add(sam);
                }
                for(int i=0; i<totalPageURLPerChapter.size(); i++) {
                    try {
                        Document doc2 = Jsoup.connect(totalPageURLPerChapter.get(i))
                                .followRedirects(false)
                                .get();

                        Element image = doc2.getElementById("img");
                        String[] image2 = image.toString().split("<");
                        String[] image3 = image2[4].split("=");
//                      String[] image4 = image3[1].split(".jpg");
                        String sam = image3[0] + ".jpg";
                        images.add(sam);

                        char[] Mi = image3[1].toCharArray();
                        ArrayList<Character> imageCharacters = new ArrayList<>();
                        for (i = 1; i < imageCharacters.size(); i++) {
                            imageCharacters.add(Mi[i]);
                        }

                        StringBuilder builder = new StringBuilder(imageCharacters.size());
                        for (i = 0; i < imageCharacters.size(); i++) {
                            builder.append(imageCharacters.get(i));
                        }
                        pimage = builder.toString();
                        Element table = doc.select("table").get(0);
                        Table = table.toString();

                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
       }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            mpi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    f++;

                    Handler handler
                            = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (f == 1){
                                Toast.makeText(ImageViewer.this, Table, Toast.LENGTH_SHORT).show();

                            } else if (f == 2) {
                                Toast.makeText(ImageViewer.this, websiteURL, Toast.LENGTH_SHORT).show();

                            }
                            f = 0;
                        }
                    }, 500);
                }
            });

            Picasso.with(getApplicationContext()).load("http://i999.mangareader.net/nanatsu-no-taizai/245/nanatsu-no-taizai-10103275.jpg").into(mpi);
            String[] titlesArray = images.toArray(new String[images.size()]);
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
//                    android.R.layout.simple_list_item_1, titlesArray);
//            getListView().setAdapter(adapter);

        }
    }
}
