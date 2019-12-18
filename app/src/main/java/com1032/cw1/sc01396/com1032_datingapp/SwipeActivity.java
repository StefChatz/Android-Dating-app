package com1032.cw1.sc01396.com1032_datingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 *@author Stefanos Chatzakis
 */
public class SwipeActivity extends AppCompatActivity {

    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> array;
    private SwipeFlingAdapterView flingContainer;

/*
 * The onCreate method of the class, this is where the predefined users are customized as well.
 * @param savedInstanceState.
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        Intent intent = getIntent();
        Map myMap = (HashMap)intent.getSerializableExtra("map");
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        array = new ArrayList<>();
        if(myMap.containsKey(1)) {
            array.add(new Data(R.drawable.user_female, "Hi I am " + myMap.get(1)));
        }
        if(myMap.containsKey(2)) {
            array.add(new Data(R.drawable.user_male, "Hi I am " + myMap.get(2)));
        }
        if(myMap.containsKey(3)) {
            array.add(new Data(R.drawable.user_female, "Hi I am " + myMap.get(3)));
        }
        if(myMap.containsKey(4)) {

            array.add(new Data(R.drawable.user_female, "Hi I am " + myMap.get(4)));
        }
        if(myMap.containsKey(5)) {

            array.add(new Data(R.drawable.user_female, "Hi I am " + myMap.get(5)));
        }
        if(myMap.containsKey(6)) {

            array.add(new Data(R.drawable.user_male, "Hi I am " + myMap.get(6)));
        }
        if(myMap.containsKey(7)) {

            array.add(new Data(R.drawable.user_female, "Hi I am " + myMap.get(7)));
        }
        if(myMap.containsKey(8)) {

            array.add(new Data(R.drawable.user_male, "Hi I am " + myMap.get(8)));
        }
        if(myMap.containsKey(9)) {

            array.add(new Data(R.drawable.user_male, "Hi I am " + myMap.get(9)));
        }
        if(myMap.containsKey(10)) {

            array.add(new Data(R.drawable.user_female, "Hi I am " + myMap.get(10)));
        }
        myAppAdapter = new MyAppAdapter(array, SwipeActivity.this);
        flingContainer.setAdapter(myAppAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            /*
             * On the swipe of the card to the left, this method is triggered.
             * @param dataObject.
             */

            @Override
            public void onLeftCardExit(Object dataObject) {
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            /*
             * On the swipe of the card to the right, this method is triggered.
             * @param dataObject.
             */

            @Override
            public void onRightCardExit(Object dataObject) {
                array.remove(0);
                myAppAdapter.notifyDataSetChanged();
            }

            /*
             * Empty method, not used.
             * @param itemsInAdapter.
             */

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            /*
             * An onScroll method for the swipe cards.
             */

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });
    }
    /*
     * A class that initializes the information in a user's card.
     */
    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public ImageView cardImage;


    }
    /*
     * An array adapter class that holds the information of the user and sets the card layout of a
     * user.
     */
    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        /*
         * A getter for the count.
         * @return int.
         */

        @Override
        public int getCount() {
            return parkingList.size();

        }

        /*
         * A getter for the item.
         * @return Object.
         * @param position.
         */

        @Override
        public Object getItem(int position) {
            return position;
        }

        /*
         * A getter for the Id.
         * @param position
         * @return long.
         */

        @Override
        public long getItemId(int position) {
            return position;
        }

        /*
         * A getter for the View.
         * @param position, convertView, parent.
         * @return view.
         */

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            Glide.with(SwipeActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
        }
    }
}