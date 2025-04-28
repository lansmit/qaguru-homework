package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class ModalDialogComponent {

    private final SelenideElement modalDialog = $(".modal-dialog");
    private final SelenideElement modalTitle = $("#example-modal-sizes-title-lg");

    public void waitingForm() {
        modalDialog.should(appear);
        String modalText = "Thanks for submitting the form";
        modalTitle.shouldHave(text(modalText));
    }

    public void notAppearingForm() {
        modalDialog.shouldNot(appear);
    }
}
