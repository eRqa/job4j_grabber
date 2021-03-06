package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import ru.job4j.models.Post;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlRuParse implements Parse {

//    private static final int COUNT_OF_PAGES_TO_PARSE = 5;
    private final DateParser dateParser = new DateParser();

    @Override
    public List<Post> list(String link) {
        List<Post> result = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements rowTheme = doc.select(".postslisttopic");
            for (Element td : rowTheme) {
                String href = td.child(0).attr("href");
                result.add(detail(href));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Post detail(String link) {
        Document docPost = null;
        try {
            docPost = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements msgTables = docPost.select(".msgTable");
        Elements msgBodies = msgTables.select(".msgBody");
        Elements msgFooters = msgTables.select(".msgFooter");

        String postName = msgTables.select("td.messageHeader").get(1).text();
        postName = postName.substring(4, postName.length() - 5);
        String postText = msgBodies.get(1).text();
        String dateText = msgFooters.get(1).text().substring(0, 16);
        Date postCreated = null;
        try {
            postCreated = dateParser.strToDate(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Node> childNodes = msgBodies.get(0).child(0).childNodes();
        String author = childNodes.get(0).toString();

        Post post = new Post();
        post.setName(postName);
        post.setText(postText);
        post.setAuthor(author);
        post.setCreated(postCreated);

        return post;
    }

}