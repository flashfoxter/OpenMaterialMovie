/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lk.openmaterialmovie.R;


public class Dialogue {
    private static final int POSITIVE_BUTTON_WEIGHT = 10;
    private static final int NEGATIVE_BUTTON_WEIGHT = 20;

    @SuppressWarnings("SameParameterValue")
    private static AlertDialog create(Context c, String title, String message, String buttonOk, String buttonCancel, AlertDialog.OnClickListener listener) {
        return create(c, title, message, buttonOk, buttonCancel, listener, null);
    }

    private static AlertDialog create(Context c, String title, String message, String buttonOk, String buttonCancel, AlertDialog.OnClickListener listener, View additionalView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c, R.style.CustomDialogTheme);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonOk, listener);
        if (buttonCancel != null && !buttonCancel.isEmpty()) {
            builder.setNegativeButton(buttonCancel, listener);
        }
        if (additionalView != null) {
            builder.setView(additionalView);
        }
        builder.setCancelable(false);
        return builder.create();
    }

    private static AlertDialog.OnClickListener convertListener(Runnable actionOk, Runnable actionCancel) {
        return ((dialog, buttonId) -> {
            if (buttonId == DialogInterface.BUTTON_POSITIVE && actionOk != null) {
                actionOk.run();
            }
            if (buttonId == DialogInterface.BUTTON_NEGATIVE && actionCancel != null) {
                actionCancel.run();
            }
        });
    }

    private static void customizeOneButton(AlertDialog dialog) {
        Button btnPositive = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        params.setMargins((int) (btnPositive.getPaddingLeft() * -2.5), 0, 0, 0);
        customizeButton(btnPositive, POSITIVE_BUTTON_WEIGHT);
    }

    public static void customizeTwoButtons(AlertDialog dialog) {
        Button btnPositive = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
        customizeButton(btnPositive, POSITIVE_BUTTON_WEIGHT);
        customizeButton(btnNegative, NEGATIVE_BUTTON_WEIGHT);
    }

    private static void customizeButton(Button button, int weight) {
        if (button == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.weight = weight;
        button.setLayoutParams(layoutParams);
        colorizeButton(button);
    }

    private static void colorizeButton(Button button) {
        if (button != null) {
            button.setTextColor(button.getContext().getResources().getColor(R.color.colorAccent));
        }
    }

    // PUBLIC METHODS FROM HERE

    public static void show(String title, String message, String buttonOk, AlertDialog.OnClickListener listener) {
        Context c = Ui.getActivity();
        AlertDialog dialog = create(c, title, message, buttonOk, null, listener);
        dialog.show();
        Dialogue.customizeOneButton(dialog);
    }

    public static void showTwoButtons(String title, String message, String buttonOk, String buttonCancel, Runnable ok, Runnable cancel) {
        Context c = Ui.getActivity();
        AlertDialog dialog = create(c, title, message, buttonOk, buttonCancel, convertListener(ok, cancel));
        dialog.show();
        Dialogue.customizeTwoButtons(dialog);
    }

    public static void showTwoButtons(String title, @StringRes int message, @StringRes int buttonOk, @StringRes int buttonCancel, Runnable ok, Runnable cancel) {
        showTwoButtons(title, Ui.getString(message), Ui.getString(buttonOk), Ui.getString(buttonCancel), ok, cancel);
    }

    public static void showSimplest(String title, String message, AlertDialog.OnClickListener listener) {
        Context c = Ui.getActivity();
        show(title, message, c.getString(R.string.common_buttonOk), listener);
    }

    public static void showSimpleImage(String title, String message, Runnable actionOk, Runnable actionCancel, View additionalView) {
        Context c = Ui.getActivity();
        AlertDialog dialog = create(c, title, message, c.getString(R.string.common_buttonOk), c.getString(R.string.common_buttonCancel),
                convertListener(actionOk, actionCancel), additionalView);
        dialog.show();
        colorizeButton(dialog.getButton(DialogInterface.BUTTON_POSITIVE));
        colorizeButton(dialog.getButton(DialogInterface.BUTTON_NEGATIVE));
    }

    public static void show(@StringRes int text) {
        show("", Ui.getString(text), null);
    }

    public static void show(@StringRes int text, Runnable action) {
        show("", Ui.getString(text), action);
    }

    public static void show(String text) {
        show("", text, null);
    }

    public static void show(String text, Runnable action) {
        show("", text, action);
    }

    public static void show(@StringRes int title, @StringRes int text) {
        show(title, text, null);
    }

    public static void show(@StringRes int titleId, @StringRes int textId, Runnable action) {
        show(Ui.getString(titleId), Ui.getString(textId), action);
    }

    public static void show(@StringRes int titleId, String text, Runnable action) {
        show(Ui.getString(titleId), text, action);
    }

    public static void show(String title, String text, Runnable action) {
        showSimplest(title, text, convertListener(action, null));
    }

    public static void showError(String error) {
        show(Ui.getString(R.string.common_error), error, null);
    }

    public static void showError(@StringRes int errorId) {
        showError(Ui.getString(errorId));
    }
}
