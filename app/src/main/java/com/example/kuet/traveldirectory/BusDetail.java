package com.example.kuet.traveldirectory;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shuvro on 1/15/2016.
 */
public class BusDetail extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    PrefUtils pp=new PrefUtils();
    private static final String READ_COMMENTS_URL = "http://10.0.2.2/traveldirectory/BusdetailJson.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_POSTS = "posts";

    private static final String TAG_BUSNAME = "bus_name";
    private static final String TAG_BUSCODE = "bus_code";
    private static final String TAG_IMAGENAME = "bus_imagename";
    private static final String TAG_CATEGORY= "bus_category";
    private static final String TAG_CITYFROM = "city_from";
    private static final String TAG_CITYTO = "city_to";
    private static final String TAG_ROOTNAME = "root_name";
    String bus_code,root;

    ImageView view;



    private static final String TAG_REVIEW = "review";
    String[] bus_name_array=new String[1000];

    //private static final String TAG_MESSAGE = "review";

    private JSONArray mComments = null;

    private ArrayList<HashMap<String, String>> mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busdetail);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //loading the comments via AsyncTask
        new LoadComments().execute();
    }

    /**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata()
    {

        Intent intent = getIntent();
        String root_name  = intent.getStringExtra("root_name");

        User user=pp.getCurrentUser(BusDetail.this);
        Log.d("shuvro:: ", user.name);



        List<NameValuePair> params = new ArrayList<NameValuePair>();


        params.add(new BasicNameValuePair("root_name", root_name));

        mCommentList = new ArrayList<HashMap<String, String>>();
        JSONParser jParser = new JSONParser();

        JSONObject json = jParser.makeHttpRequest(READ_COMMENTS_URL, "POST",params);

        //when parsing JSON stuff, we should probably
        //try to catch any exceptions:
        try {

            //I know I said we would check if "Posts were Avail." (success==1)
            //before we tried to read the individual posts, but I lied...
            //mComments will tell us how many "posts" or comments are
            //available
            mComments = json.getJSONArray(TAG_POSTS);
            int t=0;

            // looping through all posts according to the json object returned
            for (int i = 0; i < mComments.length(); i++) {
                JSONObject c = mComments.getJSONObject(i);

                //gets the content of each tag
                String bus_name = c.getString(TAG_BUSNAME);
                String bus_category = c.getString(TAG_CATEGORY);
                String cityfrom = c.getString(TAG_CITYFROM);
                //String
                root=c.getString(TAG_ROOTNAME);
                bus_code=c.getString(TAG_BUSCODE);

                bus_name_array[t]=bus_code;
                t++;

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_BUSNAME, bus_name);
                map.put(TAG_CATEGORY, bus_category);
                map.put(TAG_CITYFROM, cityfrom);

                // adding HashList to ArrayList
                mCommentList.add(map);

                //annndddd, our JSON data is up to date same with our array list
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateList()
    {
        ListAdapter adapter = new SimpleAdapter(this, mCommentList,
                R.layout.single_comment, new String[] { TAG_BUSNAME, TAG_CATEGORY,
                TAG_CITYFROM }, new int[] { R.id.title, R.id.message,
                R.id.username });

        setListAdapter(adapter);


        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                for(int i=0;i<bus_name_array.length;i++)
                {
                    if(i==position)
                    {
                        Intent intent=new Intent(BusDetail.this,ReadComment.class);
                        intent.putExtra("bus_code",bus_name_array[i]);
                        intent.putExtra("root_name",root);
                        startActivity(intent);
                    }
                }


            }
        });
    }



    public class LoadComments extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BusDetail.this);
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


