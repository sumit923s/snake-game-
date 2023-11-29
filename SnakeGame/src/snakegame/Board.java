package snakegame;

//Shift F11 se hum lig jaar bana sakte hain, kuch bhi agar saved nahi hai to save ho jaaega
//Color class is inside awt package
//import java.awt.Color;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//ActionlIstener is an Interface ,it require when we click on the mouse ,cick on the keyboard,etc.It is present 
// it is present in java.awt.event package; Used in Timer class

public class Board extends JPanel implements ActionListener {
    
//    public void actionPerformed(ActionEvent e); methods in ActionListener class we need t override this
//    this Image is a class of awt
    private Image strawberry;
    private Image tail;
    private Image head;
    
    private final int ALL_DOTS = 1600;
    private final int DOT_SIZE = 10;
    private final int RANDOM_POSITION = 29;
            
    private int strawberry_x;
    private int strawberry_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
//    ye hum variable ke saath bhi le sakte hain kyonki jab ek aple kha lega tab enext apple generate hoga
 
//    initially yeh right direction me hi move karega,isliye wo true hai
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame= true;
//    no initilization needed
    private int dots;
    private Timer timer;
            Board(){
                 
//                Calling TAdapter class and this function comes from ActionListener INTERFACE
          addKeyListener(new TAdapter());
//        we are creating object of RGB Color class
//        setBackground(new Color(0,0,0));

//if RGB not used than use it Color is a class and Black is a static object
          setBackground(Color.BLACK);
          
          setPreferredSize(new Dimension(400,400));
//          jab bhi game run hoga shift f6 se to click bahi karna parega direct game run ho jaaega
          setFocusable(true);
          
          loadImages();
          
//          function to initilize the game
          initGame();
    }
//    loadImage function uper rahega initgame function ke tabhi initgame function initilize ho paaega
    public void loadImages(){
        
//        ImageIcon is class which take icons from resource , getSystemResorce is a static variable
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/Strawberry.png"));
//        pickup images from one line up class
        strawberry = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/Tail.png"));
        tail = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/Head.png"));
        head = i3.getImage();
    }
    
    public void initGame(){
//        because 1 head hoga and 2 uske piche apple honge,global declaration needed 
        dots = 3;
        
        for(int i =0;i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i*DOT_SIZE;
        }
        locateStrawberry();
      
//        har 140 second ke baad yeh ActionListener ko call karta jaaega
         timer = new Timer(120,this);
          timer.start();
         
    }
    
    public void locateStrawberry(){
        int r = (int)(Math.random()*RANDOM_POSITION);
        strawberry_x = r* DOT_SIZE;
        
        r = (int)(Math.random()*RANDOM_POSITION);
        strawberry_y = r*DOT_SIZE;
    }
    
//    3 images strawberry,head,tail ko show karna hai to  paintCompoinent method ko use karna hoga
//    paintComponent is a method of graphic class
    @Override
    public void paintComponent(Graphics g){
//        calling parent component paintComponent with super class,ye khaas baat hai
        super.paintComponent(g);
        
        draw(g);
    }
    
    public void draw(Graphics g){
        if(inGame){
        g.drawImage( strawberry,strawberry_x , strawberry_y, this);
        
        for (int i=0;i< dots;i++){
            if(i==0){
//                showing head to frame coordinates
                g.drawImage(head, x[i], y[i], this);
            }
            else{
                g.drawImage(tail, x[i], y[i], this);
            }
        }
//        default toolkit function se initilize kar rahe hain ,sync function is also calling
        Toolkit.getDefaultToolkit().sync();
        }
        
        else{
            gameOver(g);
        }
    }
    
    public void gameOver(Graphics g){
        String msg = "GAME OVER!";
        Font font = new Font("SAN SKEIF",Font.BOLD,14);
        FontMetrics metrices =  getFontMetrics(font);
        g.setColor(Color.red);
        g.setFont(font);
        g.drawString(msg, (400 - metrices.stringWidth(msg))/2, 200);
//          g.drawString(msg,100, 200);
    }
    
    public void move(){
        
//        ye loop sirf head ke piche waale element ko move kar rahai hai,head ko nahi
        for (int i=dots;i>0;i--){
//            jo piche waali dot hai wah pehle waali dot ko jagah le lega,head ke respect me
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        if (leftDirection){
            x[0] = x[0] - DOT_SIZE;
        }
        
        if (rightDirection){
            x[0] = x[0] + DOT_SIZE;
        }
                
        if (upDirection){
            y[0] = y[0] - DOT_SIZE;
        }
                        
        if (downDirection){
            y[0] = y[0] + DOT_SIZE;
        }
//        ye code head ko move kar raha hai
//        x[0] += DOT_SIZE;
//        y[0] += DOT_SIZE;
    }
    
    public void checkStrawberry(){
        if((x[0] == strawberry_x) && y[0] == strawberry_y){
            dots++;
            locateStrawberry();
        }
    }
    
    public void checkCollision(){
        for(int i =dots;i>0;i--){
//            agar i>4 hai tabhi collision hooga else nahi ho paaega
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
            
            if(y[0] >= 400){
                inGame = false;
            }
            
            if(x[0] >= 400){
                inGame = false;
            }
                        
            if(y[0] < 0){
                inGame = false;
            }
                               
            if(x[0] < 0){
                inGame = false;
            }
            
            if(!inGame){
                timer.stop();
            }
        }
    }
    
    @Override
    public void actionPerformed (ActionEvent e){
         
        if(inGame){
        checkStrawberry();
         
        
        move();
        checkCollision();
        }
//        repaint function will refresh the frame like pack();
        repaint();
        
        
    }
 
    
//    KeyAdapter is a class which has keyListener interface
    public class TAdapter extends KeyAdapter {
        @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT && (!rightDirection)){
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        }
        
        if (key == KeyEvent.VK_RIGHT && (!leftDirection)){
            upDirection = false;
            downDirection = false;
            rightDirection = true;
        }
                
        if (key == KeyEvent.VK_UP && (!downDirection)){
            leftDirection = false;
            upDirection = true;
            rightDirection = false;
        }
                
        if (key == KeyEvent.VK_DOWN && (!upDirection)){
            leftDirection = false;
            downDirection = true;
            rightDirection = false;
        }
    }
    }

 
}
