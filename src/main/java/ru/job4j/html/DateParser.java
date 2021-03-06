package ru.job4j.html;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    private final Locale LOCALE = new Locale("ru");
    private final String[] SHORT_MONTHS = {
            "янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};

    public Date strToDate(String str) throws ParseException {
        Date result;
        if (str.startsWith("сегодня") || str.startsWith("вчера")) {
            result = humanityToDate(str);
        } else {
            DateFormatSymbols dfs = DateFormatSymbols.getInstance(LOCALE);
            dfs.setShortMonths(SHORT_MONTHS);
            SimpleDateFormat format;
            format = new SimpleDateFormat("d MMM yy, HH:mm", LOCALE);
            format.setDateFormatSymbols(dfs);
            result = format.parse(str);
        }
        return result;
    }

    private Date humanityToDate(String strDate) {
        String[] dateParts = strDate.split(", ");
        Calendar calendar = Calendar.getInstance(LOCALE);
        if (dateParts[0].equals("вчера")) {
            calendar.add(Calendar.DATE, -1);
        }
        int hours = Integer.parseInt(dateParts[1].split(":")[0]);
        int minutes = Integer.parseInt(dateParts[1].split(":")[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }


}
