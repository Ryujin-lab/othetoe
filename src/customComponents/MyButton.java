package customComponents;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class MyButton extends JButton { //daemon thread
   public Color defaultColor = super.getBackground();

   public MyButton(String text, int x, int y, int w, int h, Color bg) {
      this.setText(text);
      this.setBounds(x, y, w, h);
      this.setFont(MyFont.MyFont().deriveFont(Font.BOLD, 20));

      this.setBounds(x, y, w, h);
      this.setForeground(Color.LIGHT_GRAY);

      defaultColor = bg;
      setBackground(defaultColor);
      addMouseListener(new MouseAdapter() {
         public void mouseEntered(MouseEvent me) {
            setBackground(bg.brighter());
         }
         public void mouseExited(MouseEvent me) {
            setBackground(bg);
         }
      });

      this.setFocusable(false);
   }

   public MyButton(String text, int x, int y, int w, int h, Color bg, Color stroke) {
      this( text,  x,  y,  w,  h, bg);
      this.setBorder(new LineBorder(stroke));
   }


}
