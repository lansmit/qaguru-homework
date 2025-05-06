package pages.components;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;


import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ModalDialogComponent {

    private final SelenideElement modalDialog = $(".modal-dialog");
    private final SelenideElement modalTitle = $("#example-modal-sizes-title-lg");

    private final ElementsCollection tableRows = $$("table tr");

    public ModalDialogComponent verifyFormSubmittedSuccessfully() {
        modalDialog.should(appear);
        String modalText = "Thanks for submitting the form";
        modalTitle.shouldHave(text(modalText));
        return this;
    }

    public ModalDialogComponent checkResult(String fieldName, String expectedValue) {
        tableRows.findBy(text(fieldName)).shouldHave(text(expectedValue));
        return this;
    }

    public void notAppearingForm() {
        modalDialog.shouldNot(appear);
    }
}

