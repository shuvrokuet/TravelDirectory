package com.example.kuet.traveldirectory;

/**
 * Created by shuvro on 1/18/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddComment extends Activity implements OnClickListener{

    //private EditText title, review,reason;
    private RatingBar ratingBar;
    private Button  mSubmit;
    //private EditText ratingValue;

    public String post_title,post_review;



    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //php login script


    //testing on Emulator:
    private static final String POST_COMMENT_URL = "http://10.0.2.2/traveldirectory/add_review.php";

    //testing from a real server:
    //private static final String POST_COMMENT_URL = "http://www.mybringback.com/webservice/addcomment.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String root_name,bus_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);

        Intent intent = getIntent();
        root_name  = intent.getStringExtra("root_name");
        bus_code=intent.getStringExtra("bus_code");

        Log.d("root_name_add:: ", root_name);
        Log.d("bus_code_add:: ", bus_code);


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        //title = (EditText)findViewById(R.id.title);
        //review = (EditText)findViewById(R.id.review);
        //reason = (EditText)findViewById(R.id.reason);

        //ratingValue= (EditText) findViewById(R.id.ratingTag);
        RadioGroup goal = (RadioGroup) findViewById(R.id.answer1);

        goal.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                switch (checkedId) {
                    case R.id.answer1A:
                        post_title = "Service: Good";
                        break;
                    case R.id.answer1B:
                        post_title = "Service: Medium";
                        break;
                    case R.id.answer1C:
                        post_title = "Service: Bad";
                        break;
                }
            }
        });

        RadioGroup card = (RadioGroup) findViewById(R.id.answer2);

        card.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                switch (checkedId) {
                    case R.id.answer2A:
                        post_review = "Fare: Reasonable";
                        break;
                    case R.id.answer2B:
                        post_review = "Fare: Costly";
                        break;
                    case R.id.answer2C:
                        post_review = "Fare: Cheap";
                        break;

                }
            }
        });


        mSubmit = (Button)findViewById(R.id.btnSave);
        mSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        new PostComment().execute();
    }


    class PostComment extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddComment.this);
            pDialog.setMessage("Posting Comment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            float user_rating=ratingBar.getRating();
            //String post_title = title.getText().toString();
            //String post_review = review.getText().toString();
            //String post_reason=reason.getText().toString();

            PrefUtils pp=new PrefUtils();

            User user=pp.getCurrentUser(AddComment.this);

            //We need to change this:
            //String post_username = sp.getString("username","shuvro");
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("title", post_title));
                params.add(new BasicNameValuePair("rating", Integer.toString((int) user_rating)));
                params.add(new BasicNameValuePair("comment", post_review));
                params.add(new BasicNameValuePair("username", user.name));

                params.add(new BasicNameValuePair("bus_name", bus_code));
                params.add(new BasicNameValuePair("root_name", root_name));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(POST_COMMENT_URL, "POST", params);

                // full json response
                Log.d("Post Comment attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Log.d("Comment Added!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                }
                else
                {
                    Log.d("Comment Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(AddComment.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }


}
