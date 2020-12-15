package customComponents;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class BackButton extends JButton{
   int w, h;

   public BackButton(int x, int y, int w, int h){
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
      
      int [] xPoints = {w/3, w*2/3, w*2/3};
      int [] yPoints = {h/2, h-5, 5};
      
      g.fillPolygon(xPoints, yPoints, 3);
   }
}
