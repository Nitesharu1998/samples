package com.example.samples;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_city, spinner_age, spinner_category, spinner_app, spinner_name, spinner_imagesize, spinner_bdate, spinner_religion, spinner_gender;//file_type(to add)
    Button sub;
    String last;
    final int REQUEST_EXTERNAL_STORAGE = 100;
    RecyclerView rclview;
    MainAdapter mainAdapter;
    ArrayList<Model1> model1 = new ArrayList<>();
    Uri imageUri;
    private List<Uri> bitmaps;
    Model1 model;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_city = findViewById(R.id.spn_city);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(adapter);
        spinner_city.setOnItemSelectedListener(this);

//.,.,.,.,.,

        spinner_religion = findViewById(R.id.spn_religion);
        ArrayAdapter<CharSequence> spn_religion_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_religion_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_religion.setAdapter(spn_religion_adapter);
        spinner_religion.setOnItemSelectedListener(this);

//.,.,.,.,.,
        spinner_age = findViewById(R.id.spn_age);
        ArrayAdapter<CharSequence> spn_age_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(spn_age_adapter);
        spinner_age.setOnItemSelectedListener(this);
//.,.,.,.,.,
        spinner_name = findViewById(R.id.spn_name);
        ArrayAdapter<CharSequence> spn_name_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_name.setAdapter(spn_name_adapter);
        spinner_name.setOnItemSelectedListener(this);
//.,.,.,.,.,


        spinner_app = findViewById(R.id.spn_app);
        ArrayAdapter<CharSequence> spn_app_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_app_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_app.setAdapter(spn_app_adapter);
        spinner_app.setOnItemSelectedListener(this);
//.,.,.,.,.,

        spinner_bdate = findViewById(R.id.spn_bdate);
        ArrayAdapter<CharSequence> spn_bdate_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_bdate_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bdate.setAdapter(spn_bdate_adapter);
        spinner_bdate.setOnItemSelectedListener(this);
//.,.,.,.,.,
        spinner_category = findViewById(R.id.spn_category);
        ArrayAdapter<CharSequence> spn_category_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(spn_category_adapter);
        spinner_category.setOnItemSelectedListener(this);
//.,.,.,.,.,
        spinner_gender = findViewById(R.id.spn_gender);
        ArrayAdapter<CharSequence> spn_gender_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(spn_gender_adapter);
        spinner_gender.setOnItemSelectedListener(this);
//.,.,.,.,.,
        spinner_imagesize = findViewById(R.id.spn_imgsize);
        ArrayAdapter<CharSequence> spn_imagesize_adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        spn_imagesize_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_imagesize.setAdapter(spn_imagesize_adapter);
        spinner_imagesize.setOnItemSelectedListener(this);
//.,.,.,.,.,


        rclview = findViewById(R.id.rclview);
        Button button = findViewById(R.id.img_selector);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                    return;
                } else {
                    launchGalleryIntent();
                }
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    launchGalleryIntent();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    public void launchGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        //last=spinner.getSelectedItem().toString()+" "+spinner_religion.getSelectedItem().toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            uri = item.getUri();

                            model = new Model1(uri);
                            model1.add(model);

//                            ClipData.Item item = mClipData.getItemAt(i);
//                            uri = item.getUri();
//                            mArrayUri.add(uri);

                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri,
                                    filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                            cursor.close();
                        }

                        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                                LinearLayoutManager.HORIZONTAL, false);
                        rclview.setLayoutManager(layoutManager);
                        rclview.setItemAnimator(new DefaultItemAnimator());

                        mainAdapter = new MainAdapter(MainActivity.this, model1);
                        rclview.setAdapter(mainAdapter);


//                            for (int a = 0; a <= mClipData.getItemCount(); a++) {
//
//
//                            }


                    }





                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.i("ex", e.getLocalizedMessage());
            Toast.makeText(this, "Something went wrong " + e.toString(), Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submit(View view) {

    }
}

