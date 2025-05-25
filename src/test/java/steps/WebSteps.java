package steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class WebSteps {

    @Step
    public void openMainPage(){
        open("https://github.com");
    }

    @Step("Ищем репозиторий {repo}")
    public void searchForRepository(String repo){
        $(".search-input").click();
        $("#query-builder-test").setValue(repo).pressEnter();
    }

    @Step("Кликаем по ссылке репозитория {repo}")
    public void clickOnRepositoryLink(String repo){
        $(linkText(repo)).click();
    }

    @Step("Кликаем на раздел 'Issues'")
    public void clickOnIssuesTab(){
        $("#issues-tab").click();
    }

    @Step("Проверяем заголовок 'Issues'")
    public void checkIssuesTabTitle(){
        $("[data-content=Issues]").shouldHave(text("Issues"));
    }
}
