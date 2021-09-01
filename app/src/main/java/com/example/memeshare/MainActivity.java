 package com.example.memeshare;

 import android.annotation.SuppressLint;
 import android.app.MediaRouteButton;
 import android.content.Intent;
 import android.graphics.drawable.Drawable;

 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.widget.ImageView;
 import android.widget.ProgressBar;
 import android.widget.Toast;

 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;

 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.JsonObjectRequest;
 import com.android.volley.toolbox.UrlRewriter;
 import com.android.volley.toolbox.Volley;
 import com.bumptech.glide.Glide;
 import com.bumptech.glide.load.DataSource;
 import com.bumptech.glide.load.engine.GlideException;
 import com.bumptech.glide.request.RequestListener;
 import com.bumptech.glide.request.target.Target;

 import org.json.JSONException;
 import org.json.JSONObject;

 import java.util.Random;

 public class MainActivity extends AppCompatActivity {
     ImageView imageView1;
     String  memeImageUrl;
     ProgressBar progressBar;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadMeme();

    }

    public void loadMeme(){

               progressBar=findViewById(R.id.progressBar);
        String MEME_URL = "https://meme-api.herokuapp.com/gimme";
                        // Request a object response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MEME_URL,null, response -> {

            try {
                imageView1= findViewById(R.id.imageView2);
                memeImageUrl = response.getString("url");
                Log.d("URL", memeImageUrl);
                Glide.with(MainActivity.this).load(memeImageUrl).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageView1);

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        ,error -> {
            // TODO: Handle error
            Log.d("OnError", "Error Occurred");

        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    public void nextButton(View view) {
         progressBar.setVisibility(View.VISIBLE);
         loadMeme();
    }

    public void shareMeme(View view) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Hey Checkout this Meme\n" + memeImageUrl);
        intent.setType("text/plain");
        startActivity((Intent.createChooser(intent,"MEME SHARE APP")));
    }


 }


