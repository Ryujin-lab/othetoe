package othetoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import customComponents.*;

public class Board extends JPanel{
   public static int cellAmmount;
   public static int straightCount;
   public static Cell [][] cells;
   public static Player playerTurn;
   public static String date = new MyDate().getDate();
   public static int powerupState = -1;

   public static Image hoverPowerUndo1;
   public static Image hoverPowerUndo2;

   public static Image hoverPower2x1;
   public static Image hoverPower2x2;

   public static Image hoverPowerRemove1;
   public static Image hoverPowerRemove2;

   public static Image hoverPowerSkip1;
   public static Image hoverPowerSkip2;

   Board(int c, int s){
      playerTurn = Player1Panel.player;
      cellAmmount = c;
      straightCount = s;

      int size = 600/cellAmmount;

      setBounds(320, 0, 600, 600);
      setBackground(MyColor.lightBg); 
      setLayout(new GridLayout(cellAmmount, cellAmmount));
      requestFocusInWindow();

      hoverPowerUndo1 = new ImageIcon("res/powerup/undop1e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPowerUndo2 = new ImageIcon("res/powerup/undop2e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPower2x1 = new ImageIcon("res/powerup/2xp1e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPower2x2 = new ImageIcon("res/powerup/2xp2e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPowerRemove1 = new ImageIcon("res/powerup/removep1e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPowerRemove2 = new ImageIcon("res/powerup/removep2e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPowerSkip1 = new ImageIcon("res/powerup/skipp1e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);
      hoverPowerSkip2 = new ImageIcon("res/powerup/skipp2e.png").getImage().getScaledInstance(size-6, size-6, Image.SCALE_SMOOTH);

      cells = new Cell[cellAmmount][cellAmmount];

      for (int i = 0; i<cellAmmount; i++){
         for (int j = 0; j<cellAmmount; j++){
            cells[i][j] = new Cell(i, j, size);
            add(cells[i][j]);
         }
      }

   }
}
