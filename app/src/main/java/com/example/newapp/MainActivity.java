package com.example.newapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    EditText t;
    TextView title, rating, year, genre, director, actor, plot;
    Button b;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.searchbutton);
        t = findViewById(R.id.searchbar);
        title = findViewById(R.id.MovieTitle);
        rating = findViewById(R.id.MovieRating);
        year = findViewById(R.id.MovieYear);
        genre = findViewById(R.id.MovieGenre);
        director = findViewById(R.id.MovieDirector);
        actor = findViewById(R.id.MovieActors);
        plot = findViewById(R.id.Plot);
        im = findViewById(R.id.posterimage);

        b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String movie = t.getText().toString();
                if(!movie.equals("")) {
                    new Myasynctask().execute(movie);
                    new ImageTask().execute(movie);
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class Myasynctask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject != null) {
                    title.setText(String.format("Title : %s", jsonObject.getString("Title")));
                    year.setText(String.format("Year : %s", jsonObject.getString("Year")));
                    rating.setText(String.format("IMDB Rating : %s", jsonObject.getString("imdbRating")));
                    actor.setText(String.format("Actors : %s", jsonObject.getString("Actors")));
                    genre.setText(String.format("Genre : %s", jsonObject.getString("Genre")));
                    director.setText(String.format("Director : %s", jsonObject.getString("Director")));
                    plot.setText(String.format("Plot : %s", jsonObject.getString("Plot")));

                } else
                    Toast.makeText(MainActivity.this, "Movie does not exist.", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Movie does not exist.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String data = "";
            StringTokenizer st = new StringTokenizer(strings[0]);
            StringBuilder movie = new StringBuilder();
            while (st.hasMoreTokens())
                movie.append(st.nextToken()).append("+");
            System.out.println(movie);
            movie = new StringBuilder(movie.substring(0, movie.length() - 1));
            try {
                URL url = new URL("http://www.omdbapi.com/?t=" + movie + "&apikey=2ce258ad");
                HttpURLConnection hp = (HttpURLConnection) url.openConnection();
                InputStream inputStream = hp.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = br.readLine();
                    data += line;
                }
                System.out.println(data);
                return new JSONObject(data);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class ImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String data = "";
            StringTokenizer st = new StringTokenizer(strings[0]);
            StringBuilder movie = new StringBuilder();
            while (st.hasMoreTokens())
                movie.append(st.nextToken()).append("+");
            System.out.println(movie);
            movie = new StringBuilder(movie.substring(0, movie.length() - 1));
            try {
                URL url = new URL("http://www.omdbapi.com/?t=" + movie + "&apikey=2ce258ad");
                HttpURLConnection hp = (HttpURLConnection) url.openConnection();
                InputStream inputStream = hp.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = br.readLine();
                    data += line;
                }
                JSONObject js = new JSONObject(data);
                System.out.println(data);
                URL url1 = new URL(js.getString("Poster"));
                return BitmapFactory.decodeStream(url1.openConnection().getInputStream());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null)
                im.setImageBitmap(bitmap);
        }
    }
}