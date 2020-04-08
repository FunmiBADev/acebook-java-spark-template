package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model, UserModel {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;

    }

    @Override
    public UUID createPost(String title, String content, Timestamp post_date) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postUuid = UUID.randomUUID();

            conn.createQuery("insert into posts(post_id, title, content, post_date) VALUES (:post_id, :title, :content, :post_date)")
                    .addParameter("post_id", postUuid)
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("post_date", post_date)
                    .executeUpdate();
            conn.commit();
            return postUuid;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> items = conn.createQuery("select * from posts ORDER BY post_date DESC LIMIT 10")
                    .executeAndFetch(Post.class);

            return items;
        }

    }

    @Override
    public UUID userSignup(String first_name, String last_name, String email, String password) {
        UUID userUuid;
        try (Connection conn = sql2o.beginTransaction()) {
            userUuid = UUID.randomUUID();

            conn.createQuery("insert into users(user_id, first_name, last_name, email, password) VALUES (:user_id, :first_name, :last_name, :email, :password)")
                    .addParameter("user_id", userUuid)
                    .addParameter("first_name", first_name)
                    .addParameter("last_name", last_name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();
        }
        return userUuid;
    }

//    @Override
//    public UUID userLogin(String email, String password) {
//        UUID userUuid;
//        try (Connection conn = sql2o.beginTransaction()) {
//            userUuid = UUID.randomUUID();
//
//            conn.createQuery("SELECT users(user_id,  email, password) VALUES (:user_id, :email, :password)")
//                    .addParameter("user_id", userUuid)
//                    .addParameter("email", email)
//                    .addParameter("password", password)
//                    .executeUpdate();
//            conn.commit();
//        }
//        return userUuid;
//    }

    @Override
    public boolean userLogin(String email, String password) {
        boolean existing_user = false;
        try (Connection conn = sql2o.beginTransaction()) {
            List<User> users = conn.createQuery("SELECT password FROM users WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetch(User.class);
            password = "[User(id=null, first_name=null, last_name=null, email=null, password=\"+password+\")]";
            if(users.toString().equals(password)){
                existing_user = true;
            };
        }
        return existing_user;
        }

//    String SQL = "SELECT * FROM users WHERE users_name='" + name + "' && users_password='" + password+ "'";
//
//    ResultSet rs = stmt.executeQuery(SQL);
//
//    // Check Username and Password
//    while (rs.next()) {
//        databaseUsername = rs.getString("users_name");
//        databasePassword = rs.getString("users_password");
//    }
//
//    if (name.equals(databaseUsername) && password.equals(databasePassword)) {
//        System.out.println("Successful Login!\n----");
//    } else {
//        System.out.println("Incorrect Password\n----");
//    }

//    @Override
//    public void likePosts(UUID id) {
//
//
//    }

    }
