package com.example.kuet.traveldirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserInterface extends Activity
{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    String departure_place[]={"Dhaka","Khulna","Rajshahi","Chittagong","Sylhet","Barisal"};
    String destination_name[]={"Dhaka","Khulna","Rajshahi","Chittagong","Sylhet","Barisal"};
    //String root_name[]={};


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_interface);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {

                if(groupPosition==0)
                {
                    if(childPosition == 0)
                    {
                        Intent intent = new Intent(UserInterface.this, BusDetail.class);
                        intent.putExtra("root_name","1001");
                        startActivity(intent);
                    }
                    if(childPosition == 1)
                    {
                        Intent intent = new Intent(UserInterface.this, BusDetail.class);
                        intent.putExtra("root_name","1002");
                        startActivity(intent);
                    }
                    if(childPosition == 2)
                    {
                        Intent intent = new Intent(UserInterface.this, BusDetail.class);
                        intent.putExtra("root_name","1003");
                        startActivity(intent);
                    }
                    if(childPosition == 3)
                    {
                        Intent intent = new Intent(UserInterface.this, BusDetail.class);
                        intent.putExtra("root_name","1004");
                        startActivity(intent);
                    }
                    if(childPosition == 4)
                    {
                        Intent intent = new Intent(UserInterface.this, BusDetail.class);
                        intent.putExtra("root_name","1005");
                        startActivity(intent);
                    }
                }
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData()
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        for(int i=0;i<departure_place.length;i++)
        {
            listDataHeader.add(departure_place[i]);
        }

        // Adding child's child data


        for(int j=0;j<departure_place.length;j++)
        {
            List<String> departure = new ArrayList<String>();
            for (int i=0;i<destination_name.length;i++)
            {

               if(departure_place[j]!=destination_name[i])
                {
                    departure.add(destination_name[i]);
                }

            }
            listDataChild.put(listDataHeader.get(j), departure);
            departure=null;
        }

        //listDataChild.put(listDataHeader.get(0), dhaka);// Header, Child data
        //listDataChild.put(listDataHeader.get(1), khulna);
        //listDataChild.put(listDataHeader.get(2), rajshahi);
    }

}
