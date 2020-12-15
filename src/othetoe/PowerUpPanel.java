package othetoe;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.metal.MetalToggleButtonUI;

import customComponents.MyColor;

import java.awt.*;
import java.awt.event.*;

public class PowerUpPanel extends JLayeredPane implements MouseInputListener{
    public PowerupButton [] pButtons  =  new PowerupButton[4];
    public powerupCount [] pCount = new powerupCount[4];
    public Player player = null;
    
    PowerUpPanel(int x, int y, int w, int h, int player){
        this.player = player == 1? Player1Panel.player: Player2Panel.player;
        setBounds(x, y, w, h+10);
        setBackground(null);
        setOpaque(false);
        setLayout(null);
        pButtons[0] = new PowerupButton( new ImageIcon("res/powerup/2xp"+player+"e.png"), new ImageIcon("res/powerup/2xp"+player+"d.png"),0, 0, 50, 50);
        pButtons[1] = new PowerupButton( new ImageIcon("res/powerup/removep"+player+"e.png"), new ImageIcon("res/powerup/removep"+player+"d.png"),60, 0, 50, 50);
        pButtons[2] = new PowerupButton( new ImageIcon("res/powerup/skipp"+player+"e.png"), new ImageIcon("res/powerup/skipp"+player+"d.png"),120, 0, 50, 50);
        pButtons[3] = new PowerupButton( new ImageIcon("res/powerup/undop"+player+"e.png"), new ImageIcon("res/powerup/undop"+player+"d.png"),180, 0, 50, 50);
        
        int adder = 0;
        if(player == 2){
            adder = 30;
        }

        pCount[0] = new powerupCount(adder+0,40,this.player, 0);
        pCount[1] = new powerupCount(adder+60,40,this.player, 1);
        pCount[2] = new powerupCount(adder+120,40,this.player, 2);
        pCount[3] = new powerupCount(adder+180,40,this.player, 3);

        for(int i = 0; i<4; i++){
            pButtons[i].addMouseListener(this);
            add(pButtons[i]);
            add(pCount[i], JLayeredPane.DRAG_LAYER);
        }
    }

    public void resetState(){
        for(int i = 0; i<4; i++){
            pButtons[i].setSelected(false);
        }
    }

    public void disableAllButton(){
        for(int i = 0; i<4; i++){
            pButtons[i].setEnabled(false);
        }
    }

    public void enableAllButton(){
        for(int i = 0; i<4; i++){
            pButtons[i].setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!GameFrame.isGamePaused){
            if (e.getSource() == pButtons[0] ||
            e.getSource() == pButtons[1] ||
            e.getSource() == pButtons[2] ||
            e.getSource() == pButtons[3] ){
                if (player == Board.playerTurn){
                    for(int i = 0; i<4; i++){
    
                        if (pButtons[i]  != e.getSource()){
                            pButtons[i].setSelected(false);
                        }
    
                        else{
                            if( pButtons[i].isSelected() ){
                                pButtons[i].setSelected(false);
                                Board.powerupState = -1;
                                // set boar powerup ke -1;
                                // System.out.println(-1);
                            }
                            else{
                                if(player.getPowerUp(i).getAmmount() > 0){
                                    Main.se.setFile("res/sound/powerup.wav");
                                    Main.se.play();
            
                                    pButtons[i].setSelected(true);
                                    Board.powerupState = i;
                                    // System.out.println(i);
                                    // set board powerup ke i
                                }
                                else{
                                    Main.se.setFile("res/sound/alert.wav");
                                    Main.se.play();
                                }
                            }
                        }
                    }
                }
                else{
                    Main.se.setFile("res/sound/alert.wav");
                    Main.se.play();
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
    
}

class PowerupButton extends JButton {
    int x, y, w, h;
    ImageIcon imageEnable, imageDisable;
    PowerupButton(ImageIcon imageEnable, ImageIcon imageDisable, int x, int y,int w,int h){
        this.imageEnable = imageEnable;
        this.imageDisable= imageDisable;

        setIcon(this.imageDisable);
        setSelectedIcon(this.imageEnable);
        setBounds(x,y,w,h);
        setFocusable(false);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return null;
            }
        });

        setBackground(MyColor.lightBg);
        setOpaque(false);
        setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g) .setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(getModel().isRollover() && !GameFrame.isGamePaused){
            g.setColor(MyColor.netral);
            g.drawOval(-3, -3, 56, 56);
        }
        else if(getModel().isSelected() && !GameFrame.isGamePaused){
            g.setColor(MyColor.netral);
            g.drawOval(-3, -3, 56, 56);
        }
    }
}

class powerupCount extends JLabel{
    powerupCount(int x, int y, Player p, int i){
        super(""+p.getPowerUp(i).getAmmount(), SwingConstants.CENTER);
        setForeground(MyColor.lightBg);
        setFont(GameFrame.FONT.deriveFont(Font.PLAIN,12));
        setBounds(x,y,20,20);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        ((Graphics2D)g) .setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(MyColor.netral);
        g.fillOval(0, 0, getWidth(), getHeight());
        
        super.paintComponent(g);
    }
}
