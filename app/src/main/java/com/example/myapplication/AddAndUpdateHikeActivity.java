package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.database.MyDatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddAndUpdateHikeActivity extends AppCompatActivity {
    private static final String[] parkingAvailableSpinnerItems = {"True", "False"};
    private static final String[] diffLevelSpinnerItems = {"Very easy", "Easy", "Normal", "Hard", "Very hard"};

    private MyDatabaseHelper myDatabaseHelper;
    String function;
    int hikeId;
    Button btn_save_hike;
    TextInputEditText txt_hike_name, txt_hike_location, txt_date_of_hike, txt_length_of_hike, txt_hiker_number, txt_hike_description;
    Spinner spn_level_diff, spn_parking_available;
    RadioGroup rdg_hike_type;
    RadioButton rdb_on_day;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_add_and_update);
        setTitle("Create new hike");
        myDatabaseHelper = new MyDatabaseHelper(this);

        //set interface variable
        btn_save_hike = findViewById(R.id.btn_save_hike);
        txt_hike_name = findViewById(R.id.txt_hike_name);
        txt_hike_location = findViewById(R.id.txt_hike_location);
        txt_length_of_hike = findViewById(R.id.txt_length_of_hike);
        txt_date_of_hike = findViewById(R.id.txt_date_of_hike);
        txt_hiker_number = findViewById(R.id.txt_hiker_number);
        txt_hike_description = findViewById(R.id.txt_hike_description);
        spn_level_diff = findViewById(R.id.spn_level_diff);
        spn_parking_available = findViewById(R.id.spn_parking_available);
        rdg_hike_type = findViewById(R.id.rdg_hike_type);
        rdb_on_day = findViewById(R.id.rdb_on_day);



        //date picker
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yyyy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
                txt_date_of_hike.setText(dateFormat.format(calendar.getTime()));
            }
        };
        txt_date_of_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAndUpdateHikeActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //set items for spinner
        ArrayAdapter<String> parkingAvailableSpinnerAdapter = new ArrayAdapter<String>(AddAndUpdateHikeActivity.this, android.R.layout.simple_spinner_dropdown_item, parkingAvailableSpinnerItems);
        parkingAvailableSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_parking_available.setAdapter(parkingAvailableSpinnerAdapter);

        ArrayAdapter<String> diffLevelSpinnerAdapter = new ArrayAdapter<String>(AddAndUpdateHikeActivity.this, android.R.layout.simple_spinner_dropdown_item, diffLevelSpinnerItems);
        diffLevelSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_level_diff.setAdapter(diffLevelSpinnerAdapter);

        //Determine function
        if (getIntent().hasExtra("function")){
            function = getIntent().getStringExtra("function");
        }

        //set "on day" radio button to be default option
        rdb_on_day.setChecked(true);

        //if function is update, set data to input field
        if (function.equals("UPDATE") && getIntent().hasExtra("hikeId") && getIntent().hasExtra("hikeName")&& getIntent().hasExtra("hikeLocation") && getIntent().hasExtra("dateOfHike")
                && getIntent().hasExtra("parkingAvailable") && getIntent().hasExtra("levelOfDifficulty") && getIntent().hasExtra("hikeDescription") && getIntent().hasExtra("hikerNumber") && getIntent().hasExtra("hikeType") && getIntent().hasExtra("hikeLength")){
            setTitle(getIntent().getStringExtra("hikeName"));
            hikeId = getIntent().getIntExtra("hikeId", -1);
            txt_hike_name.setText(getIntent().getStringExtra("hikeName"));
            txt_hike_location.setText(getIntent().getStringExtra("hikeLocation"));
            txt_length_of_hike.setText(getIntent().getStringExtra("hikeLength"));


            txt_date_of_hike.setText(getIntent().getStringExtra("dateOfHike"));
            txt_hiker_number.setText(getIntent().getStringExtra("hikerNumber"));
            txt_hike_description.setText(getIntent().getStringExtra("hikeDescription"));

            String parkingAvailable = getIntent().getStringExtra("parkingAvailable");
            if (parkingAvailable.equals("True")) spn_parking_available.setSelection(0);
            else spn_parking_available.setSelection(1);

            String hikeLevel = getIntent().getStringExtra("levelOfDifficulty");
            switch (hikeLevel){
                case "Very easy":
                    spn_level_diff.setSelection(0);
                    break;
                case "Easy":
                    spn_level_diff.setSelection(1);
                    break;
                case "Medium":
                    spn_level_diff.setSelection(2);
                    break;
                case "Hard":
                    spn_level_diff.setSelection(3);
                    break;
                case "Very hard":
                    spn_level_diff.setSelection(4);
                    break;
            }

            String hikeType = getIntent().getStringExtra("hikeType");
            RadioButton checkedButton;
            switch (hikeType){
                case "Overnight":
                    checkedButton = findViewById(R.id.rdb_overnight);
                    checkedButton.setChecked(true);
                    break;
                case "Multi day":
                    checkedButton = findViewById(R.id.rdb_multi_day);
                    checkedButton.setChecked(true);
                    break;
            }
            btn_save_hike.setText("Update");
        }



        btn_save_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validation
                if (function.equals("UPDATE")){

                }
                List<Boolean> checkResults = new ArrayList<Boolean>();
                checkResults.add(isTextInputEditTextEmpty(txt_hike_name));
                checkResults.add(isTextInputEditTextEmpty(txt_hike_location));
                checkResults.add(isTextInputEditTextEmpty(txt_date_of_hike));
                checkResults.add(isTextInputEditTextEmpty(txt_length_of_hike));
                checkResults.add(isTextInputEditTextEmpty(txt_hiker_number));

                if(!checkResults.contains(false)){
                    //Get data from UI
                    String hikeName = txt_hike_name.getText().toString().trim();
                    String hikeLocation = txt_hike_location.getText().toString().trim();
                    String dateOfHike = txt_date_of_hike.getText().toString().trim();
                    boolean isParkingAvailable = spn_parking_available.getSelectedItem().toString().equals("True") ? true : false;
                    Float lengthOfHike =  Float.valueOf(txt_length_of_hike.getText().toString().trim());
                    String levelOfDifficulty = spn_level_diff.getSelectedItem().toString();
                    RadioButton checkedRadioButton = findViewById(rdg_hike_type.getCheckedRadioButtonId());
                    String typeOfHike = checkedRadioButton.getText().toString().trim();
                    int hikerNumber = Integer.valueOf(txt_hiker_number.getText().toString().trim());
                    String hikeDescription = txt_hike_description.getText().toString().trim();

                    //add function
                    if (function.equals("ADD")){
                        //Add data to database
                        long result = myDatabaseHelper.addNewHike(hikeName, hikeLocation, dateOfHike, isParkingAvailable, lengthOfHike, levelOfDifficulty,hikerNumber,
                                typeOfHike, hikeDescription);
                        if (result == -1){
                            Toast.makeText(AddAndUpdateHikeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }else {
                            //Refresh input if success
                            refreshInputForm();
                            Toast.makeText(AddAndUpdateHikeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    } else { //update function

                        long result = myDatabaseHelper.updateHike(hikeId+"", hikeName, hikeLocation, dateOfHike, isParkingAvailable, lengthOfHike, levelOfDifficulty,hikerNumber,
                                typeOfHike, hikeDescription);
                        if (result == -1){
                            Toast.makeText(AddAndUpdateHikeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddAndUpdateHikeActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }
        });
    }

    //validation
    private boolean isTextInputEditTextEmpty(TextInputEditText txt){

        if (txt.getText().toString().trim().length() == 0){
            txt.setError("This field is required");
            return false;
        }
        return true;
    }


    //Refresh input form
    private void refreshInputForm(){
        txt_hike_name.setText("");
        txt_hike_location.setText("");
        txt_date_of_hike.setText("");
        txt_length_of_hike.setText("");
        txt_hiker_number.setText("");
        txt_hike_description.setText("");
        rdb_on_day.setChecked(true);
        spn_parking_available.setSelection(0);
        spn_level_diff.setSelection(0);
    }

}