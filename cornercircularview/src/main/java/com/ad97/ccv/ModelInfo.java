package com.ad97.ccv;

import android.content.Intent;
import android.support.annotation.Nullable;

public class ModelInfo {

    private String text;
    private int buttonBackground,textColor;
    private int buttonIcon;

    /**
     * constructor is used to set all information of the menu item according to user requirements
     * @param text text for the TextView
     * @param textColor color for the TextViewColor
     * @param buttonIcon icon for the FloatingActionButtonIcon
     * @param buttonBackground background for the FloatingActionButton
     */
    ModelInfo(String text,int textColor ,int buttonIcon ,int buttonBackground) {
        this.text = text;
        this.textColor = textColor;
        this.buttonIcon = buttonIcon;
        this.buttonBackground = buttonBackground;
    }


    /**
     * all these methods are getters of this class used to fetch information according to project
     */

    public String getText() {
        return text;
    }

    int getButtonBackground() {
        return buttonBackground;
    }

    public int getTextColor() {
        return textColor;
    }

    int getButtonIcon() {
        return buttonIcon;
    }

}
