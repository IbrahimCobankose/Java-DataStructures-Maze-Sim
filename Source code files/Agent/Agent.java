package Agent;

import DataStructures.Stack;
import MazeManager.MazeManager;
import MazeTile.MazeTile;

import java.util.Random;


public class Agent {
        public int id;
        public int currentX, currentY;    
        public Stack moveHistory;          
        public boolean hasReachedGoal;     
        public int totalMoves;             
        public int backtracks;             
        public boolean hasPowerUp;         
        public int maxStackDepth = 0;
    
        public Agent(int id,int currentX, int currentY) {
            this.id = id;
            this.currentX = currentX;          
            this.currentY = currentY;
            this.moveHistory = new Stack();   
            this.hasReachedGoal = false;
            this.totalMoves = 0;
            this.backtracks = 0;
            this.hasPowerUp = false;
            recordMove(1, 0);       
        }
    
        public void move(String direction, MazeManager maze) {  
                                                                
            int oldX = currentX;
            int oldY = currentY;
            int moveX = oldX;
            int moveY = oldY;
    
            switch (direction) {
                case "UP": moveY--; break;
                case "DOWN": moveY++; break;
                case "LEFT": moveX--; break;
                case "RIGHT": moveX++; break;
            }
    
            if (maze.isValidMove(oldX, oldY, direction)) {
                currentX = moveX;
                currentY = moveY;
                maze.updateAgentLocation(this, moveX, moveY);
                recordMove(moveX, moveY);
                totalMoves++;
            }
        }
    
        public void backtrack(MazeManager maze) {  
            if (moveHistory.size() <= 1) return;

            moveHistory.pop(); 

            
            if (moveHistory.isEmpty()) return;
            String lastMove = moveHistory.peek(); 
            String[] parts = lastMove.split(",");
            int prevX = Integer.parseInt(parts[0]);
            int prevY = Integer.parseInt(parts[1]);

            
            currentX = prevX;
            currentY = prevY;
            backtracks++;

            maze.refreshAgentPositions();
        }
    
        public void applyPowerUp(MazeManager maze) {        
            if (hasPowerUp) {
                hasPowerUp = false;
                maze.totalPowerUpsUsed++;
                
                int goalRow = -1;
                for (int y = 0; y < maze.height; y++) {
                    for (int x = 0; x < maze.width; x++) {
                        if (maze.grid[y][x].type == 'G') {
                            goalRow = y;
                            break;
                        }
                    }
                    if (goalRow != -1) break;
                }
                
                int targetRow = goalRow - 2;
                int emptyTileCount = 0;
                
                if (targetRow >= 0) {  
                    for (int x = 0; x < maze.width; x++) {
                        MazeTile tile = maze.grid[targetRow][x];
                        if (tile.type == 'E' && !tile.hasAgent) {  
                            emptyTileCount++;
                        }
                    }
                }

                MazeTile[] emptyTilesArray = new MazeTile[emptyTileCount];
                int currentIndex = 0;
                
                if (targetRow >= 0) {
                    for (int x = 0; x < maze.width; x++) {
                        MazeTile tile = maze.grid[targetRow][x];
                        if (tile.type == 'E' && !tile.hasAgent) {
                            emptyTilesArray[currentIndex++] = tile;
                        }
                    }
                }
                
                if (emptyTileCount > 0) {
                    Random rand = new Random();
                    MazeTile targetTile = emptyTilesArray[rand.nextInt(emptyTileCount)];
                    
                    maze.getTile(currentX, currentY).hasAgent = false;
                    maze.getTile(currentX, currentY).agentId = -1;
                    
                    currentX = targetTile.x;
                    currentY = targetTile.y;
                    
                    maze.getTile(currentX, currentY).hasAgent = true;
                    maze.getTile(currentX, currentY).agentId = this.id;
                    
                    System.out.println("Agent " + id + " used power-up and teleported to (" + currentX + "," + currentY + ")");
                    
                    if (maze.grid[targetRow+1][currentX].type == 'G') {  
                        hasReachedGoal = true;
                        System.out.println("Agent " + id + " reached the GOAL right after teleportation!");
                    }
                } else {
                    System.out.println("No empty space available above goal row. Power-up not used.");
                    hasPowerUp = true;  
                }
            } else {
                System.out.println("Agent " + id + " has no power-up to use!");
            }
        }
        
        public void applyTrap(MazeManager maze){
            backtrack(maze);
            backtrack(maze);
        }
    
        public void recordMove(int x, int y) { 
            moveHistory.push(x + "," + y);
            if (moveHistory.size() > maxStackDepth) {
                maxStackDepth = moveHistory.size();
            }
        }
    
        public String getMoveHistoryAsString() {
            return moveHistory.toString();
        }
}
