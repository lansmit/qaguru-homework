package pages;

import pages.components.CalendarComponent;
import com.codeborne.selenide.SelenideElement;
import pages.components.ModalDialogComponent;

import java.util.ArrayList;

import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class RegistrationPageHelper {
    private final SelenideElement nameInput = $("#firstName"),
            surnameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            genderWrapper = $("#genterWrapper"),
            phoneNumberInput = $("#userNumber"),
            calendarInput = $("#dateOfBirthInput"),
            subjectsInput = $("#subjectsInput"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            uploadPictureButton = $("#uploadPicture"),
            addressInput = $("#currentAddress"),
            stateInput = $("#state"),
            stateCityWrapper = $("#stateCity-wrapper"),
            cityInput = $("#city"),
            submitButton = $("#submit"),
            resultTable = $(".table-responsive");

    private String cssPropertyName = "border-color",
            redColor = "rgb(220, 53, 69)",
            greyColor = "rgb(206, 212, 218)";


    private CalendarComponent calendarComponent = new CalendarComponent();
    private ModalDialogComponent modalDialogComponent = new ModalDialogComponent();
    private ArrayList<String> arrayList = new ArrayList<>();

    public RegistrationPageHelper openRegistrationPage() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        removeBanners();
        return this;
    }

    public void removeBanners(){
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
    }

    public RegistrationPageHelper setName(String value) {
        nameInput.setValue(value);
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setSurname(String value) {
        surnameInput.setValue(value);
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setEmail(String value) {
        emailInput.setValue(value);
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setGender(String value) {
        genderWrapper.$(byText(value)).click();
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setPhoneNumber(String value) {
        phoneNumberInput.setValue(value);
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setBirthDay(String day, String month, String year) {
        calendarInput.click();
        calendarComponent.setDate(day, month, year);
        arrayList.add(day + " " + month + "," + year);
        return this;
    }

    public RegistrationPageHelper setSubject(String value) {
        subjectsInput.sendKeys(value);
        subjectsInput.pressEnter();
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setHobby(String value) {
        hobbiesWrapper.$(byText(value)).click();
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper uploadPicture(String fileName) {
        uploadPictureButton.uploadFromClasspath(fileName);
        arrayList.add(fileName);
        return this;
    }

    public RegistrationPageHelper setAddress(String value) {
        addressInput.setValue(value);
        arrayList.add(value);
        return this;
    }

    public RegistrationPageHelper setStateAndCity(String state, String city) {
        stateInput.click();
        stateCityWrapper.$(byText(state)).click();
        cityInput.click();
        stateCityWrapper.$(byText(city)).click();
        arrayList.add(state + " " + city);
        return this;
    }

    public RegistrationPageHelper submitForm() {
        submitButton.click();
        modalDialogComponent.waitingForm();
        return this;
    }

    public void checkResult() {
        arrayList.forEach(x -> resultTable.shouldHave(text(x)));
    }

//    Методы для негативных тестов

    public RegistrationPageHelper submitErrorForm() {
        submitButton.click();
        modalDialogComponent.notAppearingForm();
        return this;
    }

    public void checkBorderColor(String field, String color) {
        String checkingColor = switch (color) {
            case "red" -> redColor;
            case "grey" -> greyColor;
            default -> throw new IllegalArgumentException("Неверно задан цвет");
        };

        switch (field) {
            case "first_name" -> nameInput.shouldHave(cssValue(cssPropertyName, checkingColor));
            case "email"      -> emailInput.shouldHave(cssValue(cssPropertyName, checkingColor));
            case "phone"      -> phoneNumberInput.shouldHave(cssValue(cssPropertyName, checkingColor));
            default           -> throw new IllegalArgumentException("Неверно указано название поля ввода");
        }
    }
}
