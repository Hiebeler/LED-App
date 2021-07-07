package com.example.myapplication;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), 07, 00, DateFormat.is24HourFormat(getActivity()));
    }
}
