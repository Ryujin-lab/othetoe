package othetoe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import org.jasypt.util.password.BasicPasswordEncryptor;

import DBproccess.*;
import customComponents.*;

public class RegisterPanel extends JPanel {
   MyTextBox username;
   MyTextBox name;
   MyPassword pass;
   MyPassword confirmPass;
   MyButton register;
   MyButton cancel;

   public static String avatarPath = "res/avatar/1.png";
   public static JLabel avatar;
   public static MyAvatar[] avatarChooser = new MyAvatar[10];

   RegisterPanel() {
      setSize(new Dimension(600, 600));
      setLayout(null);

      username = new MyTextBox(150, 300, 300, 30, MyColor.darkBg, MyColor.netral);
      name = new MyTextBox(150, 340, 300, 30, MyColor.darkBg, MyColor.netral);
      pass = new MyPassword(150, 380, 300, 30, MyColor.darkBg, MyColor.netral);
      confirmPass = new MyPassword(150, 420, 300, 30, MyColor.darkBg, MyColor.netral);

      Font font = MyFont.MyFont().deriveFont(Font.PLAIN, 15);
      username.setFont(font);
      name.setFont(font);
      pass.setFont(font);
      confirmPass.setFont(font);

      username.setPlaceHolder("Username", Color.darkGray, Color.lightGray);
      name.setPlaceHolder("Name", Color.darkGray, Color.lightGray);
      pass.setPlaceHolder("Password", Color.darkGray, Color.lightGray);
      confirmPass.setPlaceHolder("Confirm Password", Color.darkGray, Color.lightGray);

      username.setCaretColor(Color.lightGray);
      name.setCaretColor(Color.lightGray);
      pass.setCaretColor(Color.lightGray);
      confirmPass.setCaretColor(Color.lightGray);

      cancel = new MyButton("Cancel", 150, 460, 145, 40, MyColor.darkOrange, MyColor.lightOrange);
      cancel.setFont(font);
      cancel.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == cancel) {
               Main.se.setFile("res/sound/close.wav");
               Main.se.play();
               GameFrame.reg.dispose();
            }
         }

      });

      register = new MyButton("Register!", 600 - 150 - 145, 460, 145, 40, MyColor.lightPurple, MyColor.darkPurple);
      register.setFont(font);

      register.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == register) {
               //rismen punya
               String u = username.getText().toLowerCase();
               String n = name.getText();
               String p1 = new String(pass.getPassword());
               String p2 = new String(confirmPass.getPassword());
               boolean isUsernameValid = true;
               boolean isPasswordValid = false;
               int rowCount = 0;

               //risman disini
               ResultSet rs = SQLite.executeQuery("select * from player where userId = '" + u + "'");

               try {
                  while (rs.next()) {
                     rowCount++;
                  }
               } catch (SQLException e) {
                  e.printStackTrace();
               }

               if (rowCount > 0 || u.contains(" ")){
                  isUsernameValid = false;
                  JOptionPane.showMessageDialog(GameFrame.reg, "Username doesn't have space", "Invalid username", JOptionPane.WARNING_MESSAGE );
               }

               if (isUsernameValid){
                  if (p1.equals(p2)){
                     isPasswordValid = true;
                  }
                  else{
                     JOptionPane.showMessageDialog(GameFrame.reg, "Register failed", "Password doesnt match!", JOptionPane.WARNING_MESSAGE );
                  }
               }

               if (isPasswordValid && isUsernameValid){
                  Main.se.setFile("res/sound/clickCell.wav");
                  Main.se.play();
                  
                  BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
                  String encriptedPass = encryptor.encryptPassword(p1);
                  SQLite.insert(u, encriptedPass, n, avatarPath, 0, 0, 0);
                  JOptionPane.showMessageDialog(GameFrame.reg, "Your profile has been saved to database!,\nenjoy the game!", "Register Success", JOptionPane.INFORMATION_MESSAGE );
                  GameFrame.reg.dispose();
               }

               //sampe sini

               else{
                  Main.se.setFile("res/sound/alert.wav");
                  Main.se.play();
                  JOptionPane.showMessageDialog(GameFrame.reg, "Invalid Username or Password!", "Resgister failed", JOptionPane.ERROR_MESSAGE);
               }
               
            }

         }

      } );

      avatar = new JLabel();

      avatar.setBounds(200,10,200,200);
      avatar.setOpaque(false);
      avatar.setIcon(new ImageIcon( new ImageIcon(avatarPath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)  ));
      add(avatar);

      for (int i = 0; i < 10; i++) {
         int tmp = i + 1;
         avatarChooser[i] = new MyAvatar("res/avatar/" + tmp + ".png", 50 + (i * 50), 220, 50, 50);
         add(avatarChooser[i]);
      }
      
      add(register);
      add(cancel);
      add(username);
      add(name);
      add(pass);
      add(confirmPass);
   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponents(g);

      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(MyColor.lightBg);
      g.fillRect(0, 0, 600, 600);

      g.setColor(MyColor.netral);
      g.drawOval(200, 10, 200, 200);
      g.drawOval(195, 5, 210, 210);

      // g.drawImage(image, 0, 0, null, null);
   }
}