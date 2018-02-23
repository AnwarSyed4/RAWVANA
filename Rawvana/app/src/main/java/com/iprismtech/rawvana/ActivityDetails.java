package com.iprismtech.rawvana;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iprismtech.rawvana.database.DataBase;
import com.iprismtech.rawvana.others.HelperObj;
import com.iprismtech.rawvana.others.Values;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

public class ActivityDetails extends FragmentActivity {
    private Context context;
    private FragmentManager fragmentManager;
    private LinearLayout llListIngredients,llListMethod;
    private ImageView ivBack,ivLike,ivItemImage,ivReview,ivFacebook,ivGmail;
    private TextView tvItemName,tvRecipeName,tvDescription;
    private Button btnAdd;
    private JSONArray JsonArrayIngredients=null;
    private JSONArray JsonArrayMethods=null;
    private String Id="";
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setUP();
        onClickEvents();
    }
    private void setUP() {
        try {
            context=getApplicationContext();
            fragmentManager=getSupportFragmentManager();
            //
            ivReview=(ImageView)findViewById(R.id.ivReview);
            ivFacebook=(ImageView)findViewById(R.id.ivFacebook);
            ivGmail=(ImageView)findViewById(R.id.ivGmail);
            ivBack=(ImageView)findViewById(R.id.ivBack);
            ivLike=(ImageView)findViewById(R.id.ivLike);
            ivItemImage=(ImageView)findViewById(R.id.ivItemImage);
            tvItemName=(TextView)findViewById(R.id.tvItemName);
            tvRecipeName=(TextView)findViewById(R.id.tvRecipeName);
            tvDescription=(TextView)findViewById(R.id.tvDescription);
            btnAdd=(Button)findViewById(R.id.btnAdd);
            llListIngredients=(LinearLayout) findViewById(R.id.llListIngredients);
            llListMethod=(LinearLayout) findViewById(R.id.llListMethod);
            Intent intent = getIntent();
            try {
                Id=intent.getExtras().getString("id");
                tvItemName.setText(intent.getExtras().getString("Name"));
                String type=intent.getExtras().getString("Type");
                tvRecipeName.setText(type);
                tvDescription.setText(intent.getExtras().getString("description"));

                String jsonArray = intent.getStringExtra("Ingredients");
                String jsonArray2 = intent.getStringExtra("Method");

                try {
                    JsonArrayIngredients = new JSONArray(jsonArray);
                    JsonArrayMethods= new JSONArray(jsonArray2);
                    setLlListIngredients();
                    setLlListMethods();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String image=intent.getExtras().getString("Image");
                InputStream ims = getResources().getAssets().open(image);
                Drawable d = Drawable.createFromStream(ims, null);
                ivItemImage.setImageDrawable(d);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //
            DataBase dataBase=new DataBase(context);
            dataBase.open();
//            Boolean isExist=dataBase.CheckProductExist(Id);
//            if(isExist){
//                ivLike.setImageResource(R.drawable.fav_yes);
//            }else {
//                ivLike.setImageResource(R.drawable.like);
//            }

            Boolean isExistCart=dataBase.CheckProductExistCart(Id);
            if(isExistCart){
                btnAdd.setText("Remove From Shopping List");
                btnAdd.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else {
                btnAdd.setText("Add To Shopping List");
            }
            dataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void onClickEvents() {
        try {

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    long result=0;
                    DataBase dataBase=new DataBase(context);
                    dataBase.open();
                    Boolean isExistCart=dataBase.CheckProductExistCart(Id);
                    if(isExistCart){
                        dataBase.DeleteCartTableRow(Id);
                        btnAdd.setText("Add To Shopping List");
                    }else {
                        String Ingredients=getSelectedItems1();
                        if(Ingredients.isEmpty()){

                        }else {
                            result = dataBase.CartTable(Id,intent.getExtras().getString("Name"),Ingredients);
                            if(result > 0){
                                HelperObj.getInstance().cusToast(context," added to Shopping List");
                            } else {
                                HelperObj.getInstance().cusToast(context,"Something went wrong, Try again.");
                            }
                        }
                    }
                    dataBase.close();
                }

            });
            ivReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Toast.makeText(context,"Coming Soon",Toast.LENGTH_LONG).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setLlListIngredients(){
        try {
            llListIngredients.removeAllViews();
            if(JsonArrayIngredients != null && JsonArrayIngredients.length() > 0){
                for(int i = 0;i < JsonArrayIngredients.length();i++){
                    llListIngredients.addView(getListItemIngredients(i,JsonArrayIngredients.getJSONObject(i)));
                }
                llListIngredients.setVisibility(View.VISIBLE);

            } else {
                llListIngredients.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint({"InflateParams", "SetTextI18n"})
    public View getListItemIngredients(final Integer position, final JSONObject jsonObject){
        View rowView = null;
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            rowView = inflater.inflate(R.layout.item_ingredients_list, null);
            //
            TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
            tvName.setText(jsonObject.optString("steps"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowView;
    }
    public void setLlListMethods(){
        try {
            llListMethod.removeAllViews();
            if(JsonArrayMethods != null && JsonArrayMethods.length() > 0){
                for(int i = 0;i < JsonArrayIngredients.length();i++){
                    llListMethod.addView(getListItemMethods(i,JsonArrayMethods.getJSONObject(i)));
                }
                llListMethod.setVisibility(View.VISIBLE);
            } else {
                llListMethod.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getSelectedItems1() {

        String selectedItemsStrings = "";
        try {
            for (int i = 0; i < JsonArrayIngredients.length(); i++) {
                    selectedItemsStrings += JsonArrayIngredients.getJSONObject(i).optString("steps") + "--";
            }
            selectedItemsStrings = selectedItemsStrings.substring(0, selectedItemsStrings.length() - 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedItemsStrings;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    public View getListItemMethods(final Integer position, final JSONObject jsonObject){
        View rowView = null;
        int i=0;
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            rowView = inflater.inflate(R.layout.item_method_list, null);
            //
            i=position+1;
            TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
            TextView tvStepNo= (TextView) rowView.findViewById(R.id.tvStepNo);
            tvName.setText(jsonObject.optString("steps"));
            tvStepNo.setText("Step"+i);
            //

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowView;
    }
    @Override
    public void onBackPressed() {
//        if(Values.isWhichFrag.equals(Values.FragFavorites))
//        {
//            //HelperObj.getInstance().getActivityMain().FavoritesList();
//            HelperObj.getInstance().getActivityMain().ChangFragment(Values.FragFavorites,null);
//           // finish();
//        }else {
//            super.onBackPressed();
//            finish();
//        }
        super.onBackPressed();
        finish();

    }

}
