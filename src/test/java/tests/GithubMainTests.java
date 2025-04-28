package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.step;



public class GithubMainTests {

    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1280x800";
    }

    @Test
    public void checkEnterprisePageNavigation() {
        step("Открываем главную страницу GitHub",
                () -> open("https://github.com"));
        step("Наводим курсор на Solutions и кликаем Enterprise", () -> {
            $(byTagAndText("button", "Solutions")).hover();
            $$("a").findBy(text("Enterprise")).click();
        });
        step("Проверяем заголовок страницы",
                () -> $("title").shouldHave(innerText("The AI Powered Developer Platform")));
    }
}
