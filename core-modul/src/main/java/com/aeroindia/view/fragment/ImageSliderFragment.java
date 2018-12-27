package com.aeroindia.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeroindia.R;
import com.aeroindia.pojos.response.AnnouncementItemsModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import ir.apend.slider.ui.customUI.RoundedCornersTransformations;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageSliderFragment extends Fragment {

    private ImageView sliderImageIV;
    private TextView sliderTextTV;
    private AnnouncementItemsModel announcementItemsModel;
    private CardView cardViewAnnouncement;
    private WebView announcementWV;
    private int imageListPos = 0;

    public ImageSliderFragment() {
        // Required empty public constructor
    }

    public int getImageListPos() {
        return imageListPos;
    }

    public void setImageListPos(int imageListPos) {
        this.imageListPos = imageListPos;
    }

    public AnnouncementItemsModel getAnnouncementItemsModel() {
        return announcementItemsModel;
    }

    public void setAnnouncementItemsModel(AnnouncementItemsModel announcementItemsModel) {
        this.announcementItemsModel = announcementItemsModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);
        setupScreen(view);
        return view;
    }

    private void setupScreen(View view) {
        cardViewAnnouncement = (CardView) view.findViewById(R.id.cardViewAnnouncement);
        sliderImageIV = (ImageView) view.findViewById(R.id.sliderImageIV);


        if (imageListPos == 0) {
            sliderImageIV.setImageResource(R.drawable.banner_default);

        }else if(imageListPos!=0) {
            String imageUrl = announcementItemsModel.getFilePath();
            Log.d("image", imageUrl);

            loadImage(sliderImageIV, imageUrl);
        }
    }


    private void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()) // Bind it with the context of the actual view used
                    .load(url) // Load the image
                    .bitmapTransform(new CenterCrop(imageView.getContext()), new RoundedCornersTransformations(imageView.getContext(), 0, 0, RoundedCornersTransformations.CornerType.ALL))
                    .animate(R.anim.fade_in) // need to manually set the animation as bitmap cannot use cross fade
                    .into(imageView);
        }
    }

}
