package othetoe;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {
   public static GameFrame gameFrame;
   public static JLayeredPane gameLayer;
   public static Player1Panel p1;
   public static Player2Panel p2;
   
   public static PausePanel pause;

   public static Board board;
   public static MainMenu mainMenu;
   public static GameSetting gameSetting;
   public static HistoryPanel historyPanel;
   public static LeaderboardPanel leaderboardPanel;

   public static SoundEffect se = new SoundEffect();

   public static void main(String[] args) {
      
      new Runnable(){
         @Override
         public void run() {

            gameFrame = new GameFrame();
            gameFrame.setFocusable(true);

            new Runnable(){

               @Override
               public void run() {
                  gameFrame.requestFocus();

               }
               
            }.run();
         }
      }.run();
   }
}
