package customComponents;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextBox extends JTextField {
   Color borderColor = Color.black;

   @Override
   public void paintComponent(Graphics g) {
      
      super.paintComponent(g);
      g.setColor(borderColor);
      g.drawRect(0, 0, getWidth()-1, getHeight()-1);

   }
   
   public MyTextBox(int x, int y, int w, int h, Color bg) {
      setBackground(bg);
      setBounds(x, y, w, h);
      requestFocusInWindow();
   }

   public MyTextBox(int x, int y, int w, int h, Color bg, Color border) {
      this(x, y, w, h, bg);
      this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
      this.borderColor = border;
   }

   public void setPlaceHolder(String placeHolder, Color phColor, Color textColor) {
      setText(placeHolder);
      setForeground(phColor);

      this.addFocusListener(new FocusListener() {

         @Override
         public void focusGained(FocusEvent arg0) {
            if ( getText().equals(placeHolder) ){
               setText("");
               setForeground(textColor);
            }
         }

         @Override
         public void focusLost(FocusEvent arg0) {
            if (getText().isEmpty()){
               setText(placeHolder);
               setForeground(phColor);
               setText(placeHolder);
            }

         }
         
      });
   }
}
