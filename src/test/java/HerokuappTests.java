import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import static com.codeborne.selenide.logevents.SelenideLogger.step;

public class HerokuappTests {

    @Test
    public void checkDragAndDropWithActions() {
        step("Открываем страницу с drag&drop",
                () -> open("https://the-internet.herokuapp.com/drag_and_drop"));
        step("Перетаскиваем элемент А на место В через actions()", () -> {
            SelenideElement elementA = $("#column-a");
            SelenideElement elementB = $("#column-b");

            actions()
                    .clickAndHold(elementA)
                    .moveToElement(elementB)
                    .release()
                    .perform();

        });
        step("Проверяем, что элементы поменялись местами", () -> {
            $("#column-a header").shouldHave(text("B"));
            $("#column-b header").shouldHave(text("A"));
        });
    }

    @Test
    public void checkDragAndDropWithSelenide() {
        step("Открываем страницу с drag and drop",
                () -> open("https://the-internet.herokuapp.com/drag_and_drop"));
        step("Перетаскиваем элемент А на место В через dragAndDrop()",
                () -> $("#column-a").dragAndDrop((DragAndDropOptions.to($("#column-b")))));
        step("Проверяем, что элементы поменялись местами", () -> {
            $("#column-a header").shouldHave(text("B"));
            $("#column-b header").shouldHave(text("A"));
        });
    }


}
