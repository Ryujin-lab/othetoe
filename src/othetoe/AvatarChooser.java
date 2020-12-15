package othetoe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.Thread;

import javax.swing.JComponent;

public class AvatarChooser extends JComponent {


   AvatarChooser(String image) {
      this.setPreferredSize(new Dimension(50,50));
   }
}