package customComponents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;


public class MinimizeButton extends JButton {
   int w, h;

   public MinimizeButton(int x, int y,int w, int h){
      this.w = w;
      this.h = h;
      setBounds(x,y,w,h);
      setBorder(new LineBorder(MyColor.lightOrange));
   }
   
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      if(getModel().isRollover()){
         g.setColor(MyColor.lightBg.darker());
      }
      else{
         g.setColor(MyColor.lightBg);
      }
      g.fillRect(0, 0, w, h);

      g.setColor(MyColor.lightOrange);
      g.drawRect(0, 0, w, h);
      g.drawLine(5,h/2,w-5,h/2);
   }
}
