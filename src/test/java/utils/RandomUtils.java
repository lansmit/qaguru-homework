package utils;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RandomUtils {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String getRandomFirstName() {
            return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomGender() {
        return faker.options().option("Male", "Female", "Other");
    }

    public static String getRandomPhoneNumber() {
        return faker.numerify("77########");
    }

    public static String[] getRandomBirthDate() {
        Date from = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        Date to = new Date();

        Date birthday = faker.date().between(from, to);
        // В календаре доступен выбор даты, начиная с 1 января 1900 года, поэтому ввожу такие ограничения.
        // Использую такой более сложный формат в качестве эксперимента, чтобы не исключать из списка
        // случайных дат 29,30,31 числа.

        String day = new SimpleDateFormat("dd").format(birthday);
        String month = new SimpleDateFormat("MMMM").format(birthday);
        String year = new SimpleDateFormat("yyyy").format(birthday);

        return new String[] { day, month, year };
        // Возвращаю здесь результат для использования в TestData далее.
    }
    public static String getRandomSubject() {
        return faker.options().option("Maths", "Chemistry", "Computer Science",
                "Commerce", "Economics", "Arts");
    }

    public static String getRandomHobby() {
        return faker.options().option("Sports", "Reading", "Music");
    }

    public static String getRandomAddress() {
        return faker.address().fullAddress();
    }

    public static String getRandomState() {
        return faker.options().option("NCR", "Uttar Pradesh", "Haryana",
                "Rajasthan");
    }

    public static String getRandomCity(String stateName) {
        return switch (stateName) {
            case "NCR" -> faker.options().option("Delhi", "Gurgaon", "Noida");
            case "Uttar Pradesh" -> faker.options().option("Agra", "Lucknow", "Merrut");
            case "Haryana" -> faker.options().option("Karnal", "Panipat");
            case "Rajasthan" -> faker.options().option("Jaipur", "Jaiselmer");
            default -> throw new IllegalArgumentException("Некорректный штат");
        };
    }

}
