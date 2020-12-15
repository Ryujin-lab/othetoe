package customComponents;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {

   public MyLabel(String text,int x, int y,int w,int h, Color c, Font f){
      super(text);
      setup(x,y,w,h,c,f);
   }

   public MyLabel(String text,int x, int y,int w,int h, Color c, Font f, boolean center){
      super(text, SwingConstants.CENTER);
      setup(x,y,w,h,c,f);
   }

   private void setup(int x, int y,int w,int h, Color c, Font f){
      this.setFont(f);
      this.setBounds(x,y,w,h);
      this.setForeground(c);
   }
}

