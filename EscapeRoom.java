/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import javax.swing.*;
import java.awt.event.*;
/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//audio imports and stuf





public class EscapeRoom implements ActionListener
{
      // describe the game with brief welcome message
      // determine the size (length and width) a player must move to stay within the grid markings
      // Allow game commands:
      //    right, left, up, down: if you try to go off grid or bump into wall, score decreases
      //    jump over 1 space: you cannot jump over walls
      //    if you land on a trap, spring a trap to increase score: you must first check if there is a trap, if none exists, penalty
      //    pick up prize: score increases, if there is no prize, penalty
      //    help: display all possible commands
      //    end: reach the far right wall, score increase, game ends, if game ended without reaching far right wall, penalty
      //    replay: shows number of player steps and resets the board, you or another player can play the same board
      // Note that you must adjust the score with any method that returns a score
      // Optional: create a custom image for your player use the file player.png on disk
    
      /**** provided code:
      // set up the game
      boolean play = true;
      while (play)
      {
        // get user input and call game methods to play 
        play = false;
      }
      */

  public static GameGUI game;
  public static int player;
  // size of move
  public static final int m = 60;
  // individual player moves
  public static int px = 0;
  public static int py = 0;
  public static int score = 0;
  public static JFrame frame;

  public static void println(String output) {
    System.out.println(output);
  }
  public static void print(String output) {
    System.out.print(output);
  }
  public static void main(String[] args) 
  {
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    game = new GameGUI();
    game.createBoard(); 
    
    
    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d",
    "jump", "jumpright", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
    "pickup", "p", "quit", "q", "replay", "help", "?","switch","s","konamicode","frank"};

    EscapeRoom esc = new EscapeRoom();
    esc.Setup();

    // set up game
    boolean play = true;
    player = 1;
    while (play)
    {
      String string = UserInput.getValidInput(validCommands);
      if (UserInput.closed) { play = false; continue; }
      if (string.equals("help") || string.equals("?")) {
        for(int i = 1; i < validCommands.length; i++) {
          if (validCommands[i] != "konamicode") { print(validCommands[i] + ((i != validCommands.length-1 && validCommands[i-1] != "konamicode") ? "," : "\n")); }
        }
      } else if (string.equals("quit") || string.equals("q")) {
        System.out.println("quitting");
        play = false;
        continue;
      } else if (string.equals("right") || string.equals("r")) {
        if (game.isTrap(m,0,player)) {
          score += game.springTrap(m,0,player);
          println("score: " + score);
        }
        score += game.movePlayer(m,0,player);
      } else if (string.equals("left") || string.equals("l")) {
        if (game.isTrap(-m,0,player)) {
          score += game.springTrap(-m,0,player);
          println("score: " + score);
        }
        score += game.movePlayer(-m,0,player);
      } else if (string.equals("up") || string.equals("u")) {
        if (game.isTrap(0,-m,player)) {
          score += game.springTrap(0,-m,player);
          println("score: " + score);
        }
        score += game.movePlayer(0,-m,player);
      } else if (string.equals("down") || string.equals("d")) {
        if (game.isTrap(0,m,player)) {
          score += game.springTrap(0,m,player);
          println("score: " + score);
        }
        score += game.movePlayer(0,m,player);
      } else if (string.equals("jump") || string.equals("jumpright") || string.equals("jr")) {
        if (game.isTrap(m*2,0,player)) {
          score += game.springTrap(m*2,0,player);
          println("score: " + score);
        }
        score += game.movePlayer(m*2,0,player);
      } else if (string.equals("jumpleft") || string.equals("jl")) {
        if (game.isTrap(m*-2,0,player)) {
          score += game.springTrap(m*-2,0,player);
          println("score: " + score);
        }
        score += game.movePlayer(m*-2,0,player);
      } else if (string.equals("jumpup") || string.equals("ju")) {
        if (game.isTrap(0,m*-2,player)) {
          score += game.springTrap(0,m*-2,player);
          println("score: " + score);
        }
        score += game.movePlayer(0,m*-2,player);
      } else if (string.equals("jumpdown") || string.equals("jd")) {
        if (game.isTrap(0,m*-2,player)) {
          score += game.springTrap(0,m*-2,player);
          println("score: " + score);
        }
        score += game.movePlayer(0,m*2,player);
      } else if (string.equals("pickup") || string.equals("p")) {
        score += game.pickupPrize(player);
        println("score: " + score);
      } else if (string.equals("replay")) {
        score += game.replay();
        System.out.println("score=" + score);
        System.out.println("steps=" + game.getSteps(1) + game.getSteps(2));
      } else if (string.equals("switch") || string.equals("s")) {
        if (player == 1) { player = 2; println("player 2's turn."); } else if (player == 2) { player = 1; println("player 1's turn."); }
      } else if (string.equals("konamicode")) {
        game.toEnd(player);
      } else if (string.equals("frank")) {
        game.switchImages();

        //File audioFile = new File("/frank.wav");
        //AudioInputStream audioStream = AudioSystem.getAudioInputStream();
        //AudioFormat format = audioStream.getFormat();
        //DataLine.Info info = new DataLine.Info(Clip.class, format);
      } else {
        println(string);
      }
    }
    if (!UserInput.closed) {
      score += game.endGame();
      frame.setVisible(false);
      frame.dispose();

      System.out.println("score=" + score);
      System.out.println("steps=" + game.getSteps(1) + game.getSteps(2));
    }
  }
  public void Setup() {
    frame = new JFrame("Menu");

    JButton buttonr = new JButton("right");
    buttonr.setBounds(200,100,50,50);
    buttonr.addActionListener((ActionListener) this);
    JButton buttonjr = new JButton("jumpright");
    buttonjr.setBounds(250,100,100,50);
    buttonjr.addActionListener((ActionListener) this);

    JButton buttonl = new JButton("left");
    buttonl.setBounds(100,100,50,50);
    buttonl.addActionListener((ActionListener) this);
    JButton buttonjl = new JButton("jumpleft");
    buttonjl.setBounds(0,100,100,50);
    buttonjl.addActionListener((ActionListener) this);

    JButton buttonu = new JButton("up");
    buttonu.setBounds(150,50,50,50);
    buttonu.addActionListener((ActionListener) this);
    JButton buttonju = new JButton("jumpup");
    buttonju.setBounds(125,0,100,50);
    buttonju.addActionListener((ActionListener) this);

    JButton buttond = new JButton("down");
    buttond.setBounds(150,150,50,50);
    buttond.addActionListener((ActionListener) this);
    JButton buttonjd = new JButton("jumpdown");
    buttonjd.setBounds(125,200,100,50);
    buttonjd.addActionListener((ActionListener) this);

    JButton buttonp = new JButton("pickup");
    buttonp.setBounds(400,0,100,50);
    buttonp.addActionListener((ActionListener) this);
    JButton buttonq = new JButton("quit");
    buttonq.setBounds(400,50,100,50);
    buttonq.addActionListener((ActionListener) this);
    JButton buttonrep = new JButton("replay");
    buttonrep.setBounds(400,100,100,50);
    buttonrep.addActionListener((ActionListener) this);
    JButton buttons = new JButton("switch");
    buttons.setBounds(400,150,100,50);
    buttons.addActionListener((ActionListener) this);

    frame.getContentPane().setLayout(null);

    frame.getContentPane().add(buttonr);
    frame.getContentPane().add(buttonl);
    frame.getContentPane().add(buttonu);
    frame.getContentPane().add(buttond);
    frame.getContentPane().add(buttonjr);
    frame.getContentPane().add(buttonjl);
    frame.getContentPane().add(buttonju);
    frame.getContentPane().add(buttonjd);
    frame.getContentPane().add(buttonp);
    frame.getContentPane().add(buttonq);
    frame.getContentPane().add(buttonrep);
    frame.getContentPane().add(buttons);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500,250+25);
    frame.setVisible(true);
}
    public void actionPerformed(ActionEvent event) {
      String buttonName = event.getActionCommand();
      if (buttonName == "right") {
        px=m;
      } else if (buttonName == "left") {
        px=-m;
      } else if (buttonName == "up") {
        py=-m;
      } else if (buttonName == "down") {
        py=m;
      } else if (buttonName == "jumpright") {
        px=m*2;
      } else if (buttonName == "jumpleft") {
        px=-m*2;
      } else if (buttonName == "jumpup") {
        py=-m*2;
      } else if (buttonName == "jumpdown") {
        py=m*2;
      } else if (buttonName == "pickup") {
        score += game.pickupPrize(player);
        println("score: " + score);
      } else if (buttonName == "quit") {
        System.out.println("quitting");
        
        score += game.endGame();
        frame.setVisible(false);
        frame.dispose();

        System.out.println("score=" + score);
        System.out.println("steps=" + game.getSteps(1) + game.getSteps(2));
        println("Press enter to close.");
        UserInput.closed = true;
      } else if (buttonName == "replay") {
        score += game.replay();
      } else if (buttonName == "switch") {
        if (player == 1) { player = 2; println("player 2's turn."); } else if (player == 2) { player = 1; println("player 1's turn."); }
      }
      if (px != 0 || py != 0) {
        if (game.isTrap(px,py,player)) {
          score += game.springTrap(px,py,player);
          println("score: " + score);
        }
        score += game.movePlayer(px,py,player);
        px=0;
        py=0;
      }
    }
}

        