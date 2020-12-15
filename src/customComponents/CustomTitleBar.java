package customComponents;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import othetoe.*;
import java.awt.event.*;

public class CustomTitleBar extends JPanel {
    JButton m;
    int posx = 0, posy = 0;
    Color BG = MyColor.darkBg;

    public CustomTitleBar(){
        setBounds(0,0,1240,30);
        setLayout(null);
        JLabel label = new JLabel();
        Image img = new ImageIcon("res/icon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        label.setIcon(icon);
        label.setBounds(1,1,28,28);
        add(label);

        JLabel label2 = new JLabel("OTHETOE : The Next Level Of Othelo & Tictactoe");
        label2.setForeground(MyColor.netral);
        label2.setFont(MyFont.MyFont().deriveFont(Font.PLAIN, 15));
        label2.setBounds(35,5,400,26);
        add(label2);

        CloseButton c = new CloseButton(1210,2, 26,26);
        c.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == c){
                    System.exit(0);
                }

            }

        });

        MinimizeButton m = new MinimizeButton(1180, 2, 26, 26);
        m.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == m){
                    Main.gameFrame.setState(Frame.ICONIFIED);
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                Main.gameFrame.setLocation(e.getXOnScreen()-posx,e.getYOnScreen()-posy);
                
            }
        });

        addMouseListener( new MouseInputListener(){

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                posx=e.getX();
                posy=e.getY();

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        

        });
        add(m);
        add(c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(BG.brighter());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BG.darker());
        g.drawLine(0, 29, getWidth(), 29);

    }
}
