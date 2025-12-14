package main;

import GameController.GameController;

import java.util.Scanner;

public class MazeEscape {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("Welcome to MazeGame!");
            System.out.print("Enter width of maze: ");
            int mazeWidth = scanner.nextInt();
            System.out.print("Enter height of maze: ");
            int mazeHeight = scanner.nextInt();
            System.out.print("Enter number of agents: ");
            int numAgents = scanner.nextInt();
            
            int maxTurns = 100;
            
            GameController game = new GameController(mazeWidth, mazeHeight, numAgents, maxTurns);
            game.initializeGame(numAgents);
            game.runSimulation();

            scanner.close();
        }
}
