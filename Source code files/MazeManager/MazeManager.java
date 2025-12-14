package MazeManager;

import MazeTile.MazeTile;
import Agent.Agent;
import DataStructures.CircularLinkedList;




import java.util.Random;

public class MazeManager {
        public MazeTile[][] grid;
        public int width, height;
        public Agent[] agents;
        public CircularLinkedList rotatingRows;
        Random rand = new Random();
        public int totalTrapsTriggered = 0;
        public int totalPowerUpsCollected = 0;
        public int totalPowerUpsUsed = 0;
    
        public MazeManager(int width, int height, int numAgents) {              

            this.width = width;
            this.height = height;
            this.grid = new MazeTile[height][width];
            this.agents = new Agent[numAgents];
            this.rotatingRows = new CircularLinkedList();
            
            generateMaze();
            placeTrapsAndPowerUps();
            for (int i = 0; i < numAgents; i++) {
                agents[i] = new Agent(i, 1, 0);
                grid[0][1].hasAgent = true; 
                grid[0][1].agentId=i;
            }
        }
    
        private void generateMaze() {  
            
            Random rand = new Random();
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    grid[i][j] = new MazeTile(j, i, 'W');
                }
            }

            int startX = 1 + rand.nextInt((width - 3) / 2) * 2;
            int startY = rand.nextInt((height - 3) / 2) * 2;
            createCorridor(startX, startY);       

            
            grid[0][1].type = 'E'; 
            grid[1][1].type = 'E';
            grid[height-1][width-2].type = 'G'; 
            grid[height-2][width-2].type = 'E';
            
        }

        private void createCorridor(int x, int y) {
            grid[y][x].type = 'E'; 
            
            
            int[][] directions = {{0, -2}, {2, 0}, {0, 2}, {-2, 0}};
            mixCorridor(directions);  

            for (int[] direction : directions) {
                int newx = x + direction[0];
                int newy = y + direction[1];

                if (newx > 0 && newx < width-1 && newy > 0 && newy < height-1 && grid[newy][newx].type == 'W') {
                    grid[y + direction[1]/2][x + direction[0]/2].type = 'E'; 
                    createCorridor(newx, newy);
                }
            }
        }

        private void mixCorridor(int[][] directions) { 
            Random rand = new Random();

            for (int i = directions.length - 1; i > 0; i--) {
                int index = rand.nextInt(i + 1);
                int[] temp = directions[index];
                directions[index] = directions[i];
                directions[i] = temp;
            }
        }

        private void placeTrapsAndPowerUps() {
            for (int i = 1; i < height - 1; i++) {
                for (int j = 1; j < width - 1; j++) {
                    if (grid[i][j].type == 'E' && rand.nextDouble() < 0.3) {
                        if (rand.nextBoolean()) {
                            grid[i][j].type = 'T';
                        } else {
                            grid[i][j].type = 'P';
                        }
                    }
                }
            }
        }
        
    
        public void rotateCorridor(int rowIndex) { 
            CircularLinkedList rotatingRows = new CircularLinkedList();
            for (int j = 0; j < width; j++) {
                rotatingRows.add(grid[rowIndex][j]);
            }
            rotatingRows.rotate();
            MazeTile[] rotated = rotatingRows.toList();
            for (int j = 0; j < width; j++) {
                grid[rowIndex][j] = rotated[j];
            }
        }
    
        public boolean isValidMove(int fromX, int fromY, String direction) {
            int toX = fromX;
            int toY = fromY;
            
            switch (direction) {
                case "UP": toY--; break;
                case "DOWN": toY++; break;
                case "LEFT": toX--; break;
                case "RIGHT": toX++; break;
            }
            
            if (toX < 0 || toX >= width || toY < 0 || toY >= height) {
                return false;
            }
            
            return grid[toY][toX].isTraversable();
        }
    
        public MazeTile getTile(int x, int y) {
            if (x < 0 || x >= width || y < 0 || y >= height){
                return null;
            }
            return grid[y][x];
        }
    
        public void updateAgentLocation(Agent agent, int newX, int newY) { 
            agent.currentX = newX;
            agent.currentY = newY;

        }

        public void refreshAgentPositions() {
           
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    grid[y][x].hasAgent = false;
                    grid[y][x].agentId = -1;
                }
            }
            
            for (Agent agent : agents) {
                if (!agent.hasReachedGoal) {
                    MazeTile tile = grid[agent.currentY][agent.currentX];
                    tile.hasAgent = true;
                    tile.agentId = agent.id; 
                }
            }
        }
    
        public void printMazeSnapshot() { 
            
            refreshAgentPositions();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(grid[i][j]);
                }
                System.out.println();
            }
        }
}
