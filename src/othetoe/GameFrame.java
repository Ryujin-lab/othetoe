package othetoe;

import javax.sound.sampled.AudioInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import customComponents.*;

public class GameFrame extends JFrame{

   public static Boolean isGameStarted = false;
   public static Boolean isGamePaused = false;
   public static Boolean isGameEnd = false;

   public static PausePanel pausePanel;
   public static WinerPanel winerPanel;
   public static DrawPanel drawPanel;
   
   public static RegisterFrame reg;
   public static final Font FONT = MyFont.MyFont();
   
   
   GameFrame() {
      setUndecorated(true);
      setSize(new Dimension(1240,630)); 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      setLayout(null);
      setLocationRelativeTo(null);
      getContentPane().setBackground(MyColor.darkBg);
      requestFocusInWindow();

      Image icon = new ImageIcon("res/icon.png").getImage();
      setIconImage(icon);

      Main.gameLayer = new JLayeredPane();
      Main.gameLayer.setBounds(0,30,1240,600);
      Main.mainMenu = new MainMenu();

      pausePanel = new PausePanel();

      Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
         public void eventDispatched(AWTEvent event) {
               if(event.getID() == KeyEvent.KEY_PRESSED) {
                  KeyEvent kEvent = (KeyEvent) event;
                  boolean isEsc = (kEvent.getKeyCode() == 27);

                  if(isEsc && isGameStarted && !isGamePaused && !isGameEnd) {
                     // System.out.println("aaa");
                     Main.se.setFile("res/sound/pause.wav");
                     Main.se.play();
                     Main.gameLayer.add(pausePanel, JLayeredPane.DRAG_LAYER);
                     isGamePaused = true;
                     GameSetting.p1powers.disableAllButton();
                     GameSetting.p2powers.disableAllButton();
                  }
                  
                  else if (isEsc && isGameStarted && isGamePaused && !isGameEnd){
                     Main.se.setFile("res/sound/close.wav");
                     Main.se.play();
                     Main.gameLayer.remove(pausePanel);
                     repaint();
                     // System.out.println("bbb");
                     isGamePaused = false;
                     GameSetting.p1powers.enableAllButton();
                     GameSetting.p2powers.enableAllButton();
                  }

               }
         }
      }, AWTEvent.KEY_EVENT_MASK);
      add(new CustomTitleBar());
      add(Main.gameLayer);
      add(Main.mainMenu);
      setVisible(true);

   }

   File loadSound(String path){
      return new File(path);
   }

}