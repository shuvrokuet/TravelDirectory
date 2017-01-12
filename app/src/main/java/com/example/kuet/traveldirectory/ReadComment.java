package com.example.kuet.traveldirectory;

/**
 * Created by shuvro on 1/20/2016.
 */
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadComment extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    private static final String READ_COMMENTS_URL = "http://10.0.2.2/traveldirectory/comments.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TITLE = "title";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_POST_ID = "post_id";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_MESSAGE = "review";
    //private static final String TAG_MESSAGE = "review";

    private ImageView imageView;
    private RatingBar ratingBar;
    //String root_name,bus_code;



    private JSONArray mComments = null;
    String root_name,bus_code;

    private ArrayList<HashMap<String, String>> mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readcomment);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        imageView= (ImageView) findViewById(R.id.imageView2);

        Intent intent = getIntent();
        root_name= intent.getStringExtra("root_name");
        bus_code=intent.getStringExtra("bus_code");

        //String r="eagle";
         if(bus_code.equals("eagle"))
         {
             imageView.setImageDrawable(getResources().getDrawable(R.drawable.eagle));

         }
        if(bus_code.equals("sohag"))
        {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.sohag));

        }
        if(bus_code.equals("aktravels"))
        {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.aktravels));

        }
        if(bus_code.equals("skyline")||bus_code.equals("citycom"))
        {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.skyline));

        }

        if(bus_code.equals("greenline"))
        {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.greenline));

        }
        if(bus_code.equals("shamoli"))
        {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.shamoli));

        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //loading the comments via AsyncTask
        new LoadComments().execute();
    }

    public void addComment(View v)
    {
        /*Intent intent = getIntent();
        String root_name  = intent.getStringExtra("root_name");
        String bus_code=intent.getStringExtra("bus_code");*/

        Intent i = new Intent(ReadComment.this, AddComment.class);
        i.putExtra("bus_code", bus_code);
        i.putExtra("root_name", root_name);
        startActivity(i);
    }

    /**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata()
    {

        //Log.d("root_name: ",root_name);
        //Log.d("bus_code: ",bus_code);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("root_name", root_name));
        params.add(new BasicNameValuePair("bus_code", bus_code));
        params.add(new BasicNameValuePair("root_name", root_name));

        mCommentList = new ArrayList<HashMap<String, String>>();

        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.makeHttpRequest(READ_COMMENTS_URL, "POST", params);

        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try {
            //I know I said we would check if "Posts were Avail." (success==1)
            //before we tried to read the individual posts, but I lied...
            //mComments will tell us how many "posts" or comments are
            //available
            int average=(int)json.getDouble("avg");

            ratingBar.setRating(average);

            mComments = json.getJSONArray(TAG_POSTS);


            // looping through all posts according to the json object returned
            for (int i = 0; i < mComments.length(); i++) {
                JSONObject c = mComments.getJSONObject(i);

                //gets the content of each tag
                String title = c.getString(TAG_TITLE);
                String content = c.getString(TAG_MESSAGE);
                String username = c.getString(TAG_USERNAME);


                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_TITLE, title);
                map.put(TAG_MESSAGE, content);
                map.put(TAG_USERNAME, username);

                // adding HashList to ArrayList
                mCommentList.add(map);

                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts the parsed data into the listview.
     */
    private void updateList() {

        ListAdapter adapter = new SimpleAdapter(this, mCommentList,
                R.layout.single_comment, new String[] { TAG_TITLE, TAG_MESSAGE,
                TAG_USERNAME }, new int[] { R.id.title, R.id.message,
                R.id.username });

        // I shouldn't have to comment on this one:
        setListAdapter(adapter);


        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });
    }

    public class LoadComments extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReadComment.this);
            pDialog.setMessage("Loading Comments...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected Boolean doInBackground(Void... arg0) {
            //we will develop this method in version 2
            updateJSONdata();
            return null;

        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            //we will develop this method in version 2
            updateList();
        }
    }
}
