package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.WebSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class AllureTests {

    private static final String repo = "allure-framework/allure2";

    @BeforeEach
    public void beforeEachConfig() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    @Feature("Раздел Issues в репозитории")
    @Story("Отображение Issues")
    @DisplayName("Issue search только с Selenide")
    @Owner("Trubnikov")
    public void IssueSearchTest() {
        open("https://github.com");
        $(".search-input").click();
        $("#query-builder-test").setValue(repo).pressEnter();
        $(linkText(repo)).click();
        $("#issues-tab").click();
        $("[data-content=Issues]").shouldHave(text("Issues"));
    }

    @Test
    @Feature("Раздел Issues в репозитории")
    @Story("Отображение Issues")
    @DisplayName("Issue search с использованием Steps")
    @Owner("Trubnikov")
    public void IssueSearchWithSteps() {
            step("Открываем главную страницу", () -> {
                open("https://github.com");
            });
            step("Ищем репозиторий " + repo, () -> {
                $(".search-input").click();
                $("#query-builder-test").setValue(repo).pressEnter();
            });
            step ("Кликаем на репозиторий " + repo, () -> {
                $(linkText(repo)).click();
            });
            step ("Кликаем на раздел 'Issues'", () -> {
                $("#issues-tab").click();
            });
            step ("Проверяем заголовок 'Issues'", () -> {
                $("[data-content=Issues]").shouldHave(text("Issues"));
            });
        }

    @Test
    @Feature("Раздел Issues в репозитории")
    @Story("Отображение Issues")
    @DisplayName("Issue search с использованием Steps")
    @Owner("Trubnikov")
    public void IssueSearchWithAnnotatedSteps() {

        WebSteps webSteps = new WebSteps();

        webSteps.openMainPage();
        webSteps.searchForRepository(repo);
        webSteps.clickOnRepositoryLink(repo);
        webSteps.clickOnIssuesTab();
        webSteps.checkIssuesTabTitle();

    }
}