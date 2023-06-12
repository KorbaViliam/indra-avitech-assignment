package com.vkorba.persistance;

public final class SqlUtils {
    private SqlUtils() {
        // private constructor to prevent instantiation
    }

    public static String getCreateTableUsersScript() {
        return "CREATE TABLE IF NOT EXISTS SUSERS (" +
                "ID INT AUTO_INCREMENT," +
                "GUID UUID DEFAULT RANDOM_UUID()," +
                "NAME VARCHAR(255)" +
                ")";
    }

    public static String getInsertUserScript() {
        return "INSERT INTO SUSERS (NAME) VALUES (?)";
    }

    public static String getSelectAllUsersScript() {
        return "SELECT * FROM SUSERS";
    }

    public static String getDeleteAllUsersScript() {
        return "DELETE FROM SUSERS";
    }
}

