package othetoe;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import DBproccess.SQLite;
import customComponents.BackButton;
import customComponents.CloseButton;
import customComponents.LeftButton;
import customComponents.MyButton;
import customComponents.MyColor;
import customComponents.MyLabel;
import customComponents.RightButton;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaderboardPanel extends JPanel {
   JPanel lead;
   int maxPage = 0;
   int pageOffset = 1;
   int page = 0;

   public LeaderboardPanel() {
      setBounds(0, 30, 1240, 600);
      setBackground(MyColor.darkBg);
      setLayout(null);
      BackButton c = new BackButton(1200, 10, 30, 30);
      c.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent arg0) {
            if (arg0.getSource() == c) {
               Main.se.setFile("res/sound/close.wav");
               Main.se.play();
               Main.gameFrame.remove(Main.leaderboardPanel);
               Main.gameFrame.repaint();
               Main.gameFrame.add(Main.mainMenu);
            }
         }

      });

      MyLabel title = new MyLabel("Leaderboard", 20, 10, 1200, 30, MyColor.netral,
            GameFrame.FONT.deriveFont(Font.BOLD, 30));

      lead = new JPanel();
      lead.setBounds(20, 50, 1180, 500);
      lead.setBackground(MyColor.darkBg);

      ResultSet rsCount = SQLite.executeQuery("select count(*) from player");
      int rows = 0;
      try {
         rows = rsCount.getInt(1) ;
      } catch (SQLException e) {
         e.printStackTrace();
      }

      if(rows%5 == 0){
         maxPage = rows/5;
      }
      else {
         maxPage = (rows/5) + 1;
      }

      try {
         fillTable();
      } catch (Exception e) {
         e.printStackTrace();
      }

      RightButton r = new RightButton (1240-345, 600-40, 30, 30);
      LeftButton l = new LeftButton (345, 600-40, 30, 30);
      l.setEnabled(false);

      r.addActionListener(new ActionListener(){

         @Override
         public void actionPerformed(ActionEvent e) {
            if(e.getSource() == r){
               if (page<maxPage-1){
                  page++;
                  Main.se.setFile("res/sound/clickCell.wav");
                  Main.se.play();
                  try {
                     fillTable();
                  } catch (SQLException e1) {
                     e1.printStackTrace();
                  }

                  if(page>0 && maxPage>1 && !l.isEnabled()){
                     l.setEnabled(true);
                  }
               }
               if(page== maxPage-1){
                  r.setEnabled(false);
               }
            }
         }

      });

      l.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {
            if(e.getSource() == l){
               if (page>0){
                  page--;
                  Main.se.setFile("res/sound/clickCell.wav");
                  Main.se.play();
                  try {
                     fillTable();
                  } catch (SQLException e1) {
                     e1.printStackTrace();
                  }
                  if(page<maxPage && maxPage>1 && !r.isEnabled()){
                     r.setEnabled(true);
                  }
               }
               if(page== 0){
                  l.setEnabled(false);
               }
            }
         }

      });
      add(l);
      add(r);
      add(lead);
      add(title);
      add(c);
   }

   void fillTable() throws SQLException {
      lead.removeAll();
      repaint();
      int i = 0;
      // risman punya
      ResultSet rsLead = SQLite.executeQuery("select * from player order by length(wins) desc, wins desc, point DESC limit "+ (page*5) +",5" );
      while (rsLead.next()) {
         lead.add(new LeadList( rsLead.getString("userId"), 
                              rsLead.getString("name"), 
                              rsLead.getString("image"), 
                              rsLead.getInt("plays"), 
                              rsLead.getInt("wins"), 
                              rsLead.getInt("point"), i));
         i++;
      }
      //sampe sini
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(MyColor.netral);
      g.drawLine(20, 45, 1200, 45);
   }
}
