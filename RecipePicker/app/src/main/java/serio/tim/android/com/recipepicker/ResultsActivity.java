package serio.tim.android.com.recipepicker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import serio.tim.android.com.recipepicker.model.Hits;
import serio.tim.android.com.recipepicker.model.MainPojo;
import serio.tim.android.com.recipepicker.model.Recipe;

public class ResultsActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.edamam.com/search?q=";
    private static final String APP_ID = "app_id=e20bc88f";
    private static final String APP_KEY = "app_key=6f57da7de3ddcaed130b83e7ecffc0ad";
    private RecyclerView rec;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SpinKitView s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.appbar_title);

        rec = (RecyclerView) findViewById(R.id.recycler_view_results);
        rec.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rec.setLayoutManager(layoutManager);

        s = (SpinKitView) findViewById(R.id.spin_kit_results);
        s.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String food = intent.getStringExtra("food");
        String dietRes = intent.getStringExtra("dietRes");
        String healthRes = intent.getStringExtra("healthRes");
        int numIngredients = intent.getIntExtra("numIngredients", 0);
        String excludeStr = intent.getStringExtra("exclude");
        int minTime = intent.getIntExtra("minMinutes", 0);
        int maxTime = intent.getIntExtra("maxMinutes", 0);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://api.edamam.com").addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        EdamameClient client = retrofit.create(EdamameClient.class);

        Map<String, Object> map = new LinkedHashMap<>();
        final HashMap<String, String> recipesMap = new HashMap<>();

        map.put("q", food);
        map.put("app_id", "e20bc88f");
        map.put("app_key", "6f57da7de3ddcaed130b83e7ecffc0ad");
        map.put("diet", dietRes);

        if (!(healthRes.equals(""))) {
            map.put("health", healthRes);
        }


        if (minTime != 0 && maxTime != 0) {
            if (maxTime > minTime) {
                map.put("time", minTime + "-" + maxTime);
            }
        } else if (minTime == 0 && maxTime != 0) {
            map.put("time", maxTime);
        } else if (minTime != 0 && maxTime == 0) {
            map.put("time", minTime + "+");
        } else if (minTime == 0 && maxTime == 0) {

        }

        if (excludeStr.equals("") || excludeStr.equals(" ")) {

        } else {
            map.put("excluded", excludeStr);
        }

        if (numIngredients > 0) {
            map.put("ingr", numIngredients);
        }

        map.put("from", 0);
        map.put("to", 100);

        Call<MainPojo> call = client.recipesForSearch(map);

        new GetRecipesAsync().execute(call);
    }

    private class GetRecipesAsync extends AsyncTask<Call<MainPojo>, Integer, HashMap<String, String>> {

        @Override
        protected HashMap<String, String> doInBackground(Call<MainPojo>... pojos) {
            HashMap<String, String> recipesMap = new HashMap<>();
            try {
                Response<MainPojo> response = pojos[0].execute();

                MainPojo pojo = response.body();
                if(pojo != null) {
                    List<Hits> hits = pojo.getHits();
                    List<Recipe> recipes = new ArrayList<>();
                    for (Hits hit : hits) {
                        recipes.add(hit.getRecipe());
                        recipesMap.put(hit.getRecipe().getLabel(), hit.getRecipe().getUrl());
                    }
                }
            } catch (IOException e) {
                e.getMessage();
            }
            return recipesMap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Toast.makeText(ResultsActivity.this, "progressUpdate", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(HashMap<String, String> map) {
            super.onPostExecute(map);

            String[] r = map.keySet().toArray(new String[map.size()]);

            adapter = new ResultsAdapter(ResultsActivity.this, r, map);
            rec.setAdapter(adapter);

            s.setVisibility(View.GONE);
            rec.setVisibility(View.VISIBLE);
            if (r.length > 0) {
                TextView selectRecipe = (TextView) findViewById(R.id.text_results_select_recipe);
                selectRecipe.setVisibility(View.VISIBLE);
            }


            if (r.length == 0) {
                TextView noResults = (TextView) findViewById(R.id.text_results_none);
                rec.setVisibility(View.VISIBLE);
                noResults.setVisibility(View.VISIBLE);
            }
        }
    }

    public void backClick(View view) {
        finish();
    }
}
