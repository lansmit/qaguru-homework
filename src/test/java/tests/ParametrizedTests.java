package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import pages.RegistrationPageHelper;
import pages.components.ModalDialogComponent;
import utils.RandomUtils;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedTests {

    @ParameterizedTest (name = "Поиск '{0}' по Википедии должен открывать статью с названием {0}")
    @Tag("Sample Tests")
    @ValueSource(strings = {"Java", "Python"})
    @DisplayName("Базовый тест поиска в Википедии")
    public void wikiSearchProgrammingLanguageShouldOpenArticleTest(String searchQuery) {

    String startPage = "https://ru.wikipedia.org/wiki/%D0%97%D0%B0%D0%B3%D0%BB%D0%B0%D0%B2%D0%BD%D0%B0%D1%8F_%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B0";

    open (startPage);
    $("#searchInput").shouldBe(visible).setValue(searchQuery);
    $("#searchButton").click();
    $(".mw-page-title-main").shouldHave(text(searchQuery));
    }
}
