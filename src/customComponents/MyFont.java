package customComponents;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyFont {
   public static Font MyFont(){
      Font font  = null;
      try{
         font = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/Poppins-Regular.ttf"));
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         ge.registerFont(font);
         return font;
         
      } catch(IOException|FontFormatException e){
         
      }
      return font;
   }
}
