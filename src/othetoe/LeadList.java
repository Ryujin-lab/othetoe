package othetoe;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import DBproccess.SQLite;
import customComponents.*;

public class LeadList extends JPanel {
    Color bg = MyColor.lightBg;
    int winBar = 0;
    public LeadList(String userId, String name, String image, int plays, int wins, int point, int index){
        setBounds(0,index*100,1180, 100);
        if(plays > 0){
            winBar = (wins*500)/plays;
        }
        // setPreferredSize ( new Dimension(1180,100));
        setBackground(bg);
        setLayout(null);

        JLabel imgPlayer = new JLabel();
        imgPlayer.setBounds(30,10,80,80);
        Image i = new ImageIcon(image).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        imgPlayer.setIcon(new ImageIcon(i));
        int porsentage = 0;
        if (plays > 0){
             porsentage = wins*100/plays;
        }

        MyLabel uname = new MyLabel(name, 120, 30, 270, 20, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,20) , true);
        MyLabel uid = new MyLabel(userId, 120, 55, 270, 20, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,20) , true);
        MyLabel p = new MyLabel(""+point, 390, 32, 80, 40, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,30) , true);
        MyLabel ketPlay = new MyLabel( "Total match : " + plays, 500, 20, 500, 30, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,20) , false);
        MyLabel ketWin = new MyLabel(  "Wins : " + wins , 500, 52, 500, 30, MyColor.netral , GameFrame.FONT.deriveFont(Font.PLAIN,20) , false);
        MyLabel winRate = new MyLabel(  ""+ porsentage + "%" , 1010, 30, 160, 40, MyColor.lightOrange.brighter() , GameFrame.FONT.deriveFont(Font.PLAIN,40) , false);
        MyLabel winRateLabel = new MyLabel(  "Win rate" , 1010, 65, 160, 20, MyColor.lightOrange.brighter(), GameFrame.FONT.deriveFont(Font.PLAIN,15) , false);

        add(winRateLabel);
        add(winRate);
        add(ketWin);
        add(ketPlay);
        add(p);
        add(uname);
        add(uid);
        add(imgPlayer);

        addMouseListener(new MouseInputAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(bg.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(bg);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g) .setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(MyColor.darkPurple);
        g.drawLine(110, 50, 390, 50);
        g.drawOval(390, 10, 80, 80);
        g.drawOval(30, 10, 80, 80);
        
        g.setColor(MyColor.lightOrange);
        g.fillRect(500, 50, winBar, 30);
        g.drawRect(500, 50, 500, 30);

        g.setColor(MyColor.darkBg);
        g.drawLine(0,0,getWidth(),0);
    }
}
