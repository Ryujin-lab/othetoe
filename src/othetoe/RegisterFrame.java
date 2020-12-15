package othetoe;

import javax.swing.*;
import java.awt.*;

import customComponents.*;

public class RegisterFrame extends JFrame  {
   RegisterFrame() {
      setSize(new Dimension(600, 600));
      getContentPane().setBackground(MyColor.lightBg);
      setLocationRelativeTo(null);
      setResizable(false);
      setVisible(true);
      setResizable(false);
      setLayout(null);
      add(new RegisterPanel());
      
   }

}
