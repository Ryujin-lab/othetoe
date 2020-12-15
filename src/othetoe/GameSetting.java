package othetoe;

import javax.swing.text.NumberFormatter;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import customComponents.*;

public class GameSetting extends JPanel {
   MyButton backToMainMenu;
   MyButton startGame;
   JFormattedTextField point;

   JComboBox<String> grid;
   JComboBox<String> straight;

   int gridAmmount = 10;
   int straightAmmount = 4;
   public static int pointToWin;

   int[] grids = { 10, 15, 20, 25, 30 };
   int[] straightInt = { 4, 5, 6, 7, 8 };

   String[] gridStr = { "10 x 10", "15 x 15", "20 x 20", "25 x 25", "30 x 30" };
   String[] straightStr = { "4", "5", "6", "7", "8" };

   Font font = MyFont.MyFont();

   static PowerUpPanel p1powers;
   static PowerUpPanel p2powers;

   GameSetting() {

      this.setBounds(320, 0, 600, 600);
      this.setBackground(MyColor.lightBg);
      this.setLayout(null);

      JLabel titleSetting = new JLabel("OTHETOE", SwingConstants.CENTER);
      titleSetting.setFont(font.deriveFont(Font.BOLD, 60));
      titleSetting.setBounds(0, 100, 600, 60);
      titleSetting.setForeground(Color.lightGray);

      JLabel gameSetting = new JLabel("Game Setup", SwingConstants.CENTER);
      gameSetting.setFont(font.deriveFont(Font.BOLD, 20));
      gameSetting.setBounds(0, 160, 600, 60);
      gameSetting.setForeground(Color.lightGray);

      JLabel gridLabel = new JLabel("Grid", SwingConstants.LEFT);
      gridLabel.setFont(font.deriveFont(Font.BOLD, 20));
      gridLabel.setBounds(100, 250, 300, 20);
      gridLabel.setForeground(Color.lightGray);

      JLabel t1 = new JLabel(":", SwingConstants.LEFT);
      t1.setFont(font.deriveFont(Font.BOLD, 20));
      t1.setBounds(300, 250, 300, 20);
      t1.setForeground(Color.lightGray);

      grid = new JComboBox<String>(gridStr);
      grid.setSelectedItem(0);
      grid.setFont(font.deriveFont(Font.BOLD, 15));
      grid.setBounds(320, 240, 200, 30);
      grid.setForeground(Color.lightGray);
      grid.setBackground(MyColor.darkBg);
      grid.setBorder(new LineBorder(MyColor.netral, 1));

      grid.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == grid) {
               Main.se.setFile("res/sound/dropDown.wav");
               Main.se.play();

               int c = grid.getSelectedIndex();
               gridAmmount = grids[c];
            }

         }

      });

      JLabel straightLabel = new JLabel("Straight ", SwingConstants.LEFT);
      straightLabel.setFont(font.deriveFont(Font.BOLD, 20));
      straightLabel.setBounds(100, 290, 300, 20);
      straightLabel.setForeground(Color.lightGray);

      JLabel t2 = new JLabel(":", SwingConstants.LEFT);
      t2.setFont(font.deriveFont(Font.BOLD, 20));
      t2.setBounds(300, 290, 300, 20);
      t2.setForeground(Color.lightGray);

      straight = new JComboBox<String>(straightStr);
      straight.setSelectedItem(0);
      straight.setFont(font.deriveFont(Font.BOLD, 15));
      straight.setBounds(320, 280, 200, 30);
      straight.setForeground(Color.lightGray);
      straight.setBackground(MyColor.darkBg);
      straight.setBorder(new LineBorder(MyColor.netral, 1));

      straight.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == straight) {
               Main.se.setFile("res/sound/dropDown.wav");
               Main.se.play();
               int c = straight.getSelectedIndex();
               straightAmmount = straightInt[c];
            }
         }

      });

      JLabel pointLabel = new JLabel("Point to win ", SwingConstants.LEFT);
      pointLabel.setFont(font.deriveFont(Font.BOLD, 20));
      pointLabel.setBounds(100, 330, 300, 20);
      pointLabel.setForeground(Color.lightGray);

      JLabel t3 = new JLabel(":", SwingConstants.LEFT);
      t3.setFont(font.deriveFont(Font.BOLD, 20));
      t3.setBounds(300, 330, 300, 20);
      t3.setForeground(Color.lightGray);

      NumberFormat integer = NumberFormat.getIntegerInstance();

      NumberFormatter numberFormat = new NumberFormatter(integer);
      numberFormat.setAllowsInvalid(false);

      point = new JFormattedTextField(numberFormat);
      
      point.setText("10");
      point.setBounds(320, 320, 200, 30);
      point.setBackground(MyColor.darkBg);
      point.setForeground(Color.LIGHT_GRAY);
      point.setFont(font.deriveFont(Font.BOLD, 15));
      point.setCaretColor(Color.lightGray);
      point.setBorder(new LineBorder(MyColor.netral, 2));

      backToMainMenu = new MyButton("Main Menu", 50, 400, 200, 50, MyColor.darkOrange, MyColor.lightOrange);
      startGame = new MyButton("Start Game", 350, 400, 200, 50, MyColor.lightPurple, MyColor.darkPurple);

      backToMainMenu.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == backToMainMenu) {

               Main.se.setFile("res/sound/close.wav");
               Main.se.play();

               Main.gameLayer.remove(Main.p1);
               Main.gameLayer.remove(Main.p2);
               Main.gameLayer.remove(Main.gameSetting);
               Main.gameFrame.add(Main.mainMenu);
               Main.gameFrame.repaint();
               Player1Panel.logedIn = false;
               Player1Panel.player = null;

               Player2Panel.logedIn = false;
               Player2Panel.player = null;
            }

         }

      });

      startGame.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == startGame) {
               if (Player1Panel.logedIn && Player2Panel.logedIn) {
                  pointToWin = Integer.parseInt(point.getText());
                  if (pointToWin < 10) {
                     Main.se.setFile("res/sound/alert.wav");
                     Main.se.play();
                     JOptionPane.showMessageDialog(Main.gameFrame, "Minimum point to win is 10",
                           "Point to win less than minimum requirement", JOptionPane.ERROR_MESSAGE);
                  } else {
                     Main.se.setFile("res/sound/open.wav");
                     Main.se.play();
                     try {
                        Thread.sleep(300);
                     } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                     }
                     
                     Main.se.setFile("res/sound/gameStart.wav");
                     Main.se.play();

                     Main.gameLayer.remove(Main.gameSetting);
                     
                     Main.board = new Board(gridAmmount, straightAmmount);
                     Main.gameLayer.add( Main.board);
                     GameFrame.isGameStarted = true;

                     Main.p1.pointLabel = new MyLabel( ""+Player1Panel.player.getPoint(), 0, 310, 300, 100, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN, 100), true);
                     p1powers = new PowerUpPanel(60, 455, 290, 51,1);
                     Main.p1.add(p1powers);
                     Main.p1.add(Main.p1.pointLabel);
                     
                     Main.p2.pointLabel = new MyLabel( ""+Player2Panel.player.getPoint(), 0, 310, 300, 100, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN, 100), true);
                     p2powers = new PowerUpPanel(10, 455, 290, 51,2);
                     Main.p2.add(p2powers);
                     Main.p2.add(Main.p2.pointLabel);

                     MyLabel info1 = new MyLabel("Straight : " + straightAmmount + " | Win Point : " + pointToWin , 10, 420, 280, 20, MyColor.netral, font.deriveFont(Font.PLAIN, 15), true);
                     Main.p1.add(info1);
                     MyLabel info2 = new MyLabel("Straight : " + straightAmmount + " | Win Point : " + pointToWin , 10, 420, 280, 20, MyColor.netral, font.deriveFont(Font.PLAIN, 15), true);
                     Main.p2.add(info2);
                     Main.gameLayer.revalidate();
                     Main.gameLayer.repaint();
                  }

               }
               else{
                  Main.se.setFile("res/sound/alert.wav");
                  Main.se.play();
                  JOptionPane.showMessageDialog(Main.gameFrame, "Need 2 players to loged in", "Player not loged in", JOptionPane.ERROR_MESSAGE );
               }
            }

         }

      });

      add(gameSetting);
      add(titleSetting);
      add(gridLabel);
      add(straightLabel);
      add(pointLabel);
      add(t1);
      add(t2);  
      add(t3);
      add(grid);
      add(straight);
      add(point);
      add(backToMainMenu);
      add(startGame);
   }

}
