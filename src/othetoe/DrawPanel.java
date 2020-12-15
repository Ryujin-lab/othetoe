package othetoe;

import javax.swing.*;

import DBproccess.SQLite;
import customComponents.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class DrawPanel extends JPanel{
   DrawPanel(){
      this.setBounds(0,0,1241,601);
      this.setOpaque(false);
      this.setLayout(null);

      MyLabel winLabel = new MyLabel("GAME DRAW!", 0, 200, 1240,60, MyColor.netral, MyFont.MyFont().deriveFont(Font.BOLD, 50) ,true);
      MyButton quit = new MyButton( "Quit Game",300,320, 1240-600,50 , MyColor.darkOrange, MyColor.lightOrange); 

      quit.addActionListener(
         new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
               if (arg0.getSource() == quit){
                  Main.se.setFile("res/sound/close.wav");
                  Main.se.play();
                  
                  //risman 
                  Player p1 = Player1Panel.player;
                  Player p2 = Player2Panel.player;
                  updatePlayerData(p1, false);
                  updatePlayerData(p2, false);
                  SQLite.executeUpdate("INSERT into history (date, player1Id, player2Id, p1Point, p2Point, pointToWin)  VALUES  ('"+Board.date+"', '"+p1.getUserId()+"', '"+p2.getUserId()+"','"+p1.getPoint()+"','"+p2.getPoint()+"','"+GameSetting.pointToWin+"')");
                  //risman sampe sini 
                  
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
      // TODO Auto-generated method stub
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;

      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      
      g2d.setColor(new Color(30,30,30,200));
      g2d.fillRect(0, 0, 1241, 601);

      g2d.setColor(MyColor.lightBg);
      g2d.fillRect(0, 150, 1240, 300);

      g2d.setColor(MyColor.netral.darker());
      g2d.drawRect(1, 150, 1239, 300);

      // top left
      g2d.drawRect(1, 150, 50, 50);
      g2d.drawRect(1, 150, 200, 25);
      g2d.drawRect(1, 150, 10, 100);

      // top right
      g2d.drawRect(1240-50, 150, 50, 50);
      g2d.drawRect(1240-200, 150, 200, 25);
      g2d.drawRect(1240-10, 150, 10, 100);

      // bottom right
      g2d.drawRect(1240-50, 150+300-50, 50, 50);;
      g2d.drawRect(1, 150+300-25, 200, 25);
      g2d.drawRect(1, 150+300-100, 10, 100);

      // bottom left
      g2d.drawRect(1, 150+300-50, 50, 50);
      g2d.drawRect(1240-200, 150+300-25, 200, 25);
      g2d.drawRect(1240-10, 150+300-100, 10, 100);
   }
}
