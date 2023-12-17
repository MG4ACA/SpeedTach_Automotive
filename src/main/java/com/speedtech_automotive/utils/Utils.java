package com.speedtech_automotive.utils;


import com.speedtech_automotive.db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class Utils {

    private static PreparedStatement getPreparedStatement(String sql, Object... args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1),args[i]);
        }
        return pstm;
    }

    public static boolean executeUpdate(String sql, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatement(sql,args).executeUpdate()>0;
    }

    public static String executeUpdateWithReturnId(String sql, Object... args) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1),args[i]);
        }
        int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getString(1);
            }
        }
        return null;
    }

    public static ResultSet executeQuery(String sql, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatement(sql,args).executeQuery();
    }

    public static void setUi(String url, Pane impPane){
        try {
            Pane pane = FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource("/view/" + url + ".fxml")));
            impPane.getChildren().setAll(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
