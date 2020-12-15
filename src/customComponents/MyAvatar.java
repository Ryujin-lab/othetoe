package customComponents;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import othetoe.*;

public class MyAvatar extends JComponent implements MouseInputListener {
   // Image img;
   private Image img;
   private String path;

   public MyAvatar(String image, int x, int y, int w, int h) {
      path = image;
      setSize(new Dimension(w, h));
      img = new ImageIcon(image).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);

      setOpaque(false);
      setBounds(x, y, w, h);
      setFocusable(true);
      addMouseListener(this);
   }

   void setImage(String image){
      this.path = image;
      img = new ImageIcon(path).getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
      repaint();
   }

   public String getPath() {
      return path;
   }

   public void setPath(String path) {
      this.path = path; 
      repaint();
   }

   @Override
   public void paintComponent(Graphics g) {      
      super.paintComponent(g);
      g.drawImage(img, 0, 0, null, this);

   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      if (arg0.getSource() == this){
         RegisterPanel.avatarPath = this.path;
         Image img = new ImageIcon(path).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
         RegisterPanel.avatar.setIcon(new ImageIcon(img));
         repaint();
      }
   }

   @Override
   public void mouseEntered(MouseEvent arg0) {

   }

   @Override
   public void mouseExited(MouseEvent arg0) {

   }

   @Override
   public void mousePressed(MouseEvent arg0) {

   }

   @Override
   public void mouseReleased(MouseEvent arg0) {

   }

   @Override
   public void mouseDragged(MouseEvent arg0) {

   }

   @Override
   public void mouseMoved(MouseEvent arg0) {

   }
}
