package com.xiongxh.cryptocoin.utilities;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
    public final DecimalFormat dollarFormatWithPlus;
    public final DecimalFormat dollarFormat;
    public final DecimalFormat dollarFormatWithSign;
    public final DecimalFormat percentageFormat;

    public final DecimalFormat btcFormat;
    public final DecimalFormat btcFormatWithPlus;
    public final DecimalFormat btcFormatWithSign;

    public NumberUtils(){
        dollarFormatWithSign = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        dollarFormat.setMaximumFractionDigits(2);
        dollarFormat.setMinimumFractionDigits(2);

        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

        btcFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        btcFormat.setMaximumFractionDigits(6);
        btcFormat.setMinimumFractionDigits(6);

        btcFormatWithSign = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        btcFormatWithSign.setMaximumFractionDigits(6);
        btcFormatWithSign.setMinimumFractionDigits(6);
        btcFormatWithSign.setPositivePrefix("B");

        btcFormatWithPlus = (DecimalFormat) NumberFormat.getNumberInstance(Locale.getDefault());
        btcFormatWithPlus.setPositivePrefix("+B");
    }
}
