package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {

    }

    private static final Faker faker = new Faker(new Locale("en-US"));

    @Value
    public static class CardInfo {
        private String numberCards;   //номер карты
        private String month;            //месяц
        private String year;             //год
        private String cardowner;     //владелец
        private String cvc;              //CVC/CVV
    }

    //рабочая карта — операция одобрена
    public static String getCardNumberApproved() {
        String cardNumber1 = "1111222233334444";
        return cardNumber1;
    }

    //рабочая карта — операция не одобрена
    public static String getCardNumberDeclined() {
        String cardNumber2 = "5555666677778888";
        return cardNumber2;
    }

    //генерируем месяц
    public static String generateMonth() {
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int rnd = new Random().nextInt(month.length);
        return month[rnd];
    }

    //генерируем год
    public static String generateYear() {
        String[] year = {"23", "24", "25", "26"};
        int rnd = new Random().nextInt(year.length);
        return year[rnd];
    }

    //генерируем владельца
    public static String generateCardowner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String cardowner = faker.name().name();
        return cardowner;
    }

    //генерируем cvc
    public static String generateCvc(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String cvc = faker.number().digits(3);
        return cvc;
    }

    //генерируем cvc
    public static int generateYear2(String locale) {
        Faker faker = new Faker(new Locale(locale));
        int cvc = faker.number().numberBetween(23, 26);
        return cvc;
    }

    public static String getYearSysdate(int numberMonth, String ofPattern) {
        LocalDate date = LocalDate.now().plusMonths(numberMonth);
        String year = date.format(DateTimeFormatter.ofPattern("yy"));
        String month = date.format(DateTimeFormatter.ofPattern("MM"));

        String sysdate = null;
        if (ofPattern == "yy") {
            sysdate = year;
        }

        if (ofPattern == "MM"){
            sysdate = month;
        }
        return sysdate;
    }
}
