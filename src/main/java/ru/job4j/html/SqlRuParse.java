package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SqlRuParse {
    private static final Locale LOCALE = new Locale("ru");
    private static final int COUNT_OF_PAGES_TO_PARSE = 5;
    private static final String[] SHORT_MONTHS = {
            "янв", "фев", "мар", "апр", "май", "июн", "июл", "ауг", "сен", "окт", "ноя", "дек"};

    public static void main(String[] args) throws Exception {

        for (int i = 1; i <= COUNT_OF_PAGES_TO_PARSE; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements rowTheme = doc.select(".postslisttopic");
            for (Element td : rowTheme) {
                Element href = td.child(0);
                Element data = td.parent().child(5);
                System.out.println(href.attr("href"));
                System.out.println(href.text() + " - " + strToDate(data.text()));
            }
        }
    }

    private static Date strToDate(String str) throws ParseException {
        Date result = null;
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

    private static Date humanityToDate(String strDate) {
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