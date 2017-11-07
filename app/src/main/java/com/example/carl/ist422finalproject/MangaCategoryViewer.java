package com.example.carl.ist422finalproject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Carl on 10/26/2017.
 */

public class MangaCategoryViewer extends ListActivity {
    ArrayList<HashMap<String, String>> Item_List;
    ArrayList stock_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manga_categories);

        new MangaReaderCategories().execute();

        //add onclick listener to send user to tab with all of the mangos of their choice for the category of their choosing
        //this.onListItemClick();
    }

    private class MangaReaderCategories extends AsyncTask<Void, Void, Void> {

        ArrayList<String> sam = new ArrayList<String>();

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<String> copy = new ArrayList<String>();

            try {
                Document doc = Jsoup.connect("http://www.mangareader.net/search")
                        .followRedirects(false)
                        .get();


                Elements genres = doc.getElementsByClass("genre r0");

                for (int i = 0; i < genres.size(); i++)
                    copy.add(genres.get(i).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] n;
            String[] t;
            String temp = "";
            for (int i = 0; i < copy.size(); i++) {
                n = copy.get(i).split(">");
                t = n[1].toString().split("<");
                temp = t[0].toString().replaceAll("\\s+", "");
                sam.add(temp);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            String[] categoriesArray = sam.toArray(new String[sam.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
                    android.R.layout.simple_list_item_1, categoriesArray);
            getListView().setAdapter(adapter);


//      I have no Idea What I'm Doing
//            getListView().setOnClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position,
//                                        long id) {
//                    stock_list.add(Item_List.get(position));


//                    .setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent modify_intent = new Intent(grabedDb.this,
//                                    updatedata.class);
//                            modify_intent.putStringArrayListExtra("stock_list", stock_list);
//
//                            startActivity(modify_intent);
//                        }
//                    });
//        }
//
//            });

        }
    }
}


