package com.intellisyscorp.fitzme_android.activtiy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.AutoLabelUISettings;
import com.intellisyscorp.fitzme_android.R;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity
        implements View.OnClickListener {

    private AutoLabelUI mAutoLabel;
    private EditText etLabelAdd;
    final static String TAG = "FilterActivity";
    TextView tvReset;
    TextView tvApply;
    ToggleButton btnBlouseShirt;
    ToggleButton btnTShirt;
    ToggleButton btnSweatshirt;
    ToggleButton btnSweater;
    ToggleButton btnJeans;
    ToggleButton btnJumpsuit;
    ToggleButton btnLeggings;
    ToggleButton btnHoodie;
    ToggleButton btnPants;
    ToggleButton btnSkirt;
    ToggleButton btnBlazer;
    ToggleButton btnVest;
    ToggleButton btnCoat;
    ToggleButton btnJacket;
    ToggleButton btnPufferdown;
    ToggleButton btnCardigan;
    ToggleButton btnDress;
    ToggleButton btnShoes;

    //color button
    ToggleButton btnRed;
    ToggleButton btnYellow;
    ToggleButton btnOrange;
    ToggleButton btnGreen;
    ToggleButton btnTurquoise;
    ToggleButton btnPink;
    ToggleButton btnGrey;
    ToggleButton btnWhite;
    ToggleButton btnBlack;
    ToggleButton btnPlum;
    ToggleButton btnPurple;
    ToggleButton btnBlue;
    ToggleButton btnLime;
    ToggleButton btnNavy;
    ToggleButton btnBrown;
    ToggleButton btnSleeveless;
    ToggleButton btnShorts;
    ToggleButton btnSweatpants;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setTitleTextColor(Color.BLACK);
        mToolbar.setTitle("필터");
        initUI();
        setListeners();
        setAutoLabelUISettings();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

//        ToggleButton btnShoes;

        tvReset = findViewById(R.id.tv_reset);
        tvApply = findViewById(R.id.tv_apply);
        mAutoLabel = findViewById(R.id.label_view);
        etLabelAdd = findViewById(R.id.etLabel);
//        btnFinish = findViewById(R.id.btn_finish);
        btnBlouseShirt = findViewById(R.id.btn_blouse_shirt);
        btnTShirt = findViewById(R.id.btn_t_shirt);
        btnSweatshirt = findViewById(R.id.btn_sweatshirt);
        btnSweater = findViewById(R.id.btn_sweater);
        btnJeans = findViewById(R.id.btn_jeans);
        btnJumpsuit = findViewById(R.id.btn_jumpsuit);
        btnLeggings = findViewById(R.id.btn_leggings);
        btnPants = findViewById(R.id.btn_pants);
        btnSkirt = findViewById(R.id.btn_skirt);
        btnBlazer = findViewById(R.id.btn_blazer);
        btnVest = findViewById(R.id.btn_vest);
        btnCoat = findViewById(R.id.btn_coat);
        btnJacket = findViewById(R.id.btn_jacket);
        btnPufferdown = findViewById(R.id.btn_pufferdown);
        btnCardigan = findViewById(R.id.btn_cardigan);
        btnDress = findViewById(R.id.btn_dress);
        btnShoes = findViewById(R.id.btn_shoes);
        btnHoodie = findViewById(R.id.btn_hoodie);
        btnSleeveless = findViewById(R.id.btn_sleeveless);
        btnShorts = findViewById(R.id.btn_shorts);
        btnSweatpants = findViewById(R.id.btn_sweatpants);


        btnRed = findViewById(R.id.btn_red);
        btnYellow = findViewById(R.id.btn_yellow);
        btnOrange = findViewById(R.id.btn_orange);
        btnGreen = findViewById(R.id.btn_green);
        btnTurquoise = findViewById(R.id.btn_turquoise);
        btnPink = findViewById(R.id.btn_pink);
        btnGrey = findViewById(R.id.btn_grey);
        btnWhite = findViewById(R.id.btn_white);
        btnBlack = findViewById(R.id.btn_black);
        btnPlum = findViewById(R.id.btn_plum);
        btnPurple = findViewById(R.id.btn_purple);
        btnBlue = findViewById(R.id.btn_blue);
        btnLime = findViewById(R.id.btn_lime);
        btnNavy = findViewById(R.id.btn_navy);
        btnBrown = findViewById(R.id.btn_brown);


        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
        btnOrange.setOnClickListener(this);
        btnGreen.setOnClickListener(this);
        btnTurquoise.setOnClickListener(this);
        btnPink.setOnClickListener(this);
        btnGrey.setOnClickListener(this);
        btnWhite.setOnClickListener(this);
        btnBlack.setOnClickListener(this);
        btnPlum.setOnClickListener(this);
        btnPurple.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        btnLime.setOnClickListener(this);
        btnNavy.setOnClickListener(this);
        btnBrown.setOnClickListener(this);


        tvApply.setOnClickListener(this);
        tvReset.setOnClickListener(this);
        btnBlouseShirt.setOnClickListener(this);
        btnTShirt.setOnClickListener(this);
        btnSweatshirt.setOnClickListener(this);
        btnSweater.setOnClickListener(this);
        btnJeans.setOnClickListener(this);
        btnJumpsuit.setOnClickListener(this);
        btnLeggings.setOnClickListener(this);
        btnPants.setOnClickListener(this);
        btnSkirt.setOnClickListener(this);
        btnBlazer.setOnClickListener(this);
        btnVest.setOnClickListener(this);
        btnCoat.setOnClickListener(this);
        btnJacket.setOnClickListener(this);
        btnPufferdown.setOnClickListener(this);
        btnCardigan.setOnClickListener(this);
        btnDress.setOnClickListener(this);
        btnShoes.setOnClickListener(this);
        btnHoodie.setOnClickListener(this);
        btnSleeveless.setOnClickListener(this);
        btnShorts.setOnClickListener(this);
        btnSweatpants.setOnClickListener(this);


        Intent intent = getIntent();
        ArrayList<String> filterList = intent.getStringArrayListExtra("filter_list");
        ArrayList<String> filterCategory = intent.getStringArrayListExtra("filter_category");
        ArrayList<String> filterKey = intent.getStringArrayListExtra("filter_key");
        ArrayList<String> filterColor = intent.getStringArrayListExtra("filter_color");

        for (String filter : filterCategory) {
            if ("BlouseShirt".equals(filter)) {
                setButtonState(btnBlouseShirt, true);

            } else if ("T-Shirt".equals(filter)) {
                setButtonState(btnTShirt, true);

            } else if ("Sweatshirt".equals(filter)) {
                setButtonState(btnSweatshirt, true);


            } else if ("Sweater".equals(filter)) {
                setButtonState(btnSweater, true);

            } else if ("Jeans".equals(filter)) {
                setButtonState(btnJeans, true);

            } else if ("Jumpsuit".equals(filter)) {
                setButtonState(btnJumpsuit, true);

            } else if ("Leggings".equals(filter)) {
                setButtonState(btnLeggings, true);

            } else if ("Pants".equals(filter)) {
                setButtonState(btnPants, true);

            } else if ("Skirt".equals(filter)) {
                setButtonState(btnSkirt, true);

            } else if ("Blazer".equals(filter)) {
                setButtonState(btnBlazer, true);

            } else if ("Vest".equals(filter)) {
                setButtonState(btnVest, true);

            } else if ("Coat".equals(filter)) {
                setButtonState(btnCoat, true);

            } else if ("Jacket".equals(filter)) {
                setButtonState(btnJacket, true);

            } else if ("Pufferdown".equals(filter)) {
                setButtonState(btnPufferdown, true);

            } else if ("Cardigan".equals(filter)) {
                setButtonState(btnCardigan, true);

            } else if ("Dress".equals(filter)) {
                setButtonState(btnDress, true);

            } else if ("Shoes".equals(filter)) {
                setButtonState(btnShoes, true);

            } else if ("Hoodie".equals(filter)) {
                setButtonState(btnHoodie, true);

            } else if ("Sleeveless".equals(filter)) {
                setButtonState(btnSleeveless, true);

            } else if ("Shorts".equals(filter)) {
                setButtonState(btnShorts, true);

            } else if ("Sweatpants".equals(filter)) {
                setButtonState(btnSweatpants, true);

            }
        }

        for (String color : filterColor) {
            if ("red".equals(color)) {
                setColorButtonState(btnRed, true);

            } else if ("yellow".equals(color)) {
                setColorButtonState(btnYellow, true);

            } else if ("orange".equals(color)) {

            } else if ("green".equals(color)) {
                setColorButtonState(btnGreen, true);

            } else if ("turquoise".equals(color)) {
                setColorButtonState(btnTurquoise, true);

            } else if ("pink".equals(color)) {
                setColorButtonState(btnPink, true);

            } else if ("grey".equals(color)) {
                setColorButtonState(btnGrey, true);

            } else if ("white".equals(color)) {
                setColorButtonState(btnWhite, true);

            } else if ("black".equals(color)) {
                setColorButtonState(btnBlack, true);

            } else if ("plum".equals(color)) {
                setColorButtonState(btnPlum, true);

            } else if ("purple".equals(color)) {
                setColorButtonState(btnPurple, true);

            } else if ("blue".equals(color)) {
                setColorButtonState(btnBlue, true);

            } else if ("lime".equals(color)) {
                setColorButtonState(btnLime, true);

            } else if ("navy".equals(color)) {
                setColorButtonState(btnNavy, true);

            } else if ("brown".equals(color)) {
                setColorButtonState(btnBrown, true);

            }
        }
    }

    private void setAutoLabelUISettings() {
        AutoLabelUISettings autoLabelUISettings =
                new AutoLabelUISettings.Builder()
                        .withBackgroundResource(R.color.default_background_label)
                        .withIconCross(R.drawable.cross)
                        .withMaxLabels(6)
                        .withShowCross(true)
                        .withLabelsClickables(true)
                        .withTextColor(android.R.color.white)
                        .withTextSize(R.dimen.label_title_size)
                        .withLabelPadding(30)
                        .build();

        mAutoLabel.setSettings(autoLabelUISettings);
    }

    private void setListeners() {
        mAutoLabel.setOnLabelsCompletedListener(new AutoLabelUI.OnLabelsCompletedListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onLabelsCompleted() {

            }
        });

        mAutoLabel.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {

            }
        });

        mAutoLabel.setOnLabelsEmptyListener(new AutoLabelUI.OnLabelsEmptyListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onLabelsEmpty() {
//                Snackbar.make(getView(), "EMPTY!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAutoLabel.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(View v) {

            }

        });
    }

    private void initUI() {
        mAutoLabel = findViewById(R.id.label_view);
        etLabelAdd = findViewById(R.id.etLabel);

        Button btAddLabel = findViewById(R.id.btAddLabel);
        btAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToAdd = etLabelAdd.getText().toString();
                if (!textToAdd.isEmpty()) {
                    boolean success = mAutoLabel.addLabel(textToAdd);
                    if (success) {
                        Toast.makeText(FilterActivity.this, "Label added!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(FilterActivity.this, "ERROR! Label not adde", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }


    private void setButtonState(ToggleButton btn, boolean isChecked) {
        btn.setChecked(isChecked);
        if (isChecked) {
            btn.setBackgroundResource(R.color.maincolor);
        } else {
            btn.setBackgroundResource(R.color.white);
        }
    }


    private void setColorButtonState(ToggleButton btn, boolean isChecked) {
        btn.setChecked(isChecked);
        if (isChecked) {
            btn.setTextOn("✓");
        } else {
            btn.setTextOff("");
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.btn_finish:
//                finish();
//                break;


            case R.id.btn_blouse_shirt:
                if (btnBlouseShirt.isChecked()) {
                    btnBlouseShirt.setBackgroundResource(R.color.maincolor);
                } else {
                    btnBlouseShirt.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_t_shirt:
                if (btnTShirt.isChecked()) {
                    btnTShirt.setBackgroundResource(R.color.maincolor);
                } else {
                    btnTShirt.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_sweatshirt:
                if (btnSweatshirt.isChecked()) {
                    btnSweatshirt.setBackgroundResource(R.color.maincolor);
                } else {
                    btnSweatshirt.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_sweater:
                if (btnSweater.isChecked()) {
                    btnSweater.setBackgroundResource(R.color.maincolor);
                } else {
                    btnSweater.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_jeans:
                if (btnJeans.isChecked()) {
                    btnJeans.setBackgroundResource(R.color.maincolor);
                } else {
                    btnJeans.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_jumpsuit:
                if (btnJumpsuit.isChecked()) {
                    btnJumpsuit.setBackgroundResource(R.color.maincolor);
                } else {
                    btnJumpsuit.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_leggings:
                if (btnLeggings.isChecked()) {
                    btnLeggings.setBackgroundResource(R.color.maincolor);
                } else {
                    btnLeggings.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_pants:
                if (btnPants.isChecked()) {
                    btnPants.setBackgroundResource(R.color.maincolor);
                } else {
                    btnPants.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_skirt:
                if (btnSkirt.isChecked()) {
                    btnSkirt.setBackgroundResource(R.color.maincolor);
                } else {
                    btnSkirt.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_blazer:
                if (btnBlazer.isChecked()) {
                    btnBlazer.setBackgroundResource(R.color.maincolor);
                } else {
                    btnBlazer.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_vest:
                if (btnVest.isChecked()) {
                    btnVest.setBackgroundResource(R.color.maincolor);
                } else {
                    btnVest.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_coat:
                if (btnCoat.isChecked()) {
                    btnCoat.setBackgroundResource(R.color.maincolor);
                } else {
                    btnCoat.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_jacket:
                if (btnJacket.isChecked()) {
                    btnJacket.setBackgroundResource(R.color.maincolor);
                } else {
                    btnJacket.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_pufferdown:
                if (btnPufferdown.isChecked()) {
                    btnPufferdown.setBackgroundResource(R.color.maincolor);
                } else {
                    btnPufferdown.setBackgroundResource(R.color.white);
                }
                break;
            case R.id.btn_cardigan:
                if (btnCardigan.isChecked()) {
                    btnCardigan.setBackgroundResource(R.color.maincolor);
                } else {
                    btnCardigan.setBackgroundResource(R.color.white);
                }
                break;


            case R.id.btn_dress:
                if (btnDress.isChecked()) {
                    btnDress.setBackgroundResource(R.color.maincolor);
                } else {
                    btnDress.setBackgroundResource(R.color.white);
                }
                break;


            case R.id.btn_shoes:
                if (btnShoes.isChecked()) {
                    btnShoes.setBackgroundResource(R.color.maincolor);
                } else {
                    btnShoes.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_hoodie:
                if (btnHoodie.isChecked()) {
                    btnHoodie.setBackgroundResource(R.color.maincolor);
                } else {
                    btnHoodie.setBackgroundResource(R.color.white);
                }
                break;
            case R.id.btn_sleeveless:
                if (btnSleeveless.isChecked()) {
                    btnSleeveless.setBackgroundResource(R.color.maincolor);
                } else {
                    btnSleeveless.setBackgroundResource(R.color.white);
                }
                break;

            case R.id.btn_shorts:
                if (btnShorts.isChecked()) {
                    btnShorts.setBackgroundResource(R.color.maincolor);
                } else {
                    btnShorts.setBackgroundResource(R.color.white);
                }
                break;


            case R.id.btn_sweatpants:
                if (btnSweatpants.isChecked()) {
                    btnSweatpants.setBackgroundResource(R.color.maincolor);
                } else {
                    btnSweatpants.setBackgroundResource(R.color.white);
                }
                break;


            case R.id.tv_reset:
                setButtonState(btnBlouseShirt, false);
                btnBlouseShirt.setBackgroundResource(R.color.white);

                setButtonState(btnTShirt, false);
                btnTShirt.setBackgroundResource(R.color.white);

                setButtonState(btnPants, false);
                btnPants.setBackgroundResource(R.color.white);

                setButtonState(btnSweatshirt, false);
                btnSweatshirt.setBackgroundResource(R.color.white);

                setButtonState(btnSweater, false);
                btnSweater.setBackgroundResource(R.color.white);

                setButtonState(btnJeans, false);
                btnJeans.setBackgroundResource(R.color.white);


                setButtonState(btnJumpsuit, false);
                btnJumpsuit.setBackgroundResource(R.color.white);

                setButtonState(btnLeggings, false);
                btnLeggings.setBackgroundResource(R.color.white);

                setButtonState(btnSkirt, false);
                btnSkirt.setBackgroundResource(R.color.white);

                setButtonState(btnBlazer, false);
                btnBlazer.setBackgroundResource(R.color.white);

                setButtonState(btnVest, false);
                btnVest.setBackgroundResource(R.color.white);


                setButtonState(btnCoat, false);
                btnCoat.setBackgroundResource(R.color.white);

                setButtonState(btnJacket, false);
                btnJacket.setBackgroundResource(R.color.white);

                setButtonState(btnPufferdown, false);
                btnPufferdown.setBackgroundResource(R.color.white);

                setButtonState(btnCardigan, false);
                btnCardigan.setBackgroundResource(R.color.white);

                setButtonState(btnDress, false);
                btnDress.setBackgroundResource(R.color.white);

                setButtonState(btnShoes, false);
                btnShoes.setBackgroundResource(R.color.white);


                setButtonState(btnHoodie, false);
                btnHoodie.setBackgroundResource(R.color.white);


                setButtonState(btnSleeveless, false);
                btnSleeveless.setBackgroundResource(R.color.white);

                setButtonState(btnShorts, false);
                btnShorts.setBackgroundResource(R.color.white);

                setButtonState(btnSweatpants, false);
                btnSweatpants.setBackgroundResource(R.color.white);


                break;

            case R.id.tv_apply:
                ArrayList<String> filterCategory = new ArrayList<>();
                ArrayList<String> filterColor = new ArrayList<>();

                if (btnBlouseShirt.isChecked()) {
                    filterCategory.add(btnBlouseShirt.getTag().toString());
                }
                if (btnHoodie.isChecked()) {
                    filterCategory.add(btnHoodie.getTag().toString());
                }
                if (btnSleeveless.isChecked()) {
                    filterCategory.add(btnSleeveless.getTag().toString());
                }
                if (btnShorts.isChecked()) {
                    filterCategory.add(btnShorts.getTag().toString());
                }
                if (btnSweatpants.isChecked()) {
                    filterCategory.add(btnSweatpants.getTag().toString());
                }
                if (btnPufferdown.isChecked()) {
                    filterCategory.add(btnPufferdown.getTag().toString());
                }
                if (btnTShirt.isChecked()) {
                    filterCategory.add(btnTShirt.getTag().toString());
                }
                if (btnSweatshirt.isChecked()) {
                    filterCategory.add(btnSweatshirt.getTag().toString());
                }
                if (btnSweater.isChecked()) {
                    filterCategory.add(btnSweater.getTag().toString());
                }
                if (btnJeans.isChecked()) {
                    filterCategory.add(btnJeans.getTag().toString());
                }
                if (btnJumpsuit.isChecked()) {
                    filterCategory.add(btnJumpsuit.getTag().toString());
                }
                if (btnLeggings.isChecked()) {
                    filterCategory.add(btnLeggings.getTag().toString());
                }
                if (btnPants.isChecked()) {
                    filterCategory.add(btnPants.getTag().toString());
                }
                if (btnSkirt.isChecked()) {
                    filterCategory.add(btnSkirt.getTag().toString());
                }
                if (btnBlazer.isChecked()) {
                    filterCategory.add(btnBlazer.getTag().toString());
                }
                if (btnVest.isChecked()) {
                    filterCategory.add(btnVest.getTag().toString());
                }
                if (btnCoat.isChecked()) {
                    filterCategory.add(btnCoat.getTag().toString());
                }
                if (btnJacket.isChecked()) {
                    filterCategory.add(btnJacket.getTag().toString());
                }

                if (btnCardigan.isChecked()) {
                    filterCategory.add(btnCardigan.getTag().toString());
                }
                if (btnDress.isChecked()) {
                    filterCategory.add(btnDress.getTag().toString());
                }
                if (btnShoes.isChecked()) {
                    filterCategory.add(btnShoes.getTag().toString());
                }
                if (btnRed.isChecked()) {
                    filterColor.add(btnRed.getTag().toString());
                }
                if (btnYellow.isChecked()) {
                    filterColor.add(btnYellow.getTag().toString());
                }
                if (btnOrange.isChecked()) {
                    filterColor.add(btnOrange.getTag().toString());
                }
                if (btnGreen.isChecked()) {
                    filterColor.add(btnGreen.getTag().toString());
                }
                if (btnTurquoise.isChecked()) {
                    filterColor.add(btnTurquoise.getTag().toString());
                }
                if (btnPink.isChecked()) {
                    filterColor.add(btnPink.getTag().toString());
                }
                if (btnGrey.isChecked()) {
                    filterColor.add(btnGrey.getTag().toString());
                }
                if (btnWhite.isChecked()) {
                    filterColor.add(btnWhite.getTag().toString());
                }
                if (btnBlack.isChecked()) {
                    filterColor.add(btnBlack.getTag().toString());
                }
                if (btnPurple.isChecked()) {
                    filterColor.add(btnPurple.getTag().toString());
                }
                if (btnPlum.isChecked()) {
                    filterColor.add(btnPlum.getTag().toString());
                }
                if (btnBlue.isChecked()) {
                    filterColor.add(btnBlue.getTag().toString());
                }
                if (btnLime.isChecked()) {
                    filterColor.add(btnLime.getTag().toString());
                }
                if (btnNavy.isChecked()) {
                    filterColor.add(btnNavy.getTag().toString());
                }
                if (btnBrown.isChecked()) {
                    filterColor.add(btnBrown.getTag().toString());
                }

                Intent intent = new Intent();
                intent.putStringArrayListExtra("filter_category", filterCategory);
                intent.putStringArrayListExtra("filter_color", filterColor);
                setResult(RESULT_OK, intent);
                finish();
                break;

        }

    }
}
