package ru.netology.database;

import lombok.SneakyThrows;

import java.sql.*;

public class Database {

    //пустой конструктор
    private Database() {

    }

    @SneakyThrows
    //получаем статус (одобрен или не одобрен)
    public static String getStatus() {
        String dataSQLStatus = "SELECT status FROM payment_entity WHERE true;";
        String status = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                //создаём консоль, через которую будем создавать запрос к БД
                PreparedStatement statusStatment = connection.prepareStatement(dataSQLStatus);
        ) {
            try (ResultSet rs = statusStatment.executeQuery()) {
                if (rs.next()) {  //определяем есть ли следующая строка в возвращённом из селекта результате
                    status = rs.getString("status");  //сохраняем полученный статус
                }
            }
        }
        return status;
    }

    @SneakyThrows
    //получаем статус (одобрен или не одобрен)
    public static String getAmount() {
        String dataSQLStatus = "SELECT amount FROM payment_entity WHERE true;";
        String amount = null;
        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
                //создаём консоль, через которую будем создавать запрос к БД
                PreparedStatement amountStatment = connection.prepareStatement(dataSQLStatus);
        ) {
            try (ResultSet rs = amountStatment.executeQuery()) {
                if (rs.next()) {  //определяем есть ли следующая строка в возвращённом из селекта результате
                    amount = rs.getString("status");  //сохраняем полученный статус
                }
            }
        }
        return amount;
    }

    @SneakyThrows
    //очищаем БД
    public static void cleanDatabase() {
        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity WHERE TRUE;";
        String deletePaymentEntity = "DELETE FROM payment_entity WHERE TRUE;";
        String deleteOrderEntity = "DELETE FROM order_entity WHERE TRUE;";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
             Statement deleteCreditRequestEntityStatment = connection.createStatement();
             Statement deletePaymentEntityStatment = connection.createStatement();
             Statement deleteOrderEntityStatment = connection.createStatement();
        ) {
            deleteCreditRequestEntityStatment.executeUpdate(deleteCreditRequestEntity);
            deletePaymentEntityStatment.executeUpdate(deletePaymentEntity);
            deleteOrderEntityStatment.executeUpdate(deleteOrderEntity);
        }
    }

}
