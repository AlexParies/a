/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;
/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
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
    
    GameGUI game = new GameGUI();
    game.createBoard();

    Menu menu = new Menu();
    menu.Setup();

    // size of move
    int m = 60; 
    // individual player moves
    int px = 0;
    int py = 0; 
    
    int score = 0;
    String[] validCommands = { "right", "left", "up", "down", "r", "l", "u", "d",
    "jump", "jumpright", "jr", "jumpleft", "jl", "jumpup", "ju", "jumpdown", "jd",
    "pickup", "p", "quit", "q", "replay", "help", "?","switch","s","konamicode"};
  
    // set up game
    boolean play = true;
    int player = 1;
    while (play)
    {
      String string = UserInput.getValidInput(validCommands);

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
      } else if (string.equals("switch") || string.equals("s")) {
        if (player == 1) { player = 2; println("player 2's turn."); } else if (player == 2) { player = 1; println("player 1's turn."); }
      } else if (string.equals("konamicode")) {
        game.toEnd(player);
      } else {
        println(string);
      }
    }

    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps(1) + game.getSteps(2));
  }
}

        