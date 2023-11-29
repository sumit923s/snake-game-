package snakegame;
 
import javax.swing.*;
//        javax means it is an extendwd package
public class SnakeGame extends JFrame{
 
    SnakeGame() {
        super("Snake Game");
        add(new Board());
        pack();
        
//        setLocation(0,0);
//        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new SnakeGame();
//        It also work.
//        new SnakeGame();.setVisible(true);
    }
    
}
