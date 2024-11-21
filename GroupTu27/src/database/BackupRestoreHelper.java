package database;

import java.io.File;

import services.UserService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The Backup-Restore Helper handles the backup and restoration of the articles in the database
 */

public class BackupRestoreHelper {


    private static final String DB_URL = "jdbc:h2:./database/help_system";
    private static final String BACKUP_DIR = "./backups/";

    // Backup the database by copying the database file
    public void backupDatabase(String backupFileName) throws IOException, SQLException {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            backupDir.mkdir(); // Create the backup directory if it doesn't exist
        }

        String backupFilePath = BACKUP_DIR + backupFileName;

        // Perform backup operation using H2 database command
        try (Connection connection = DriverManager.getConnection(DB_URL, "sa", "")) {
            String backupCommand = "BACKUP TO '" + backupFilePath + "'";
            connection.createStatement().execute(backupCommand);
        }
        System.out.println("Backup completed: " + backupFilePath);
    }

    // Restore the database from a backup file
    public void restoreDatabase(String backupFileName) throws IOException, SQLException {
        String backupFilePath = BACKUP_DIR + backupFileName;

        File backupFile = new File(backupFilePath);
        if (!backupFile.exists()) {
            throw new IOException("Backup file does not exist: " + backupFilePath);
        }

        // Perform restore operation using H2 database command
        try (Connection connection = DriverManager.getConnection(DB_URL, "sa", "")) {
            String restoreCommand = "RESTORE FROM '" + backupFilePath + "'";
            connection.createStatement().execute(restoreCommand);
        }
        System.out.println("Database restored from: " + backupFilePath);
    }
}
