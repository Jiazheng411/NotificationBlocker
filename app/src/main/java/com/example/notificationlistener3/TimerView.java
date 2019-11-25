package com.example.notificationlistener3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Locale;

import androidx.core.content.res.ResourcesCompat;

// Frame Layout is needed if you need to emmmm... fade in fade out two different layouts for a single object
// Remember, reset(study period, rest period) is in millisecond

public class TimerView extends FrameLayout {

    // only one instance will be provided
    private static CountDownTimer event;

    private static TimerView timerView;

    private static LinearLayout viewStudy;
    private static LinearLayout viewStudyVertical;
    private static LinearLayout viewRest;

    private static TextClock clock;

    private static TextView studyCounter, restCounter, studyTextI, studyTextII, restTextI;

    private static long startTime;

    private static int studyPeriod, restPeriod, textColor;

    // Useless constructor
    private TimerView(Context context){
        super(context);
    }

    // Useful get instance
    public static TimerView getInstance(Context context){
        if(timerView == null){
            // Everything starts here
            timerView = new TimerView(context);
            viewStudy = new LinearLayout(context);
            viewStudyVertical = new LinearLayout(context);
            viewRest = new LinearLayout(context);
            clock = new TextClock(context);
            clock.setFormat24Hour("hh:mm");
            clock.setFormat12Hour("kk:mm");
            textColor = 0xBFEFEFEF;

            // By default we set study period to 45 and rest period to 15
            studyPeriod = 2700000;
            restPeriod = 900000;

            // Start building the layout
            // clock first
            clock.setTextSize(150.0f);
            clock.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            clock.setTypeface(ResourcesCompat.getFont(context, R.font.calibril));
            clock.setTextColor(textColor);

            // Build text view for rest and study
            studyCounter = new TextView(context);
            restCounter = new TextView(context);
            studyTextI = new TextView(context);
            studyTextII = new TextView(context);
            restTextI = new TextView(context);

            // NAN
            studyCounter.setTextSize(25.0f);
            restCounter.setTextSize(50.0f);
            studyTextI.setTextSize(50.0f);
            studyTextII.setTextSize(25.0f);
            restTextI.setTextSize(50.0f);

            // Five font settings nah NAN
            studyCounter.setTypeface(ResourcesCompat.getFont(context, R.font.calibril));
            restCounter.setTypeface(ResourcesCompat.getFont(context, R.font.calibril));
            studyTextI.setTypeface(ResourcesCompat.getFont(context, R.font.calibril));
            studyTextII.setTypeface(ResourcesCompat.getFont(context, R.font.calibril));
            restTextI.setTypeface(ResourcesCompat.getFont(context, R.font.calibril));

            // Coloring
            studyCounter.setTextColor(textColor);
            restCounter.setTextColor(textColor);
            studyTextI.setTextColor(textColor);
            studyTextII.setTextColor(textColor);
            restTextI.setTextColor(textColor);

            // Text alignment
            studyCounter.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            restCounter.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            studyTextI.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            studyTextII.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            restTextI.setTextAlignment(TEXT_ALIGNMENT_CENTER);

            // Nah. NAN troublesome
            // Add to strings.xml:
            // <string name="UNTIL"><u>UNTIL</u></string>
            // <string name="NEXTBREAK">NEXT BREAK</string>
            // <string name="NEXTSTUDYPERIOD"><u>NEXT STUDY PERIOD</u></string>
            studyTextI.setText(R.string.UNTIL);
            studyTextII.setText(R.string.NEXTBREAK);
            restTextI.setText(R.string.NEXTSTUDYPERIOD);

            // Linear Layout orientation
            viewStudy.setOrientation(LinearLayout.HORIZONTAL);
            viewStudyVertical.setOrientation(LinearLayout.VERTICAL);
            viewRest.setOrientation(LinearLayout.VERTICAL);

            // Next we gonna set Layout params
            LinearLayout.LayoutParams clockParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            clockParams.setMargins(0, -100, 10, 0);
            LinearLayout.LayoutParams studyTextIParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            studyTextIParams.setMargins(0, -22, 0, 0);
            LinearLayout.LayoutParams restTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            restTextParams.setMargins(0, -50, 0, 0);
            studyCounter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            restCounter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            studyTextII.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            restTextI.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            clock.setLayoutParams(clockParams);
            studyTextI.setLayoutParams(studyTextIParams);

            // Add them to different hierarchy
            viewStudyVertical.addView(studyCounter);
            viewStudyVertical.addView(studyTextI);
            viewStudyVertical.addView(studyTextII);

            viewStudyVertical.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

            viewStudy.addView(clock);
            viewStudy.addView(viewStudyVertical);

            viewRest.addView(restCounter);
            viewRest.addView(restTextI);

            // Next we add these Linear Layouts to FrameLayout
            viewStudy.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            viewRest.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            timerView.addView(viewStudy);
            timerView.addView(viewRest);
        }
        return timerView;
    }

    // if and only if you want to start with default parameters should you use this non-arg method. Otherwise please use reset to start the timer
    // Default study period is 45min, rest period is 15min
    public void reset(){

        if(event != null) {
            event.cancel();
        }

        // You never want to see first the rest stuff
        viewStudy.setVisibility(View.VISIBLE);
        viewRest.setVisibility(View.GONE);
        TimerView.startTime = System.currentTimeMillis();
        study();
    }

    // When study period and rest period changed, gotta reset these two variables
    public void reset(int studyp, int restp){

        if(event != null) {
            event.cancel();
        }

        // reset configurations
        TimerView.studyPeriod = studyp;
        TimerView.restPeriod = restp;
        viewStudy.setVisibility(View.VISIBLE);
        viewRest.setVisibility(View.GONE);
        TimerView.startTime = System.currentTimeMillis();
        study();
    }

    // To rest
    public static void rest(){
        viewRest.setAlpha(0.0f);
        viewRest.setVisibility(View.VISIBLE);

        viewRest.animate()
                .alpha(1.0f)
                .setDuration(700)
                .setListener(null);

        // Get rid of study task yay
        viewStudy.animate()
                .alpha(0.0f)
                .setDuration(700)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        viewStudy.setVisibility(View.GONE);
                    }
                });

        startTime += studyPeriod;
        event = new CountDownTimer(restPeriod, 1000) {
            @Override
            public void onTick(long l) {
                restCounter.setText(String.format(Locale.US, "%d MINUTES UNTIL", (int)(l / 60000 + 1)));
            }

            @Override
            public void onFinish() {
                study();
            }
        };
        event.start();
    }

    // To study
    public static void study(){
        viewStudy.setAlpha(0.0f);
        viewStudy.setVisibility(View.VISIBLE);

        viewStudy.animate()
                .alpha(1.0f)
                .setDuration(700)
                .setListener(null);

        // Goodbye rest time
        viewRest.animate()
                .alpha(0.0f)
                .setDuration(700)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        viewRest.setVisibility(View.GONE);
                        startTime = System.currentTimeMillis();
                    }
                });

        startTime += restPeriod;

        event = new CountDownTimer(studyPeriod, 1000) {
            @Override
            public void onTick(long l) {
                studyCounter.setText(String.format(Locale.US, "%d MINUTES", (int)(l / 60000 + 1)));
            }

            @Override
            public void onFinish() {
                rest();
            }
        };
        event.start();
    }
}
