package com.iprismtech.rawvana.fragments;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iprismtech.rawvana.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragAbout extends Fragment {
    private FragmentManager fragmentManager;
    private Context context;
    private ImageView ivYoutube,ivInstagram,ivFacebook,ivWebSite;
    private ImageView ivTouch_MOre,ivTouch_instagram,ivTouch_FaceBook,ivTouch_Support,ivTouch_twitter,ivTouch_website;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.frag_about, container, false);
        context=getActivity();
        fragmentManager=getFragmentManager();
        setUp(view);
        setOnClickeEvents();
        return  view;
    }

    private void setUp(View view) {
        try {
            ivYoutube=(ImageView)view.findViewById(R.id.ivYoutube);
            ivInstagram=(ImageView)view.findViewById(R.id.ivInstagram);
            ivFacebook=(ImageView)view.findViewById(R.id.ivFacebook);
            ivWebSite=(ImageView)view.findViewById(R.id.ivWebSite);
            //
            ivTouch_MOre=(ImageView)view.findViewById(R.id.ivTouchMOre);
            ivTouch_instagram=(ImageView)view.findViewById(R.id.ivTouch_instagram);
            ivTouch_FaceBook=(ImageView)view.findViewById(R.id.ivTouchFaceBook);
            ivTouch_Support=(ImageView)view.findViewById(R.id.ivSupport);
            ivTouch_twitter=(ImageView)view.findViewById(R.id.ivTouch_twitter);
            ivTouch_website=(ImageView)view.findViewById(R.id.ivTouch_website);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setOnClickeEvents() {
        try {
             ivYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/rawvana"));
                    startActivity(i);

                }
            });

            //
            ivTouch_instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/touchzenmedia"));
                    startActivity(i);
                }
            });



            ivTouch_twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.twitter.com/touchzenmedia"));
                    startActivity(i);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
