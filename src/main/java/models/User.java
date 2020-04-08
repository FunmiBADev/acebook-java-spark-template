package models;

import java.sql.Timestamp;
import java.util.UUID;

public class User {
    private UUID user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public User(UUID user_id, String first_name, String last_name, String email, String password) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }
}
