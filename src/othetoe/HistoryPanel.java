package othetoe;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import DBproccess.SQLite;
import customComponents.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryPanel extends JPanel {
   JPanel his;
   int page = 0;
   int maxPage = 0;
   public HistoryPanel() throws SQLException {
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
               Main.gameFrame.remove(Main.historyPanel);
               Main.gameFrame.repaint();
               Main.gameFrame.add(Main.mainMenu);
            }
         }

      });

      MyLabel title = new MyLabel("Game History", 20, 10, 1200, 30, MyColor.netral,
            GameFrame.FONT.deriveFont(Font.BOLD, 30));
      
      his = new JPanel();
      his.setBounds(20, 50, 1180, 500);
      his.setBackground(MyColor.darkBg);
      ResultSet rsCount = SQLite.executeQuery("select count(*) from history");
      int rows = rsCount.getInt(1);
      if(rows%10 == 0){
         maxPage = rows/10;
      }
      else {
         maxPage = (rows/10) + 1;
      }

      fillTable(page, rows);

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
                     fillTable(page, rows);
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
                     fillTable(page, rows);
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
      
      add(r);
      add(l);
      add(his);
      add(title);
      add(c);
   }

   void fillTable(int page, int rows) throws SQLException {
      his.removeAll();
      repaint();
      //risman punya
      ResultSet rsHis = SQLite.executeQuery("select * from history where historyId <= "+(rows-(10*page))+" order by length(historyId) desc, historyId desc limit 10");
      int i = 0;
      while (rsHis.next()) {
         his.add(new HisList( rsHis.getString("player1Id") , 
                              rsHis.getString("player2Id"), 
                              rsHis.getInt("p1Point"), 
                              rsHis.getInt("p2Point"), 
                              rsHis.getString("winnerPlayerId"), 
                              rsHis.getInt("pointToWin"), 
                              rsHis.getString("date"), i
                              ));
         i++;
      }

      // sampe sini;
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(MyColor.netral);
      g.drawLine(20, 45, 1200, 45);
   }
}
