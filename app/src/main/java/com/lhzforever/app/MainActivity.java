package com.lhzforever.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button camera;
    private Button album;
    private Button test;
    ProgressDialog progressDialog;
    String URL ="http://121.36.36.134:5555/getIndice";
    private final int CAMERA_REQUEST = 1;
    private final int ALBUM_REQUEST = 2;
    private int[] indices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = (Button) findViewById(R.id.camera);
        album = (Button) findViewById(R.id.album);
        test = (Button) findViewById(R.id.test);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.CAMERA",
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"},101);
        }

        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST);
            }
        });


        album.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{"android.permission.CAMERA",
                            "android.permission.READ_EXTERNAL_STORAGE",
                            "android.permission.WRITE_EXTERNAL_STORAGE"},101);
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), ALBUM_REQUEST);
            }
        });


        test.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityGallery.class);
                intent.putExtra("indices", MainActivity.this.indices);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
        }
        if (resultCode == RESULT_OK && requestCode == ALBUM_REQUEST) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_OK) {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Uploading, please wait...");
            progressDialog.show();

            final RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);

            //converting image to base64 string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    progressDialog.dismiss();
                    System.out.println("Indices: " + s);
                    Toast.makeText(MainActivity.this, "Uploaded Successful", Toast.LENGTH_LONG).show();
                    int[] indices = Arrays.stream(s.substring(1, s.length() - 1).split(","))
                            .map(String::trim)
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    MainActivity.this.indices = indices;
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(MainActivity.this, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
                }
            }) {
                //adding parameters to send
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("imageBase", imageString);
                    return parameters;
                }
            };
            rQueue.add(request);
        }
    }
}
