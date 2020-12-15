package othetoe;

import javax.swing.*;

import DBproccess.SQLite;

import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import customComponents.*;

public class Cell extends JButton implements ActionListener {
   private int xpos, ypos, size;
   public Player player = null;
   public PowerUp powerUp = null;
   boolean isActive = false;
   boolean marked = false;
   Color markColor = null;

   Random random = new Random();

   Cell(int xpos, int ypos, int size) {
      setSize(size, size);
      this.xpos = xpos;
      this.ypos = ypos;
      this.size = size;
      addActionListener(this);
      setBorder(null);
   }

   public int getXpos() {
      return xpos;
   }

   public int getYpos() {
      return ypos;
   }

   @Override
   public void paintComponent(Graphics g) {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(MyColor.lightBg);
      g.fillRect(0, 0, size, size);
      if(marked){
         g.setColor(markColor);
         g.drawRect(1, 1, size - 3, size - 3);
         g.drawRect(2, 2, size - 5, size - 5);
      }
      else{
         g.setColor(Color.gray);
         g.drawRect(1, 1, size - 3, size - 3);
      }
      
      if (isActive) {

         if( Board.powerupState == 3 && getModel().isRollover()){
            if (Board.playerTurn == Player1Panel.player) {
               g.drawImage(Board.hoverPowerUndo1, 3,3,size-6, size-6,null);
            } 
            else if (Board.playerTurn == Player2Panel.player) {
               g.drawImage(Board.hoverPowerUndo2, 3,3,size-6, size-6,null);
            }
         }
         else{
            if (player == Player1Panel.player) {
               g.setColor(MyColor.lightOrange);
            } else if (player == Player2Panel.player) {
               g.setColor(MyColor.darkPurple);
            }
            g.fillOval(3, 3, size - 6, size - 6);
         }
      }

      if (getModel().isRollover()) {
         if (!isActive) {
            if(Board.powerupState == -1 || Board.powerupState == 3 ){
               if (Board.playerTurn == Player1Panel.player) {
                  g.setColor(MyColor.lightOrange);
               } 
               else if (Board.playerTurn == Player2Panel.player) {
                  g.setColor(MyColor.darkPurple);
               }
               g.drawOval(3, 3, size - 6, size - 6);
               g.drawOval(3 + 2, 3 + 2, size - 6 - 4, size - 6 - 4);
            }
            else{
               if(Board.powerupState == 0){
                  if (Board.playerTurn == Player1Panel.player) {
                     g.drawImage(Board.hoverPower2x1, 3,3,size-6, size-6,null);
                  } 
                  else if (Board.playerTurn == Player2Panel.player) {
                     g.drawImage(Board.hoverPower2x2, 3,3,size-6, size-6,null);
                  }
               }
               else if(Board.powerupState == 1){
                  if (Board.playerTurn == Player1Panel.player) {
                     g.drawImage(Board.hoverPowerRemove1 , 3,3,size-6, size-6,null);
                  } 
                  else if (Board.playerTurn == Player2Panel.player) {
                     g.drawImage(Board.hoverPowerRemove2 , 3,3,size-6, size-6,null);
                  }
               }
               else if(Board.powerupState == 2){
                  if (Board.playerTurn == Player1Panel.player) {
                     g.drawImage(Board.hoverPowerSkip1 , 3,3,size-6, size-6,null);
                  } 
                  else if (Board.playerTurn == Player2Panel.player) {
                     g.drawImage(Board.hoverPowerSkip2 , 3,3,size-6, size-6,null);
                  }
               }
            }
         }


      }

   }

   public void resetCell(ArrayList<int[]> cell) {
      new Thread(new Runnable() {
         @Override
         public void run() {
            Player turn = Board.playerTurn;
            int powerupState = Board.powerupState;
            for (int i = 0; i<4; i++){
               if(powerupState == i){
                  turn.getPowerUp(i).setAmmount( turn.getPowerUp(i).getAmmount()-1 );
                  GameSetting.p1powers.pCount[i].setText(""+Player1Panel.player.getPowerUp(i).getAmmount());
                  GameSetting.p2powers.pCount[i].setText(""+Player2Panel.player.getPowerUp(i).getAmmount());
               }
            }

            if(powerupState != 2){
               Board.playerTurn = Board.playerTurn == Player1Panel.player ? Player2Panel.player : Player1Panel.player;
            }

            if(powerupState != -1){
               Board.powerupState = -1;
               GameSetting.p1powers.resetState();
               GameSetting.p2powers.resetState();
            }

            for (int i = 0; i < cell.size(); i++) {
               try {
                  Thread.sleep(50);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
               Main.se.setFile("res/sound/getPointNew.wav");
               Main.se.play();
               Board.cells[cell.get(i)[0]][cell.get(i)[1]].player = null;
               Board.cells[cell.get(i)[0]][cell.get(i)[1]].isActive = false;

               turn.setPoint(turn.getPoint() + (powerupState==0? 2:1) );
               Main.p1.pointLabel.setText("" + Player1Panel.player.getPoint());
               Main.p2.pointLabel.setText("" + Player2Panel.player.getPoint());

               Main.gameFrame.repaint();
            }
            
            try {
               Thread.sleep(50);
               if(powerupState == -1){
                  int r = random.nextInt(4);
                  if (cell.size()>Board.straightCount){

                     Main.se.setFile("res/sound/getPowerup.wav");
                     Main.se.play();
                     turn.getPowerUp(r).setAmmount(turn.getPowerUp(r).getAmmount()+1);
                     GameSetting.p1powers.pCount[r].setText(""+Player1Panel.player.getPowerUp(r).getAmmount());
                     GameSetting.p2powers.pCount[r].setText(""+Player2Panel.player.getPowerUp(r).getAmmount());
                  }
                  else{
                     int randProb = random.nextInt(100);
                     if(randProb>50){
                        Main.se.setFile("res/sound/getPowerup.wav");
                        Main.se.play();
                        
                        turn.getPowerUp(r).setAmmount(turn.getPowerUp(r).getAmmount()+1);
                        GameSetting.p1powers.pCount[r].setText(""+Player1Panel.player.getPowerUp(r).getAmmount());
                        GameSetting.p2powers.pCount[r].setText(""+Player2Panel.player.getPowerUp(r).getAmmount());
                     }
                  }
               } 
               Thread.sleep(50);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }

            if(powerupState == 1){
               int x = getXpos();
               int y = getYpos();
               for(int i = 0; i<Board.cellAmmount; i++){
                  Board.cells[x][i].marked = false;
                  Board.cells[x][i].markColor = null;
                  Board.cells[x][i].revalidate();
                  Board.cells[x][i].repaint();
               }

               for(int i = 0; i<Board.cellAmmount; i++){
                  Board.cells[i][y].marked = false;
                  Board.cells[i][y].markColor = null;
                  Board.cells[i][y].revalidate();
                  Board.cells[i][y].repaint();
               }
            }

            else{
               for(int i =0; i<cell.size(); i++){
                  Board.cells[ cell.get(i)[0] ][cell.get(i)[1]].marked = false;
                  Board.cells[ cell.get(i)[0] ][cell.get(i)[1]].markColor = null;
                  Board.cells[ cell.get(i)[0] ][cell.get(i)[1]].revalidate();
                  Board.cells[ cell.get(i)[0] ][cell.get(i)[1]].repaint();
               }
            }
            winProccess();
         }
      }).start();
   }

   void winProccess() {
      Player winer = winCheck();
      if (winer != null) {
         Main.se.setFile("res/sound/win.wav");
         Main.se.play();
         GameFrame.isGameEnd = true;
         GameFrame.winerPanel = new WinerPanel(winer);
         Main.gameLayer.add(GameFrame.winerPanel, JLayeredPane.DRAG_LAYER);
      }
   }

   public boolean isDraw() {
      boolean res = true;
      for (int i = 0; i < Board.cellAmmount; i++) {
         for (int j = 0; j < Board.cellAmmount; j++) {
            if (Board.cells[i][j].player == null) {
               return false;
            }
         }
      }
      return res;
   }

   public Player winCheck() {
      if (Player1Panel.player.getPoint() >= GameSetting.pointToWin) {
         return Player1Panel.player;
      } 
      else if (Player2Panel.player.getPoint() >= GameSetting.pointToWin) {
         return Player2Panel.player;
      }
      return null;
   }

   ArrayList<int[]> isDiagonalRight(int x, int y) {
      ArrayList<int[]> del = new ArrayList<int[]>();
      int i = x - 1;
      int j = y + 1;
      while (i >= 0 && j < Board.cellAmmount) {
         if (Board.cells[i][j].player == Board.cells[x][y].player) {
            del.add(new int[] { i, j });
         } else {
            break;
         }
         i--;
         j++;
      }

      i = x + 1;
      j = y - 1;
      while (i < Board.cellAmmount && j >= 0) {
         if (Board.cells[i][j].player == Board.cells[x][y].player) {
            del.add(new int[] { i, j });
         } else {
            break;
         }
         i++;
         j--;
      }
      return del;
   }

   ArrayList<int[]> isDiagonalLeft(int x, int y) {
      ArrayList<int[]> del = new ArrayList<int[]>();
      int i = x - 1;
      int j = y - 1;
      while (i >= 0 && j >= 0) {
         if (Board.cells[i][j].player == Board.cells[x][y].player) {
            del.add(new int[] { i, j });
         } else {
            break;
         }
         i--;
         j--;
      }

      i = x + 1;
      j = y + 1;
      while (i < Board.cellAmmount && j < Board.cellAmmount) {
         if (Board.cells[i][j].player == Board.cells[x][y].player) {
            del.add(new int[] { i, j });
         } else {
            break;
         }
         i++;
         j++;
      }
      return del;
   }

   ArrayList<int[]> isVertical(int x, int y) {
      ArrayList<int[]> del = new ArrayList<int[]>();
      int i = x - 1;
      while (i >= 0) {
         if (Board.cells[i][y].player == Board.cells[x][y].player) {
            del.add(new int[] { i, y });
         } else {
            break;
         }
         i--;
      }

      i = x + 1;
      while (i < Board.cellAmmount) {
         if (Board.cells[i][y].player == Board.cells[x][y].player) {
            del.add(new int[] { i, y });
         } else {
            break;
         }
         i++;
      }
      return del;
   }

   ArrayList<int[]> isHorizontal(int x, int y) {
      ArrayList<int[]> del = new ArrayList<int[]>();
      int i = y - 1;
      while (i >= 0) {
         if (Board.cells[x][i].player == Board.cells[x][y].player) {
            del.add(new int[] { x, i });
         } else {
            break;
         }
         i--;
      }

      i = y + 1;
      while (i < Board.cellAmmount) {
         if (Board.cells[x][i].player == Board.cells[x][y].player) {
            del.add(new int[] { x, i });
         } else {
            break;
         }
         i++;
      }
      return del;
   }

   
   @Override
   public void actionPerformed(ActionEvent arg0) {
      if (arg0.getSource() == this) {
         if (!GameFrame.isGamePaused && !GameFrame.isGameEnd) {
            int powerupState = Board.powerupState;
            if(powerupState == 1 && player == null){
               ArrayList<int[]> resetAll = new ArrayList<int[]>();
               int x = getXpos();
               int y = getYpos();

               for(int i = 0; i<Board.cellAmmount; i++){
                  Board.cells[x][i].marked = true;
                  Board.cells[x][i].markColor = Board.playerTurn == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                  if(Board.cells[x][i].isActive){
                     resetAll.add(new int[] {x,i});
                  }
               }

               for(int i = 0; i<Board.cellAmmount; i++){
                  Board.cells[i][y].marked = true;
                  Board.cells[i][y].markColor = Board.playerTurn == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                  if(Board.cells[i][y].isActive){
                     resetAll.add(new int[] {i,y});
                  }
               }

               resetCell(resetAll);
            }

            else if(powerupState == 3 && isActive){
               ArrayList<int[]> resetAll = new ArrayList<int[]>();
               resetAll.add(new int[] {getXpos(),getYpos()});
               resetCell(resetAll);
            }

            else {
               if (player == null) {
                  Main.se.setFile("res/sound/clickCell.wav");
                  Main.se.play();
                  isActive = true;
                  player = Board.playerTurn;
                  repaint();
   
                  ArrayList<int[]> diaRight = isDiagonalRight(getXpos(), getYpos());
                  ArrayList<int[]> diaLeft = isDiagonalLeft(getXpos(), getYpos());
                  ArrayList<int[]> horizontal = isHorizontal(getXpos(), getYpos());
                  ArrayList<int[]> vertical = isVertical(getXpos(), getYpos());
   
                  if (diaLeft.size() >= Board.straightCount - 1 || diaRight.size() >= Board.straightCount - 1
                        || horizontal.size() >= Board.straightCount - 1 || vertical.size() >= Board.straightCount - 1) {
   
                     ArrayList<int[]> allReset = new ArrayList<int[]>();
                     allReset.add(new int[] {getXpos(), getYpos()} );
                     Board.cells[ getXpos() ][getYpos()].markColor = player == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                     Board.cells[ getXpos() ][getYpos()].marked = true;
                     
                     if (diaLeft.size() >= Board.straightCount - 1) {
                        for(int i = 0; i<diaLeft.size(); i++){
                           Board.cells[ diaLeft.get(i)[0] ][diaLeft.get(i)[1]].markColor = player == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                           Board.cells[ diaLeft.get(i)[0] ][diaLeft.get(i)[1]].marked = true;
                        }
                        allReset.addAll(diaLeft);
                     }
                     if (diaRight.size() >= Board.straightCount - 1) {
                        for(int i = 0; i<diaRight.size(); i++){
                           Board.cells[ diaRight.get(i)[0] ][diaRight.get(i)[1]].markColor = player == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                           Board.cells[ diaRight.get(i)[0] ][diaRight.get(i)[1]].marked = true;
                        }
                        allReset.addAll(diaRight);
                     }
                     if (horizontal.size() >= Board.straightCount - 1) {
                        for(int i = 0; i<horizontal.size(); i++){
                           Board.cells[ horizontal.get(i)[0] ][horizontal.get(i)[1]].markColor = player == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                           Board.cells[ horizontal.get(i)[0] ][horizontal.get(i)[1]].marked = true;
                        }
                        allReset.addAll(horizontal);
                     }
                     if (vertical.size() >= Board.straightCount - 1) {
                        for(int i = 0; i<vertical.size(); i++){
                           Board.cells[ vertical.get(i)[0] ][vertical.get(i)[1]].markColor = player == Player1Panel.player ? MyColor.orangge : MyColor.unggu;
                           Board.cells[ vertical.get(i)[0] ][vertical.get(i)[1]].marked = true;
                        }
                        allReset.addAll(vertical);
                     }
                     resetCell(allReset);
                  }
                  
                  else if ( isDraw()){
                     Main.se.setFile("res/sound/draw.wav");
                     Main.se.play();
                     GameFrame.isGameEnd = true;
                     GameFrame.drawPanel = new DrawPanel();
                     Main.gameLayer.add(GameFrame.drawPanel, JLayeredPane.DRAG_LAYER);
                  }
   
                  else{
                     if(Board.powerupState == 2){
                        Board.playerTurn.getPowerUp(2).setAmmount( Board.playerTurn.getPowerUp(2).getAmmount()-1 );
                        GameSetting.p1powers.pCount[2].setText(""+Player1Panel.player.getPowerUp(2).getAmmount());
                        GameSetting.p2powers.pCount[2].setText(""+Player2Panel.player.getPowerUp(2).getAmmount());
                        repaint();
                     }
                     else{
                        Board.playerTurn = Board.playerTurn == Player1Panel.player ? Player2Panel.player : Player1Panel.player;
                     }
                     Board.powerupState = -1;
                     GameSetting.p1powers.resetState();
                     GameSetting.p2powers.resetState();
                  }
                  
               }
            }
         }         
      }
   }
}