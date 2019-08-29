package com.intellisyscorp.fitzme_android.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.intellisyscorp.fitzme_android.R;
import com.intellisyscorp.fitzme_android.models.GarmentVO;
import com.intellisyscorp.fitzme_android.models.OutfitVO;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CustomViewUtil {
    public static void bindOutfit(Context context, OutfitVO outfit, ConstraintLayout view) {
        ImageView ivTop = view.findViewById(R.id.iv_top);
        ImageView ivBottom = view.findViewById(R.id.iv_bottom);
        ImageView ivDress = view.findViewById(R.id.iv_dress);
        ImageView ivOuter = view.findViewById(R.id.iv_outer);
        ImageView ivShoes = view.findViewById(R.id.iv_shoes);

        // For ViewHolder of RecyclerView, clear image from ImageView
        ivTop.setImageDrawable(null);
        ivBottom.setImageDrawable(null);
        ivDress.setImageDrawable(null);
        ivOuter.setImageDrawable(null);
        ivShoes.setImageDrawable(null);

        GarmentVO top = outfit.getTop();
        GarmentVO bottom = outfit.getBottom();
        GarmentVO dress = outfit.getDress();
        GarmentVO outer = outfit.getOuter();
        GarmentVO shoes = outfit.getShoes();

        // Set images' size
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(view);

        if (dress != null) {
            switch (dress.getCategory()) {
                case "점프수트":
                    constraintSet.setGuidelinePercent(R.id.guide_dress_left, 0.13f);
                    constraintSet.setGuidelinePercent(R.id.guide_dress_right, 0.37f);
                    break;

                case "드레스":
                    constraintSet.setGuidelinePercent(R.id.guide_dress_left, 0.0875f);
                    constraintSet.setGuidelinePercent(R.id.guide_dress_right, 0.4125f);
                    break;

                default:
                    throw new AssertionError("Invalid category: " + dress.getCategory());
            }
        } else {
            constraintSet.setGuidelinePercent(R.id.guide_dress_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_dress_right, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_dress_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_dress_right, 0f);
        }

        if (dress == null && top != null) {
            // By default, top has fixed size
        } else {
            constraintSet.setGuidelinePercent(R.id.guide_top_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_top_right, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_top_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_top_right, 0f);
        }

        if (dress == null && bottom != null) {
            switch (bottom.getCategory()) {
                case "바지":
                case "청바지":
                case "레깅스":
                case "트레이닝팬츠":
                    constraintSet.setGuidelinePercent(R.id.guide_bottom_left, 0.1425f);
                    constraintSet.setGuidelinePercent(R.id.guide_bottom_right, 0.3575f);
                    break;

                case "반바지":
                    constraintSet.setGuidelinePercent(R.id.guide_bottom_left, 0.13f);
                    constraintSet.setGuidelinePercent(R.id.guide_bottom_right, 0.37f);
                    break;

                case "치마":
                    constraintSet.setGuidelinePercent(R.id.guide_bottom_left, 0.125f);
                    constraintSet.setGuidelinePercent(R.id.guide_bottom_right, 0.375f);
                    break;

                default:
                    throw new AssertionError("Invalid category: " + bottom.getCategory());
            }
        } else {
            constraintSet.setGuidelinePercent(R.id.guide_bottom_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_bottom_right, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_bottom_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_bottom_right, 0f);
        }

        boolean outfitIsLong = false;
        if (outer != null) {
            switch (outer.getCategory()) {
                case "가디건":
                case "블레이저":
                case "자켓":
                case "조끼":
                    constraintSet.setGuidelinePercent(R.id.guide_outer_top, 0.0375f);
                    constraintSet.setGuidelinePercent(R.id.guide_outer_bottom, 0.4625f);
                    constraintSet.setGuidelinePercent(R.id.guide_outer_left, 0.595f);
                    constraintSet.setGuidelinePercent(R.id.guide_outer_right, 0.905f);
                    break;

                case "코트":
                case "패딩/다운":
                    constraintSet.setGuidelinePercent(R.id.guide_outer_top, 0.09375f);
                    constraintSet.setGuidelinePercent(R.id.guide_outer_bottom, 0.65625f);
                    constraintSet.setGuidelinePercent(R.id.guide_outer_left, 0.575f);
                    constraintSet.setGuidelinePercent(R.id.guide_outer_right, 0.925f);
                    outfitIsLong = true;
                    break;

                default:
                    throw new AssertionError("Invalid category: " + outer.getCategory());
            }
        } else {
            constraintSet.setGuidelinePercent(R.id.guide_outer_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_outer_right, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_outer_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_outer_right, 0f);
        }

        if (shoes != null) {
            if (outfitIsLong) {
                constraintSet.setGuidelinePercent(R.id.guide_shoes_top, 0.7875f);
                constraintSet.setGuidelinePercent(R.id.guide_shoes_bottom, 0.9625f);
            } else {
                constraintSet.setGuidelinePercent(R.id.guide_shoes_top, 0.55f);
                constraintSet.setGuidelinePercent(R.id.guide_shoes_bottom, 0.95f);
            }
            constraintSet.setGuidelinePercent(R.id.guide_shoes_left, 0.65f);
            constraintSet.setGuidelinePercent(R.id.guide_shoes_right, 0.85f);
        } else {
            constraintSet.setGuidelinePercent(R.id.guide_shoes_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_shoes_right, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_shoes_left, 0f);
            constraintSet.setGuidelinePercent(R.id.guide_shoes_right, 0f);
        }

        constraintSet.applyTo(view);

        // Download images
        RequestOptions options = new RequestOptions();
        options.fitCenter();

        if (dress != null) {
            Glide
                    .with(context)
                    .load(dress.getImage())
                    .apply(options)
                    .transition(withCrossFade())
                    .into(ivDress);
        } else {
            if (top != null) {
                Glide
                        .with(context)
                        .load(top.getImage())
                        .apply(options)
                        .transition(withCrossFade())
                        .into(ivTop);
            }

            if (bottom != null) {
                Glide
                        .with(context)
                        .load(bottom.getImage())
                        .apply(options)
                        .transition(withCrossFade())
                        .into(ivBottom);
            }
        }

        if (outer != null) {
            Glide
                    .with(context)
                    .load(outer.getImage())
                    .apply(options)
                    .transition(withCrossFade())
                    .into(ivOuter);
        }

        if (shoes != null) {
            Glide
                    .with(context)
                    .load(shoes.getImage())
                    .apply(options)
                    .transition(withCrossFade())
                    .into(ivShoes);
        }
    }
}
