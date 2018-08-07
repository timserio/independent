package serio.tim.android.com.recipepicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String[] randomFoods = { "brat", "pizza", "beer cheese", "chili", "duck", "ramen", "chicken", "pork", "steak", "steak salad", "tuna", "salmon", "indian", "italian", "asian", "mexican", "fajita", "chicken vesuvio", "spanish", "burrito", "burger", "ribs", "pho", "sushi", "lasagna", "pasta", "taco", "torta", "wrap", "red beans and rice" };
    private static final String[] DIETS = { "Balanced", "High-fiber", "High-protein", "Low-carb", "Low-fat", "Low-sodium" };
    private static final String[] HEALTH = {
            "", "Alcohol-free", "Celery-free", "Crustacean-free", "Dairy-free", "Egg-free", "Fish-free",
            "Gluten-free", "Kidney-friendly", "Kosher", "Low-potassium", "Lupine-free", "Mustard-free",
            "No-oil-added", "Low-sugar", "Paleo", "Peanut-free", "Pescatarian", "Pork-free", "Red-meat-free",
            "Sesame-free", "Shellfish-free", "Soy-free", "Sugar-conscious", "Tree-nut-free", "Vegan",
            "Vegetarian", "Wheat-free"

    };

    private String food;
    private String exclude;
    private String healthRes = "";
    private String dietRes = "balanced";
    private int numbrIngredients;
    private int minMinutes;
    private int maxMinutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.appbar_title);

        setUpDietSpinner();
        setUpHealthSpinner();
    }


    private void setUpDietSpinner() {
        MaterialSpinner sp = (MaterialSpinner) findViewById(R.id.spinner_main_diet);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,DIETS
        );

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(spinnerArrayAdapter);

        sp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String diet = DIETS[position];
                dietRes = diet.toLowerCase();
            }
        });
    }

    private void setUpHealthSpinner() {
        MaterialSpinner sp = (MaterialSpinner) findViewById(R.id.spinner_main_health);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,HEALTH
        );

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(spinnerArrayAdapter);

        sp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String health = HEALTH[position];
                healthRes = health.toLowerCase();
            }
        });
    }

    public void randomFoodClick(View view) {
        EditText foodText = (EditText) findViewById(R.id.edittext_main_food);
        Random rand = new Random();
        int r = rand.nextInt(randomFoods.length) + 0;
        String rndmFood = randomFoods[r];
        foodText.setText(rndmFood);
        food = rndmFood;
    }

    public void doneButtonClick(View view) {
        EditText foodText = (EditText) findViewById(R.id.edittext_main_food);
        food = foodText.getText().toString().toLowerCase();
        food = food.replace(' ', '+');
        EditText excludeText = (EditText) findViewById(R.id.edittext_main_exclude);
        exclude = excludeText.getText().toString().toLowerCase();

        EditText ingredientsText = (EditText) findViewById(R.id.edittext_main_ingredients);
        try {
            numbrIngredients = Integer.parseInt(ingredientsText.toString());
        } catch (NumberFormatException e) {
            numbrIngredients = 0;
        }

        EditText minMinutesText = (EditText) findViewById(R.id.edittext_main_minimum_cooktime);
        if(!(minMinutesText.toString().equals(""))) {
            try {
                minMinutes = Integer.parseInt(minMinutesText.toString());
            } catch (NumberFormatException e) {
                minMinutes = 0;
            }
        }

        EditText maxMinutesText = (EditText) findViewById(R.id.edittext_main_maximum_cooktime);
        if(!(maxMinutesText.toString().equals(""))) {
            try {
                maxMinutes = Integer.parseInt(maxMinutesText.toString());
            } catch (NumberFormatException e) {
                maxMinutes = 0;
            }
        }

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("food", food);
        intent.putExtra("exclude", exclude);
        intent.putExtra("healthRes", healthRes);
        intent.putExtra("dietRes", dietRes);
        intent.putExtra("numIngredients", numbrIngredients);
        intent.putExtra("minMinutes", minMinutes);
        intent.putExtra("maxMinutes", maxMinutes);
        startActivity(intent);
    }
}
