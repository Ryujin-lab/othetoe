package customComponents;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyPassword extends JPasswordField {
   Color borderColor = Color.BLACK;

   public MyPassword(int x, int y, int w, int h, Color bg) {
      setBackground(bg);
      setBounds(x, y, w, h);
      requestFocusInWindow();
   }

   public MyPassword(int x, int y, int w, int h, Color bg, Color border) {
      this(x, y, w, h, bg);
      this.setBorder(new LineBorder(border));
      this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      this.borderColor = border;
   }

   public void setPlaceHolder(String placeHolder, Color phColor, Color textColor) {
      setText(placeHolder);
      setForeground(phColor);
      setEchoChar((char)0);
      System.out.println(getEchoChar()); 

      this.addFocusListener(new FocusListener() {
         
         @Override
         public void focusGained(FocusEvent arg0) {
            if ( getText().equals(placeHolder) ){
               setText("");
               setForeground(textColor);
               setEchoChar('•');
            }
         }
         
         @Override
         public void focusLost(FocusEvent arg0) {
            if (getText().isEmpty()){
               setText(placeHolder);
               setForeground(phColor);
               setEchoChar((char)0);
            }
         }
      });
   }

   @Override
   public void paintComponent(Graphics g) {
      
      super.paintComponent(g);
      g.setColor(borderColor);
      g.drawRect(0, 0, getWidth()-1, getHeight()-1);

   }
}
