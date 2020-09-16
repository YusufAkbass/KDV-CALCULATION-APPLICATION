package com.example.kdvhesaplamaprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText editTutar,editKDV;
    private Button buttonYuzde1,buttonYuzde8,buttonYuzde18,button2,buttonbaslik;
    private TextView textViewKdvDahilorHaric,textViewislemtutari,textViewKdvtutari,textViewToplamTutar;
    private RadioGroup radioGroup;
    private  double tutar = 0.0;
    private double kdv =0.0;
    private boolean kdvdahil=true;
    private Animation ay,ya;
    private AdView banner1;
    private TextWatcher editTutardegisimler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                tutar=Double.parseDouble(s.toString());
            }catch (NumberFormatException e){
                tutar=0.0;
            }
            guncelle();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher editKDVdegisimler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                kdv=Double.parseDouble(s.toString());

            }catch (NumberFormatException e ){
                kdv=0.0;

            }
            guncelle();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private RadioGroup.OnCheckedChangeListener radioGroupdegisimler=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int i) {
            if (i == R.id.radioButtonKDVDahıl){
                kdvdahil=true;

            }
            else if (i==R.id.radioButtonKDVHarıc){
                kdvdahil=false;
            }
            guncelle();

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        banner1=findViewById(R.id.banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner1.loadAd(adRequest);

        editTutar=(EditText)findViewById(R.id.editTutar);
        editKDV=(EditText)findViewById(R.id.editKDV);
        buttonYuzde1=(Button)findViewById(R.id.buttonYuzde1);
        buttonYuzde8=(Button)findViewById(R.id.buttonYuzde8);
        buttonYuzde18=(Button)findViewById(R.id.buttonYuzde18);
        button2=(Button)findViewById(R.id.button2);
        buttonbaslik=(Button)findViewById(R.id.buttonBaslik);
        textViewKdvDahilorHaric=(TextView)findViewById(R.id.textViewKdvDahilorHaric);
        textViewislemtutari=(TextView)findViewById(R.id.textViewislemtutari);
        textViewKdvtutari=(TextView)findViewById(R.id.textViewKdvtutari);
        textViewToplamTutar=(TextView)findViewById(R.id.textViewToplamTutar);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        ya=(Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.yukaridanasagiyabaslik);
        ay=(Animation)AnimationUtils.loadAnimation(getApplicationContext(),R.anim.asagidanyukaributon);
        buttonbaslik.setAnimation(ya);
        button2.setAnimation(ay);

        editTutar.addTextChangedListener(editTutardegisimler);
        editKDV.addTextChangedListener(editKDVdegisimler);
        radioGroup.setOnCheckedChangeListener(radioGroupdegisimler);
        buttonYuzde1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(1));
            }
        });
        buttonYuzde8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(8));
            }
        });
        buttonYuzde18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(18));
            }
        });
        guncelle();

    }
    public void guncelle(){
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                DecimalFormat formatter = new DecimalFormat("###,###.##");

                double kdvdahilislemTutari = tutar / (1+kdv/100);
                double kdvdahilKdvTutari = tutar-kdvdahilislemTutari;

                double kdvharicKdvsi = tutar*(kdv)/100;
                double kdvharicToplamTutar = tutar+kdvharicKdvsi;


                if (kdvdahil){
                    textViewKdvDahilorHaric.setText("### KDV dahil ###");
                    textViewKdvDahilorHaric.setTextColor(Color.BLACK);
                    textViewKdvDahilorHaric.setBackgroundResource(R.color.yesil);
                    textViewKdvDahilorHaric.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    textViewToplamTutar.setText(formatter.format(tutar));
                    textViewislemtutari.setText(formatter.format(kdvdahilislemTutari));
                    textViewKdvtutari.setText(formatter.format(kdvdahilKdvTutari));
                }
                else{
                    textViewKdvDahilorHaric.setText("### KDV Hariç ###");
                    textViewKdvDahilorHaric.setTextColor(Color.BLACK);
                    textViewKdvDahilorHaric.setBackgroundResource(R.color.kirmizi);
                    textViewKdvDahilorHaric.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    textViewislemtutari.setText(formatter.format(tutar));
                    textViewKdvtutari.setText(formatter.format(kdvharicKdvsi));
                    textViewToplamTutar.setText(formatter.format(kdvharicToplamTutar));

                }
            }
        });
    }

}