package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Tag("SampleTests")
public class ParametrizedTests
 {
    @ValueSource(strings = {"Java", "Python"})
    @ParameterizedTest (name = "Поиск {0} по Википедии должен открывать статью с названием {0}")
    public void wikiSearchProgrammingLanguageShouldOpenArticleTest(String searchQuery) {

        String startPage = "https://ru.wikipedia.org/wiki/Заглавная_страница";

        open (startPage);
        $("#searchInput").shouldBe(visible).setValue(searchQuery);
        $("#searchButton").click();
        $(".mw-page-title-main").shouldHave(text(searchQuery));
    }

    @CsvSource(value = {"Java, https://ru.wikipedia.org/wiki/Java", "Python, https://ru.wikipedia.org/wiki/Python"})
    @ParameterizedTest(name = "Поиск {0} по Википедии должен открывать статью с названием {0}")
    public void wikiOpenDirectLinkTest(String languageName, String url) {
        open(url);
        $(".mw-page-title-main").shouldHave(text(languageName));
    }

    static Stream<Arguments> shouldSeeElementByIdOnWikipediaMainPage() {
        return Stream.of(
                Arguments.of("Изображение_дня"),
                Arguments.of("Текущие_события"),
                Arguments.of("Совместная_работа_недели"),
                Arguments.of("footer-info-copyright"),
                Arguments.of("Родственные_проекты_Викимедиа")
        );
    }

    @MethodSource("shouldSeeElementByIdOnWikipediaMainPage")
    @ParameterizedTest(name = "Проверка наличия элемента с id={0} на главной странице Википедии")
    void shouldSeeElementByIdOnWikipediaMainPage(String id) {
        open("https://ru.wikipedia.org/wiki/Заглавная_страница");
        $("#" + id).shouldBe(visible);
    }
}

