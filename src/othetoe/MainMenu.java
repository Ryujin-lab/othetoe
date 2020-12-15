package othetoe;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import customComponents.*;

public class MainMenu extends JPanel {
   MyButton gameStart;
   MyButton showHistory;
   MyButton showLeaderboard;
   Image title = new ImageIcon("res/title.png").getImage();

   MainMenu() {
      // this.setSize(400,350);
      this.setBounds(0, 30, 1240, 600);

      this.setBackground(MyColor.darkBg);
      this.setFocusable(true);
      this.setLayout(null);

      JLabel title = new JLabel("MAIN MENU");
      title.setFont(MyFont.MyFont().deriveFont(Font.BOLD, 50));
      title.setForeground(Color.LIGHT_GRAY);
      title.setBounds(920, 130, 400, 100);

      gameStart = new MyButton("Game Start", 920, 225, 200, 50, MyColor.darkOrange, MyColor.darkPurple);
      showHistory = new MyButton("History", 920, 285, 200, 50, MyColor.darkOrange, MyColor.darkPurple);
      showLeaderboard = new MyButton("Leaderboard", 920, 345, 200, 50, MyColor.darkOrange, MyColor.darkPurple);

      this.add(title);
      this.add(gameStart);
      this.add(showHistory);
      this.add(showLeaderboard);

      Main.se.setFile("res/sound/opening.wav");
      Main.se.play();

      gameStart.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == gameStart) {
               // MySound.play(MySound.OPEN, false);
               Main.se.setFile("res/sound/open.wav");
               Main.se.play();

               Main.gameFrame.remove(Main.mainMenu);
               Main.gameFrame.repaint();

               Main.p1 = new Player1Panel();
               Main.p2 = new Player2Panel();
               Main.gameSetting = new GameSetting();

               Main.gameLayer.add(Main.gameSetting);
               Main.gameLayer.add(Main.p1);
               Main.gameLayer.add(Main.p2);

               Main.gameFrame.add(Main.gameLayer);

            }

         }

      });

      showHistory.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == showHistory) {
               // MySound.play(MySound.OPEN, false);
               Main.se.setFile("res/sound/open.wav");
               Main.se.play();

               Main.gameFrame.remove(Main.mainMenu);
               Main.gameFrame.repaint();

               try {
                  Main.historyPanel = new HistoryPanel();
               } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               Main.gameFrame.add(Main.historyPanel);
            }

         }
         
      });
      
      showLeaderboard.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent arg0) {
            // MySound.play(MySound.OPEN, false);
            Main.se.setFile("res/sound/open.wav");
            Main.se.play();

            Main.gameFrame.remove(Main.mainMenu);
            Main.gameFrame.repaint();

            Main.leaderboardPanel = new LeaderboardPanel();
            Main.gameFrame.add(Main.leaderboardPanel);
         }

      });

   }
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(title, 0, 0, null);
      g.setColor(MyColor.darkBg);
      g.fillRect(900, 0, 340, 600);
   }

}
