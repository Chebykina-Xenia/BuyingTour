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

    public static String getCardNumber(int key) {
        String cardNumber = "";
        if (key == 1) {
            cardNumber = "1111222233334444";  //Approved
        }
        if (key == 0) {
            cardNumber = "5555666677778888";   //Declined
        }
        return cardNumber;
    }

    //генерируем месяц
    public static String generateMonth() {
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int rnd = new Random().nextInt(month.length);
        return month[rnd];
    }

    //генерируем строку из символов
    public static String generateSymbols() {
        String[] symbols = {"$%^&", "$$$", "/.", "(*("};
        int rnd = new Random().nextInt(symbols.length);
        return symbols[rnd];
    }

    //генерируем владельца
    public static String generateCardowner() {
        String cardowner = faker.name().name();
        return cardowner;
    }

    //генерируем 1 букву
    public static String generateLetter(int length) {
        String letter = faker.random().hex(length);
        return letter;
    }

    //генерируем 1 букву
    public static String generateRU() {
        Faker faker2 = new Faker(new Locale("ru"));
        String textRU = faker2.name().name();
        return textRU;
    }

    //генерируем cvc
    public static String generateCvc() {
        String cvc = faker.number().digits(3);
        return cvc;
    }

    //генерируем число
    public static String generateNumber(int numberLenght) {
        String number = faker.number().digits(numberLenght);
        return number;
    }

    public static String getYearSysdate(int numberMonth, String ofPattern) {
        return LocalDate.now().plusMonths(numberMonth).format(DateTimeFormatter.ofPattern(ofPattern));
    }

    //генерируем строку на англ
    public static String generateStringEN(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(53);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
