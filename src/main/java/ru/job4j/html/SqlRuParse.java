package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    private static final int COUNT_OF_PAGES_TO_PARSE = 5;
    private static final DateParser dateParser = new DateParser();

    public static void main(String[] args) throws Exception {

        for (int i = 1; i <= COUNT_OF_PAGES_TO_PARSE; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements rowTheme = doc.select(".postslisttopic");
            for (Element td : rowTheme) {
                Element href = td.child(0);
                Element data = td.parent().child(5);
                System.out.println(href.attr("href"));
                System.out.println(href.text() + " - " + dateParser.strToDate(data.text()));
            }
        }
    }

}