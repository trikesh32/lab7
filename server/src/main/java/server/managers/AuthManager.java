package server.managers;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;
import org.postgresql.util.PSQLException;
import server.App;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Logger;

public class AuthManager {
    private final DataSource dataSource;
    private final int SALT_LENGTH = 10;
    private final String pepper;
    private final Logger logger = App.logger;

    public AuthManager(DataSource dataSource, String pepper) {
        this.dataSource = dataSource;
        this.pepper = pepper;
    }

    public int registerUser(String login, String password) throws SQLException {
        var salt = generateSalt();
        var passwordHash = generatePasswordHash(password, salt);
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users(name, password_hash, salt) VALUES " +
                "(?, ?, ?)");
        stmt.setString(1, login);
        stmt.setString(2, passwordHash);
        stmt.setString(3, salt);
        try {
            stmt.executeUpdate();
        } catch (PSQLException e){
            if (e.getMessage().startsWith("ERROR: duplicate key")){
                return -1;
            }
        }
        stmt = conn.prepareStatement("SELECT id FROM users WHERE name=?");
        stmt.setString(1, login);
        ResultSet res = stmt.executeQuery();
        res.next();
        conn.close();
        return res.getInt("id");
    }

    public int authenticateUser(String login, String password) throws SQLException {
        var conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        stmt.setString(1, login);
        ResultSet res = stmt.executeQuery();
        if (res.next()) {
            var exceptedPassword = res.getString(3);
            var foundSalt = res.getString(4);
            var passwordHash = generatePasswordHash(password, foundSalt);
            conn.close();
            if (exceptedPassword.equals(passwordHash)) {
                return res.getInt(1);
            } return -1;
        }
        return 0;
    }

    private String generateSalt() {
        return RandomStringUtils.randomAlphabetic(SALT_LENGTH);
    }

    private String generatePasswordHash(String password, String salt) {
        return Hashing.sha1().hashString(pepper + password + salt, StandardCharsets.UTF_8).toString();
    }
}
