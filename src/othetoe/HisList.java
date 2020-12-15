package othetoe;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import DBproccess.SQLite;
import customComponents.MyColor;
import customComponents.MyLabel;

public class HisList extends JPanel {
    String cekwin = null;
    Color bg = MyColor.lightBg;
    public HisList(String p1id, String p2id, int p1Point, int p2Point, String winerId, int pointToWin, String date,int index) {

        cekwin = winerId;
        setBounds(0,index*50,1180, 50);
        setBackground(bg);
        setLayout(null);
        ResultSet rsP1 = SQLite.executeQuery("select * from player where userId ='" + p1id + "' ");
        Player p1 = null, p2 = null;
        try {
            while (rsP1.next()) {
                p1 = new Player(p1id, rsP1.getString("name"), rsP1.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rsP2 = SQLite.executeQuery("select * from player where userId ='" + p2id + "' ");
        try {
            while (rsP2.next()) {
                p2 = new Player(p2id, rsP2.getString("name"), rsP2.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel imgp1 = new JLabel();
        Image i = new ImageIcon(p1.getUserImage()).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        imgp1.setIcon(new ImageIcon(i));

        JLabel imgp2 = new JLabel();
        Image j = new ImageIcon(p2.getUserImage()).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        imgp2.setIcon(new ImageIcon(j));

        imgp1.setBounds(10,5,40,40);
        imgp2.setBounds(555,5,40,40);

        MyLabel p1Name = new MyLabel(p1.getUserName(), 55, 10, 150, 15, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,12) , true);
        MyLabel p2Name = new MyLabel(p2.getUserName(), 400, 10, 150, 15, MyColor.netral,  GameFrame.FONT.deriveFont(Font.PLAIN,12), true);

        MyLabel p1uid = new MyLabel(p1.getUserId(), 55, 27, 150, 15, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,12) , true);
        MyLabel p2uid = new MyLabel(p2.getUserId(), 400, 27, 150, 15, MyColor.netral,  GameFrame.FONT.deriveFont(Font.PLAIN,12), true);

        MyLabel p1p = new MyLabel(""+p1Point, 205, 7, 40, 40, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,17) , true);
        MyLabel p2p = new MyLabel(""+p2Point, 360, 7, 40, 40, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,17) , true);

        MyLabel vs = new MyLabel("VS", 245, 5, 360-245, 20, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,20) , true);
        MyLabel tanggal = new MyLabel(date, 245, 30, 360-245, 15, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,12) , true);
        
        if (winerId != null){
            MyLabel winer = new MyLabel("WINER : ", 630, 7, 100, 40, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,25) , true);
            
            Player win = winerId.equals(p1id) ? p1 : p2;
            int winPlayerPoint = winerId.equals(p1id) ? p1Point : p2Point;
            JLabel winImg = new JLabel();
            Image winImage = new ImageIcon(win.getUserImage()).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            winImg.setIcon(new ImageIcon(winImage));
            winImg.setBounds(740,5,40,40);

            MyLabel winLab = new MyLabel(win.getUserName(), 780, 5, 200, 20, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN, 15), true);
            MyLabel winLabId = new MyLabel(win.getUserId(), 780, 26, 200, 20, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN, 15), true);
            MyLabel winp = new MyLabel(winPlayerPoint + " Points out of " + pointToWin ,990, 14, 180, 20, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN, 15), true);
            
            add(winp);
            add(winLabId);
            add(winLab);
            add(winImg);
            add(winer);
        }
        else{
            MyLabel winer = new MyLabel("GAME DRAW ", 630, 7, 200, 40, MyColor.netral, GameFrame.FONT.deriveFont(Font.PLAIN,25) , true);
            add(winer);
        }

        add(tanggal);
        add(p1uid);
        add(p2uid);
        add(vs);
        add(imgp1);
        add(imgp2);
        add(p1Name);
        add(p2Name);
        add(p1p);
        add(p2p);

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
        g.setColor(MyColor.darkBg);
        g.drawLine(0,0,getWidth(),0);
        //p1
        g.setColor(MyColor.lightOrange);
        g.drawLine(55, 25, 205, 25);
        g.drawOval(205, 5, 40, 40);

        // p2
        g.setColor(MyColor.darkPurple);
        g.drawLine(400, 25, 550, 25);
        g.drawOval(360, 5, 40, 40);

        //separator
        g.setColor(MyColor.netral);
        g.drawLine(610, 5, 610, 45);
        if (cekwin != null){
            g.drawLine(780, 25, 980, 25);
            g.drawLine(980, 5, 980, 45);
        }

    }
}