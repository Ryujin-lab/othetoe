package othetoe;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import org.jasypt.util.password.BasicPasswordEncryptor;
import DBproccess.SQLite;

import javax.swing.JPanel;
import customComponents.*;

public class Player1Panel extends JPanel {
   String playerName = "Player 1";
   Font font = MyFont.MyFont();
   MyTextBox username;
   MyPassword password;
   MyButton login;
   MyButton register;

   JPanel loginPanel;
   
   public static boolean logedIn = false;
   public static Player player;

   JLabel imageLabel;
   MyLabel nameLabel;
   MyLabel pointLabel;
   MyLabel remainingPoint;


   Player1Panel() {
      this.setBounds(0, 0, 300, 600);
      this.setLayout(null);

      loginPanel = new JPanel();
      loginPanel.setLayout(null);
      loginPanel.setBounds(25, 270, 250, 180);
      loginPanel.setOpaque(false);
      // loginPanel.setBackground(Color.BLUE);

      username = new MyTextBox(0, 0, 250, 30, MyColor.darkBg, MyColor.lightOrange);
      username.setFont(font.deriveFont(Font.PLAIN, 15));
      username.setForeground(Color.lightGray);
      username.setCaretColor(Color.lightGray);
      username.setPlaceHolder("User Id", Color.darkGray, Color.LIGHT_GRAY);

      password = new MyPassword(0, 40, 250, 30, MyColor.darkBg, MyColor.lightOrange);
      password.setFont(font.deriveFont(Font.PLAIN, 15));
      password.setForeground(Color.lightGray);
      password.setCaretColor(Color.lightGray);
      password.setPlaceHolder("Password", Color.darkGray, Color.LIGHT_GRAY);

      JLabel or = new JLabel("Or", SwingConstants.CENTER);
      or.setFont(font.deriveFont(Font.PLAIN, 15));
      or.setForeground(MyColor.lightOrange);
      // or.setOpaque(true);
      or.setBounds(75, 115, 100, 30);

      login = new MyButton("Login", 75, 80, 100, 30, MyColor.darkBg, MyColor.lightOrange);
      login.setFont(font.deriveFont(Font.PLAIN, 15));

      login.setForeground(MyColor.lightOrange);
      login.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == login) {

               if (username.getText().equals("User Id") || username.getText().length() == 0
                     || new String(password.getPassword()).equals("Password")
                     || new String(password.getPassword()).length() == 0) {
                  Main.se.setFile("res/sound/alert.wav");
                  Main.se.play();

                  JOptionPane.showMessageDialog(Main.gameFrame, "User Id or password field still empty", "Empty field",
                        JOptionPane.ERROR_MESSAGE);
                  
               } 
               else {
                  BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
                  String u = username.getText().toLowerCase();
                  String p = new String(password.getPassword());
                  //risman punya
                  ResultSet rs = SQLite.executeQuery("select * from player where userId='" + u + "'");
                  
                  try {
                     while (rs.next()) {
                        if ( encryptor.checkPassword(p, rs.getString("password")) ){
                           // System.out.println("login success");
                           boolean canLogin = true;
                           if (Player2Panel.player != null){
                              if (Player2Panel.player.getUserId().equals(u)){
                                 Main.se.setFile("res/sound/alert.wav");
                                 Main.se.play();
                                 JOptionPane.showMessageDialog(Main.gameFrame, "Player already loged in,\ntry to login with other account!","Player already loged in" ,JOptionPane.ERROR_MESSAGE);
                                 canLogin = false;
                              }
                           }
                           if(canLogin) {
                              Main.se.setFile("res/sound/clickCell.wav");
                              Main.se.play();

                              logedIn = true;
                              player = new Player(u, rs.getString("name") , rs.getString("image"));
                              Main.p1.remove(loginPanel);
                              repaint();
                              
                              imageLabel = new JLabel();
                              imageLabel.setIcon(new ImageIcon( new ImageIcon(player.getUserImage()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH ) ));
                              imageLabel.setBounds(50,50,200,200);
                              
                              nameLabel = new MyLabel(player.getUserName() , 20, 260, 260, 30, MyColor.netral , GameFrame.FONT.deriveFont(Font.PLAIN, 20),true );
                              
                              add(nameLabel);
                              add(imageLabel);
                           }

                           else{
                              Main.se.setFile("res/sound/alert.wav");
                              Main.se.play();
                              JOptionPane.showMessageDialog(Main.gameFrame, "Login failed!,\n Username and password doesn't match","Username password doesn't match" ,JOptionPane.ERROR_MESSAGE);
                           }

                           break;
                        }
                        ////risman sampe sini
                        else{
                           Main.se.setFile("res/sound/alert.wav");
                           Main.se.play();
                           JOptionPane.showMessageDialog(Main.gameFrame, "invalid user id or password!,\ntry to register or input a correct one!", "Error user id or password", JOptionPane.ERROR_MESSAGE);
                        }
                     }
                  } catch (SQLException e) {
                     e.printStackTrace();
                  }
               }
            }
            
         }

      });

      register  = new MyButton("Register", 75 , 140, 100, 30, MyColor.darkBg , MyColor.lightOrange );
      register.setFont(font.deriveFont(Font.PLAIN, 15));

      register.setForeground(MyColor.lightOrange);
      register.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent arg0) {
            Main.se.setFile("res/sound/alert.wav");
            Main.se.play();
            GameFrame.reg = new RegisterFrame();
         }

      } );
      
      loginPanel.add(username);
      loginPanel.add(password);
      loginPanel.add(login);
      loginPanel.add(or);
      loginPanel.add(register);
      this.add(loginPanel);
   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setColor(MyColor.lightBg);
      g2d.fillRect(0, 0, 300, 600);
      g2d.setColor(MyColor.darkBg);
      g2d.fillOval(50, 50, 200, 200);

      g2d.setColor(MyColor.lightOrange);
      g2d.drawOval(50, 50, 200, 200);

      g2d.drawOval(45, 45, 210, 210);
      
      g2d.drawLine(299, 0, 299, 600);
      g2d.drawLine(0, 0, 300, 0);
      g2d.drawLine(0, 0, 0, 600);
      g2d.drawLine(0, 599, 300, 599);
      
      g2d.drawRect(0,0,100,10);
      g2d.drawRect(0, 0, 50, 5);

      g2d.drawRect(295, 0, 5, 50);
      g2d.drawRect(290, 0, 9, 100);

      g2d.drawRect(0, 550, 5, 50);
      g2d.drawRect(0, 500, 9, 100);

      g2d.drawRect(250, 595, 50, 5);
      g2d.drawRect(200, 590, 100, 10);

      g2d.drawLine(10, 445, 300-10, 445);
      g2d.drawLine(100, 450, 50+250, 450);
      g2d.drawLine(50, 520, 50+250, 520);

      g2d.drawOval(60, 460, 50, 50);
      g2d.drawOval(120, 460, 50, 50);
      g2d.drawOval(180, 460, 50, 50);
      g2d.drawOval(240, 460, 50, 50);

      if (logedIn){
         g2d.drawLine(20, 290, 300-20, 290);
      }

   }

}
