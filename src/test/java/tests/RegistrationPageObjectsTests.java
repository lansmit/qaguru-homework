package tests;

import org.junit.jupiter.api.Test;
import pages.RegistrationPageHelper;
import pages.components.ModalDialogComponent;

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
    protected ModalDialogComponent modalDialogComponent = new ModalDialogComponent();

    @Test
    void sendAllFieldsSuccessfulTest() {

        registrationPage.openRegistrationPage()
                .removeBanners()
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
                .submitForm();

        modalDialogComponent.verifyFormSubmittedSuccessfully()
                .checkResult("Student Name", name + " " + surname)
                .checkResult("Student Email", email)
                .checkResult("Gender", gender)
                .checkResult("Mobile", phoneNumber)
                .checkResult("Date of Birth", birthDaySent + " " + birthMonthSent + "," + birthYearSent)
                .checkResult("Subjects", subjectName)
                .checkResult("Hobbies", hobbyName)
                .checkResult("Picture", fileName)
                .checkResult("Address", address)
                .checkResult("State and City", stateName + " " + cityName);
    }

    @Test
    void sendOnlyRequiredFieldsSuccessfulTest() {

        registrationPage.openRegistrationPage()
                .setName(name)
                .setSurname(surname)
                .setGender(gender)
                .setPhoneNumber(phoneNumber)
                .submitForm();

        modalDialogComponent.verifyFormSubmittedSuccessfully()
                .checkResult("Student Name", name + " " + surname)
                .checkResult("Gender", gender)
                .checkResult("Mobile", phoneNumber);
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
