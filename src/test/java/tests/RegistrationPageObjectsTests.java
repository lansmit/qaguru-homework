package tests;

import org.junit.jupiter.api.Test;
import pages.RegistrationPageHelper;

public class RegistrationPageObjectsTests extends TestBase {
    private final String fileName = "samplephoto.jpg",
            name = "SomeName",
            surname = "SomeSurname",
            email = "some@mail.com",
            gender = "Male",
            phoneNumber = "9990001122",
            birthDaySent = "11",
            birthMonthSent = "September",
            birthYearSent = "1996",
            subjectName = "Arts",
            hobbyName = "Sports",
            address = "St-Petersburg",
            stateName = "Uttar Pradesh",
            cityName = "Agra";

    protected RegistrationPageHelper registrationPage = new RegistrationPageHelper();

    @Test
    void sendAllFieldsSuccessfulTest() {

        registrationPage.openRegistrationPage()
                .setName(name)
                .setSurname(surname)
                .setEmail(email)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .setBirthDay(birthDaySent, birthMonthSent, birthYearSent)
                .setSubject(subjectName)
                .setHobby(hobbyName)
                .uploadPicture(fileName)
                .setAddress(address)
                .setStateAndCity(stateName, cityName)
                .submitForm()
                .checkResult();
    }

    @Test
    void sendOnlyRequiredFieldsSuccessfulTest() {

        registrationPage.openRegistrationPage()
                .setName(name)
                .setSurname(surname)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .submitForm()
                .checkResult();
    }

    @Test
    void checkNoNameErrorTest() {

        registrationPage.openRegistrationPage()
                .setSurname(surname)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .submitErrorForm()
                .checkBorderColor("first_name", "red");
    }
}
