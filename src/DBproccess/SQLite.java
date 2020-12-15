package DBproccess;

import java.sql.*;

// import java.sql.SQLException;

public class SQLite {
   private static final Connection CONN = connect();

   static Connection connect() {
      Connection conn = null;
      String url = "jdbc:sqlite:data/othetoe.db";
      try {
         conn = DriverManager.getConnection(url);
         // System.out.println("Connection enstablished");
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
      return conn;
   }

   public static void insert(String userId, String pass, String name, String image, int plays, int wins, int points) {
      String query = "insert into player values(?,?,?,?,?,?,?)";
      try {
         PreparedStatement ps = CONN.prepareStatement(query);
         ps.setString(1, userId);
         ps.setString(2, pass);
         ps.setString(3, name);
         ps.setString(4, image);
         ps.setInt(5, plays);
         ps.setInt(6, wins);
         ps.setInt(7, points);
         ps.executeUpdate();
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
   }

   public static ResultSet executeQuery(String query) {
      ResultSet res = null;
      try {
         Statement stt = CONN.createStatement();
         res = stt.executeQuery(query);
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return res;
   }

   public static void executeUpdate(String query) {
      
      try {
         Statement stt = CONN.createStatement();
         stt.executeUpdate(query);
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

}
