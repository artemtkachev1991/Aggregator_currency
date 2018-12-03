package com.example.currency.aggregation.support;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class StaticMessages {
    public static final String HISTORY_UNKNOWN_MESSAGE = "Unknown action for two currency values";
    public static final String ENABLED ="enabled";
    public static final String DISABLED ="disabled";
    public static final String CHECK_USERS_SQL = "SELECT count(*) from users";
    public static final String CHECK_CURRENCIES_SQL = "SELECT count(*) from currencyvalue";
    public static final String MESSAGE_ILLEGAL_CURRENCY_NAME = "There are no currency with such name: %s";
    public static final String MESSAGE_ILLEGAL_CURRENCY_CREATION = "Can't create currency for: %s";
    public static final String EMPTY_VALUE = "-";
    public static final String NO_VALUE_FOR_PDF = "-//-";
    public static final String NO_FLAGS = "No allow or delete flag present - what do you wanted to do with data?";
    public static final String CODES = "Codes";
    public static final String BUYING = "Buying";
    public static final String SELLING = "Selling";
    public static final String RATE = "Rate";
    public static final String BANK = "Bank";
    public static final String UNKNOWN_ERROR = "Unknown error: ";
    public static final String UNKNOWN_FORMAT = "Unknown format: ";

    public static final String SPLITTER_REGEX = "\\.";

    private static final int FONT_SIZE_STANDARD = 10;
    private static final int FONT_SIZE_TITLE = 12;
    public static final Font BLACK_TITLE_FONT = new Font(Font.FontFamily.HELVETICA, FONT_SIZE_TITLE, Font.BOLD, BaseColor.BLACK);
    public static final Font STANDARD_FONT = new Font(Font.FontFamily.HELVETICA, FONT_SIZE_STANDARD, Font.NORMAL, BaseColor.BLACK);
    public static final Font STANDARD_BOLD_FONT = new Font(Font.FontFamily.HELVETICA, FONT_SIZE_STANDARD, Font.BOLD, BaseColor.BLACK);
}
