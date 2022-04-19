package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    private static final String url = "jdbc:mysql://localhost:3306/app";
    //private static final String url = "jdbc:postgresql://localhost:5432/app";
    private static final String user = "app";
    private static final String password = "pass";

    @SneakyThrows
    public static void cleanData() {
        var queryRunner = new QueryRunner();
        var cleanOrder = "DELETE FROM order_entity";
        var cleanPayment = "DELETE FROM payment_entity";
        var cleanRequest = "DELETE FROM credit_request_entity";

        try (
                var connection = DriverManager.getConnection(url, user, password)
        ) {
            queryRunner.update(connection, cleanOrder);
            queryRunner.update(connection, cleanPayment);
            queryRunner.update(connection, cleanRequest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static String getStatus(String query) {
        var queryRunner = new QueryRunner();
        var status = "";
        try (
                var connection = DriverManager.getConnection(url, user, password)
        ) {
            status = queryRunner.query(connection, query, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var query = "SELECT status FROM payment_entity";
        return getStatus(query);
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var query = "SELECT status FROM credit_request_entity";
        return getStatus(query);
    }
}
