import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class FormTests {

//    Переменные для ввода значений
    String name = "SomeName";
    String surname = "SomeSurname";
    String email = "some@mail.com";
    String phone = "9990001122";
    String subject = "Arts";
    String currentAddress = "St-Petersburg";

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }

    @Test
    void formSuccessfulTest() {
//        Заполняем контакты
        open("/automation-practice-form");
        $("#firstName").setValue(name);
        $("#lastName").setValue(surname);
        $("#userEmail").setValue(email);
        $("#userNumber").setValue(phone);

//        Выбираем пол и дату рождения
        $("#genterWrapper").$(byText("Male")).click();
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("September");
        $(".react-datepicker__year-select").selectOption("1996");
        $$("div.react-datepicker__day").findBy(text("11")).click();

//        Выбираем предмет и хобби
        $("#subjectsInput").setValue(subject).pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#hobbiesWrapper").$(byText("Music")).click();

//        Загружаем картинку
        $("#uploadPicture").uploadFromClasspath("samplephoto.jpg");

//        Выбираем адрес
        $("#currentAddress").setValue(currentAddress);
        $("#state").click();
        $("#react-select-3-input").setValue("Uttar Pradesh").pressEnter();
        $("#city").click();
        $("#react-select-4-input").setValue("Agra").pressEnter();
        $("#submit").click();

//        Проверяем выбранные значения
        $(".modal-content").shouldHave(text("Thanks for submitting the form"));
        $$("table tr").findBy(text("Student Name")).shouldHave(text(name+" "+surname));
        $$("table tr").findBy(text("Student Email")).shouldHave(text(email));
        $$("table tr").findBy(text("Gender")).shouldHave(text("Male"));
        $$("table tr").findBy(text("Mobile")).shouldHave(text(phone));
        $$("table tr").findBy(text("Date of Birth")).shouldHave(text("11 September,1996"));
        $$("table tr").findBy(text("Subjects")).shouldHave(text(subject));
        $$("table tr").findBy(text("Hobbies")).shouldHave(text("Sports, Reading, Music"));
        $$("table tr").findBy(text("Picture")).shouldHave(text("samplephoto.jpg"));
        $$("table tr").findBy(text("Address")).shouldHave(text(currentAddress));
        $$("table tr").findBy(text("State and City")).shouldHave(text("Uttar Pradesh Agra"));


    }

}
