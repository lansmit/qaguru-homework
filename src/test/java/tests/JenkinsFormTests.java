package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("smoke")
public class JenkinsFormTests {
    private static final String SELENOID_URL = System.getProperty("selenoid.url");
    private static final String SELENOID_LOGIN = System.getProperty("selenoid.login");;
    private static final String SELENOID_PASSWORD = System.getProperty("selenoid.password");

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
        Configuration.remote = "https://" + SELENOID_LOGIN + ":" + SELENOID_PASSWORD + "@" + SELENOID_URL + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    @DisplayName("Проверка формы регистрации")
    void formSuccessfulTest() {
        step ("Открываем форму", () -> {
            open("/automation-practice-form");
            $("#firstName").setValue(name);
            $("#lastName").setValue(surname);
            $("#userEmail").setValue(email);
            $("#userNumber").setValue(phone);
        });
        step ("Выбираем пол и дату рождения", () -> {
            $("#genterWrapper").$(byText("Male")).click();
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("September");
            $(".react-datepicker__year-select").selectOption("1996");
            $$("div.react-datepicker__day").findBy(text("11")).click();
        });
        step ("Выбираем предмет и хобби", () -> {
            $("#subjectsInput").setValue(subject).pressEnter();
            $("#hobbiesWrapper").$(byText("Sports")).click();
            $("#hobbiesWrapper").$(byText("Reading")).click();
            $("#hobbiesWrapper").$(byText("Music")).click();
        });
      step ("Загружаем картинку", () -> {
            $("#uploadPicture").uploadFromClasspath("samplephoto.jpg");
        });
      step ("Выбираем адрес", () -> {
            $("#currentAddress").scrollIntoView(true);
            $("#currentAddress").setValue(currentAddress);
            $("#state").click();
            $("#react-select-3-input").setValue("Uttar Pradesh").pressEnter();
            $("#city").click();
            $("#react-select-4-input").setValue("Agra").pressEnter();
            $("#submit").click();
      });
      step ("Проверяем выбранные значения", () -> {
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
      });
    }
}
