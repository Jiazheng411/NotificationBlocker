package com.example.notificationlistener3;

import android.animation.ArgbEvaluator;
import android.graphics.drawable.GradientDrawable;

public class DynamicBackground extends GradientDrawable{

    // Initialize new stuff
    private static final float r = 1400.0f;
    private static final int[] skyP = new int[24];
    private static final int[] skyN = new int[24];
    private static final int[] sun = new int[10];
    private static ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    // sun starts from 8

    // Call initialize once and only once before use of Dynamic Background
    public static void initialize(){
        skyP[0] = 0xFF060709;
        skyP[1] = 0xFF0E1427;
        skyP[2] = 0xFF131F39;
        skyP[3] = 0xFF1B294B;
        skyP[4] = 0xFF263B6A;
        skyP[5] = 0xFF385BA1;
        skyP[6] = 0xFF5484C4;
        skyP[7] = 0xFF69A2D6;

        /*
        skyP[8] = 0xFF9BCFEF;
        skyP[9] = 0xFFCDEAF9;
        skyP[10] = 0xFFBCE0F8;
        skyP[11] = 0xFFB1D6F0;
        skyP[12] = 0xFFA7CBEC;
        skyP[13] = 0xFF9ABDE3;
        skyP[14] = 0xFF87A9D7;
        skyP[15] = 0xFF7FA1CF;
        skyP[16] = 0xFF7D87C2;
        skyP[17] = 0xFF6C76B9;
        */

        skyP[18] = 0xFF404AA0;
        skyP[19] = 0xFF2B346F;
        skyP[20] = 0xFF1A1E41;
        skyP[21] = 0xFF101532;
        skyP[22] = 0xFF101323;
        skyP[23] = 0xFF111018;
        skyN[0] = 0xFF060709;
        skyN[1] = 0xFF0D141E;
        skyN[2] = 0xFF11162A;
        skyN[3] = 0xFF11213A;
        skyN[4] = 0xFF1B2E4F;
        skyN[5] = 0xFF29427A;
        skyN[6] = 0xFF3960A7;
        skyN[7] = 0xFF508CC6;
        skyN[8] = 0xFF81BEE9;
        skyN[9] = 0xFFAEDEF3;
        skyN[10] = 0xFF9FCDED;
        skyN[11] = 0xFF93C1E5;
        skyN[12] = 0xFF8DB7E1;
        skyN[13] = 0xFF7EA7D5;
        skyN[14] = 0xFF7197C4;
        skyN[15] = 0xFF688CB8;
        skyN[16] = 0xFF6573B4;
        skyN[17] = 0xFF5661AD;
        skyN[18] = 0xFF303B8B;
        skyN[19] = 0xFF242960;
        skyN[20] = 0xFF13173A;
        skyN[21] = 0xFF13132B;
        skyN[22] = 0xFF0F141F;
        skyN[23] = 0xFF0F0E18;

        skyP[8] = 0xFF9DB4FF;
        skyP[9] = 0xFFCAD8FF;
        skyP[10] = 0xFFEDEEFF;
        skyP[11] = 0xFFFBF8FF;
        skyP[12] = 0xFFFFF9F9;
        skyP[13] = 0xFFFFF5EC;
        skyP[14] = 0xFFFFF4E8;
        skyP[15] = 0xFFFFEBD1;
        skyP[16] = 0xFFFFD7AE;
        skyP[17] = 0xFFFFBB7B;

        sun[0] = 0xFF9DB4FF;
        sun[1] = 0xFFCAD8FF;
        sun[2] = 0xFFEDEEFF;
        sun[3] = 0xFFFBF8FF;
        sun[4] = 0xFFFFF9F9;
        sun[5] = 0xFFFFF5EC;
        sun[6] = 0xFFFFF4E8;
        sun[7] = 0xFFFFEBD1;
        sun[8] = 0xFFFFD7AE;
        sun[9] = 0xFFFFBB7B;
    }

    private DynamicBackground(int[] colors){
        super(Orientation.TOP_BOTTOM, colors);
        this.setShape(GradientDrawable.RECTANGLE);
        this.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        this.setGradientRadius(r);
        this.setGradientCenter(0, 0);
    }

    public static DynamicBackground getBackground(){
        int time = (int)(System.currentTimeMillis() % 86400000);
        int hour = time / 3600000;
        float thour = time / 3600000.0f;
        int colors[] = new int[2];

        colors[0] = (int)(argbEvaluator.evaluate(thour - hour, skyP[(hour + 8) % 24], skyP[(hour + 9) % 24]));
        colors[1] = (int)(argbEvaluator.evaluate(thour - hour, skyN[(hour + 8) % 24], skyN[(hour + 9) % 24]));

        // some function
        return new DynamicBackground(colors);
    }

    // Debug use only
    public static DynamicBackground getBackground(long time){
        long hour = time / 3600000;
        float thour = time / 3600000.0f;
        int colors[] = new int[2];

        colors[0] = (int)(argbEvaluator.evaluate(thour - hour, skyP[(int)((hour + 8) % 24)], skyP[(int)((hour + 9) % 24)]));
        colors[1] = (int)(argbEvaluator.evaluate(thour - hour, skyN[(int)((hour + 8) % 24)], skyN[(int)((hour + 9) % 24)]));

        // some function
        return new DynamicBackground(colors);
    }
}
