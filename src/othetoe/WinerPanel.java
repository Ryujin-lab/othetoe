package othetoe;

import javax.swing.*;

import DBproccess.SQLite;
import customComponents.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;


public class WinerPanel extends JPanel{
   public Player winer;
   WinerPanel(Player player){
      winer = player;
      this.setBounds(-1,-1,1241,601);
      this.setOpaque(false);
      this.setLayout(null);

      MyLabel winLabel = new MyLabel(winer.getUserName().toUpperCase() + " WON!", 0, 200, 1240,60, MyColor.netral, MyFont.MyFont().deriveFont(Font.BOLD, 50) ,true);
      MyLabel pts = new MyLabel("SCORE : "+ winer.getPoint(), 0, 260, 1240,40, MyColor.netral, MyFont.MyFont().deriveFont(Font.BOLD, 30) ,true);
      MyButton quit = new MyButton( "Quit Game",300,320, 1240-600,50 , MyColor.darkOrange, MyColor.lightOrange); 

      quit.addActionListener(
         new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
               if (arg0.getSource() == quit){

                  Main.se.setFile("res/sound/close.wav");
                  Main.se.play();
                  
                  ///////////////////////// risman punya
                  updatePlayerData( Player1Panel.player , Player1Panel.player == winer);
                  updatePlayerData( Player2Panel.player , Player2Panel.player == winer);
                  SQLite.executeUpdate("INSERT into history (date, winnerPlayerId, player1Id, player2Id, p1Point, p2Point, pointToWin)  VALUES  ('"+Board.date+"', '"+winer.getUserId()+"', '"+Player1Panel.player.getUserId()+"', '"+Player2Panel.player.getUserId()+"', '"+Player1Panel.player.getPoint()+"', '"+Player2Panel.player.getPoint()+"', '"+GameSetting.pointToWin+"'   )");
                  // //////// risman sampe sini

                  Main.gameLayer.removeAll();
                  Main.gameFrame.repaint();

                  Player1Panel.logedIn = false;
                  Player2Panel.logedIn = false;
                  
                  Player1Panel.player = null;
                  Player2Panel.player = null;
   
                  GameFrame.isGamePaused = false;
                  GameFrame.isGameStarted = false;
                  GameFrame.isGameEnd = false;
                  Main.gameFrame.add(Main.mainMenu);
               }

            }

         }
      );

      add(winLabel);
      add(pts);
      add(quit);

   }
   
   public void updatePlayerData(Player player, Boolean isWin){
      String uid = player.getUserId();
      int wins = 0, plays =0 , point = 0;
      ResultSet rs = SQLite.executeQuery("select * from player where userId='" + uid + "'");
      try {
         while(rs.next()){
            wins = rs.getInt("wins") + ( isWin? 1 : 0 );
            plays = rs.getInt("plays") + 1;
            point = rs.getInt("point") + player.getPoint();
         }
      } catch (Exception e) { e.printStackTrace();  }
      SQLite.executeUpdate("update player set wins='"+ wins+"',plays = '"+plays+"', point ='"+point+"' where userId = '"+uid+"'");
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      g2d.setColor(new Color(30,30,30,200));
      g2d.fillRect(0, 0, 1241, 601);

      g2d.setColor(MyColor.lightBg);
      g2d.fillRect(0, 150, 1240, 300);

      g2d.setColor(MyColor.netral.darker());
      g2d.drawRect(1, 150, 1239, 300);

      //top left
      g2d.drawRect(1, 150, 50, 50);
      g2d.drawRect(1, 150, 200, 25);
      g2d.drawRect(1, 150, 10, 100);

      //top right
      g2d.drawRect(1240-50, 150, 50, 50);
      g2d.drawRect(1240-200, 150, 200, 25);
      g2d.drawRect(1240-10, 150, 10, 100);

      //bottom right
      g2d.drawRect(1240-50, 150+300-50, 50, 50);;
      g2d.drawRect(1, 150+300-25, 200, 25);
      g2d.drawRect(1, 150+300-100, 10, 100);

      //bottom left
      g2d.drawRect(1, 150+300-50, 50, 50);
      g2d.drawRect(1240-200, 150+300-25, 200, 25);
      g2d.drawRect(1240-10, 150+300-100, 10, 100);
   }
}
