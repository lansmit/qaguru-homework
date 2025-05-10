package tests;

import static utils.RandomUtils.*;

public class TestData {
    String firstName = getRandomFirstName();
    String lastName = getRandomLastName();
    String email = getRandomEmail();
    String gender = getRandomGender();
    String phoneNumber = getRandomPhoneNumber();
    String[] birthDate = getRandomBirthDate();
    String birthDay = birthDate[0];
    String birthMonth = birthDate[1];
    String birthYear = birthDate[2];

    // Число, месяц и год выбираются отдельно, поэтому выгружаю дату целиком,
    // а потом беру из неё нужную часть.

    String subjectName = getRandomSubject();
    String hobbyName = getRandomHobby();
    String address = getRandomAddress();
    String stateName = getRandomState();
    String cityName = getRandomCity(stateName);
    String fileName = "samplephoto.jpg";
}
