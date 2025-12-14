package GameController;

import Agent.Agent;
import MazeManager.MazeManager;
import TurnManager.TurnManager;

import MazeTile.MazeTile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameController {
        MazeManager maze;
        TurnManager turns;
        int maxTurns;
        int turnCount;
        int firstWinnerId= -1;
    
        public GameController(int width, int height, int numAgents, int maxTurns) {
            this.maze = new MazeManager(width, height, numAgents);
            this.turns = new TurnManager(maze.agents);
            this.maxTurns = maxTurns;
            this.turnCount = 0;
        }
    
        public void initializeGame(int numAgents) {
            System.out.println("Maze initialized:");
            maze.printMazeSnapshot(); 
            System.out.println("Agents placed:");
            
            for (Agent agent : maze.agents) {  
                System.out.println("Agent " + agent.id + " at (" + agent.currentX + "," + agent.currentY + ")");
                
            }
        }
    
        public void runSimulation() {
            Scanner scanner = new Scanner(System.in);
            while (turnCount < maxTurns && turns.allAgentsFinished()!=false) {
                turnCount++;
                turns.advanceTurn(maze);
            
                Agent currentAgent = turns.getCurrentAgent();
                if (currentAgent == null || currentAgent.hasReachedGoal==true) {
                    continue;
                }
                
                System.out.println("\nCurrent Agent: " + currentAgent.id);
                System.out.println("Position: (" + currentAgent.currentX + "," + currentAgent.currentY + ")");
                System.out.print("Enter move (W=UP, A=LEFT, S=DOWN, D=RIGHT, B=BACK, P=POWERUP): ");
                String input = scanner.nextLine().toUpperCase();

                if (input.equals("B")) {
                    currentAgent.backtrack(maze);
                    System.out.println("Agent " + currentAgent.id + " backtracked!");
                    turns.logTurnSummary(currentAgent);
                    maze.printMazeSnapshot();
                    continue; 
                }

                processAgentAction(currentAgent, input);
                
                MazeTile currentTile = maze.getTile(currentAgent.currentX, currentAgent.currentY);
                checkTileEffect(currentAgent, currentTile);
                
                turns.logTurnSummary(currentAgent);
                maze.printMazeSnapshot();
            }
            scanner.close();
            printFinalStatistics();
        }  

        private void processAgentAction(Agent agent, String input) {
            switch (input) {
                case "P": agent.applyPowerUp(maze);
                break;
                    
                case "W": 
                case "A": 
                case "S": 
                case "D": 
                    String direction = convertInputToDirection(input);
                    agent.move(direction, maze);
                    break;
                default: System.out.println("Invalid input!");
                    break;
            }
        }
    
        private String convertInputToDirection(String input) {
            switch (input) {
                case "W": return "UP";
                case "A": return "LEFT";
                case "S": return "DOWN";
                case "D": return "RIGHT";
                default: return "";
            }
        }
    
        private void checkTileEffect(Agent agent, MazeTile tile) {
            if (tile == null) return;
    
            if (tile.type == 'T') {
                System.out.println("Agent " + agent.id + " triggered a TRAP! Teleporting to 2 steps before.");
                agent.applyTrap(maze);
                tile.type = 'E'; 
                maze.totalTrapsTriggered++;
            } else if (tile.type == 'P') {
                System.out.println("Agent " + agent.id + " picked up a POWER-UP!");
                agent.hasPowerUp = true;
                tile.type = 'E'; 
                maze.totalPowerUpsCollected++;
            } else if (tile.type == 'G') {
                System.out.println("Agent " + agent.id + " reached the GOAL!");
                agent.hasReachedGoal = true;
            }
            if (tile.type == 'G' && firstWinnerId == -1) {
                firstWinnerId = agent.id;
            }
        }


        public void printFinalStatistics() {
            System.out.println("\n#### SIMULATION RESULTS ####");
            System.out.println("Total turns: " + turnCount);
            System.out.println("Agents reached goal:");
            int totalMoves = 0;
            for (Agent agent : maze.agents) {
                totalMoves += agent.totalMoves;
            }
            double avgMoves = (double) totalMoves / maze.agents.length;
            
            for (Agent agent : maze.agents) {
                System.out.println("Agent " + agent.id + 
                                 ": Moves=" + agent.totalMoves + 
                                 ", Backtracks=" + agent.backtracks + 
                                 ", Reached goal=" + agent.hasReachedGoal);
            }
            System.out.println("Average moves per agent: " + avgMoves+"\n");
            System.out.println("First winner: Agent " + (firstWinnerId != -1 ? firstWinnerId : "None"));
    
    
            System.out.println("Total traps triggered: " + maze.totalTrapsTriggered);
            System.out.println("Total power-ups collected: " + maze.totalPowerUpsCollected);
            System.out.println("Total power-ups used: " + maze.totalPowerUpsUsed);
            
            System.out.println("\nMax Stack Depths:");
            for (Agent agent : maze.agents) {
                System.out.println("Agent " + agent.id + ": " + agent.maxStackDepth);
            }
            logGameSummaryToFile("game_summary.txt");
        }

        public void logGameSummaryToFile(String filename) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write("#### SIMULATION RESULTS ####\n");
                writer.write("Total turns: " + turnCount + "\n");
                writer.write("Agents reached goal:\n");
                
                int totalMoves = 0;
                for (Agent agent : maze.agents) {
                    totalMoves += agent.totalMoves;
                }
                double avgMoves = (double) totalMoves / maze.agents.length;
                
                for (Agent agent : maze.agents) {
                    writer.write("Agent " + agent.id + 
                               ": Moves=" + agent.totalMoves + 
                               ", Backtracks=" + agent.backtracks + 
                               ", Reached goal=" + agent.hasReachedGoal + "\n");
                }
                writer.write(String.format("Average moves per agent: %.2f\n", avgMoves));
                writer.write("First winner: Agent " + (firstWinnerId != -1 ? firstWinnerId : "None") + "\n");
                
                writer.write("Total traps triggered: " + maze.totalTrapsTriggered + "\n");
                writer.write("Total power-ups collected: " + maze.totalPowerUpsCollected + "\n");
                writer.write("Total power-ups used: " + maze.totalPowerUpsUsed + "\n");
                
                writer.write("\nMax Stack Depths:\n");
                for (Agent agent : maze.agents) {
                    writer.write("Agent " + agent.id + ": " + agent.maxStackDepth + "\n");
                }
                
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
}
