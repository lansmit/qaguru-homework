package tests;

import org.junit.jupiter.api.Test;
import pages.RegistrationPageHelper;
import pages.components.ModalDialogComponent;
import utils.RandomUtils;

public class RegistrationPageObjectsTests extends TestBase {
    protected RegistrationPageHelper registrationPage = new RegistrationPageHelper();
    protected ModalDialogComponent modalDialogComponent = new ModalDialogComponent();
    TestData testData = new TestData();

    @Test
    void sendAllFieldsSuccessfulTest() {

        registrationPage.openRegistrationPage()
                .removeBanners()
                .setName(testData.firstName)
                .setSurname(testData.lastName)
                .setEmail(testData.email)
                .setGender(testData.gender)
                .setPhoneNumber(testData.phoneNumber)
                .setBirthDay(testData.birthDay, testData.birthMonth, testData.birthYear)
                .setSubject(testData.subjectName)
                .setHobby(testData.hobbyName)
                .uploadPicture(testData.fileName)
                .setAddress(testData.address)
                .setStateAndCity(testData.stateName, testData.cityName)
                .submitForm();

        modalDialogComponent.verifyFormSubmittedSuccessfully()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Student Email", testData.email)
                .checkResult("Gender", testData.gender)
                .checkResult("Mobile", testData.phoneNumber)
                .checkResult("Date of Birth", testData.birthDay + " " + testData.birthMonth + "," + testData.birthYear)
                .checkResult("Subjects", testData.subjectName)
                .checkResult("Hobbies", testData.hobbyName)
                .checkResult("Picture", testData.fileName)
                .checkResult("Address", testData.address)
                .checkResult("State and City", testData.stateName + " " + testData.cityName);
    }

    @Test
    void sendOnlyRequiredFieldsSuccessfulTest() {

        registrationPage.openRegistrationPage()
                .setName(testData.firstName)
                .setSurname(testData.lastName)
                .setGender(testData.gender)
                .setPhoneNumber(testData.phoneNumber)
                .submitForm();

        modalDialogComponent.verifyFormSubmittedSuccessfully()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Gender", testData.gender)
                .checkResult("Mobile", testData.phoneNumber);
    }

    @Test
    void checkNoNameErrorTest() {

        registrationPage.openRegistrationPage()
                .setSurname(testData.lastName)
                .setGender(testData.gender)
                .setPhoneNumber(testData.phoneNumber)
                .submitErrorForm()
                .checkBorderColor("first_name", "red");
    }
}
