package ru.job4j.html;

import junit.framework.TestCase;
import ru.job4j.models.Post;

import java.util.Calendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SqlRuParseTest extends TestCase {

    public void testDetail() {
        String link = "https://www.sql.ru/forum/1333945/vakansii-sovkombank";
        SqlRuParse parser = new SqlRuParse();
        Post post = parser.detail(link);

        Post expectedPost = new Post();
        String expectedText = "Совкомбанк приглашает ИТ специалистов. Много интересных проектов. " +
                "Стек современный. Руководство адекватное, все лояльно и лайтово. " +
                "Есть ежегодный промоушен. Много плюшек от банка. " +
                "Белая зарплата по рынку и выше, есть премии, 13 зп, переработки оплачиваются в x2. " +
                "Есть потребность в разработчиках (Java, C#, Php, react, vue, Android, Ios, SQL), " +
                "аналитиках, тестировщиках. ИТ офисы есть в Москве, Казани, Саратове, Новосибирске, " +
                "С-Петербурге, Воронеже. Готовы рассматривать и удаленщиков. Кому интересно пишите в личку. " +
                "Модератор: Вакансия обязана содержать вилку ЗП. " +
                "Как таковой \"лички\" здесь нет. " +
                "Для связи опубликуйте контакты, не забыв указать " +
                "вилку ЗП Сообщение было отредактировано: 2 мар 21, 13:26";
        expectedPost.setText(expectedText);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 3, 2, 12, 54);
        expectedPost.setCreated(calendar.getTime());
        expectedPost.setAuthor(" BankIT ");
        expectedPost.setName("Вакансии Совкомбанк");
        assertThat(post, is(expectedPost));
    }
}