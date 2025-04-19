import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideGithubTests {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://github.com"; // Абсолютный URL
    }

    @Test
    void checkSoftAssertionsPageTest() {
        // Открываем Wiki в Github Selenide
        open("/selenide/selenide/wiki");
        // Кликаем на кнопку "Show 3 more pages", так как нам нужна именно ссылка в блоке Pages
        $(".Box-row.wiki-more-pages-link").click();
        // Проверяем, что текст ссылки = "Soft assertions"" и кликаем на неё
        $("a[href='/selenide/selenide/wiki/SoftAssertions']").shouldHave(text("Soft assertions")).click();
        // Проверяем наличие кода про JUnit 5 после заголовка "3. Using JUnit5 extend test class:"
        $$(".markdown-heading").findBy(text("3. Using JUnit5 extend test class:")).sibling(0).$("pre")
                .shouldHave(text("""
                @ExtendWith({SoftAssertsExtension.class})
                class Tests {
                  @Test
                  void test() {
                    Configuration.assertionMode = SOFT;
                    open("page.html");

                    $("#first").should(visible).click();
                    $("#second").should(visible).click();
                  }
                }"""));
    }
}