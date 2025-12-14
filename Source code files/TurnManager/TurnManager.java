package TurnManager;


import MazeManager.MazeManager;
import DataStructures.Queue;
import Agent.Agent;

import java.util.Random;

public class TurnManager {
        public Queue agentQueue;
        public int currentRound;
    
        public TurnManager(Agent[] agents) {
            this.agentQueue = new Queue();
            for (Agent agent : agents) {
                agentQueue.enqueue(agent);
            }
            this.currentRound = 0;
        }
    
        public void advanceTurn(MazeManager maze) {  
            currentRound++;
        
            if (currentRound % 3 == 0 ) {
                int randomRow =  new Random().nextInt(maze.height - 2) + 1;
                maze.rotateCorridor(randomRow);
                System.out.println("Row " + randomRow + " rotated!");
            } 
            
            Agent current = agentQueue.dequeue();
            if (!current.hasReachedGoal) {
                agentQueue.enqueue(current);
            }
        }
    
        public Agent getCurrentAgent() {
            
            if (agentQueue.isEmpty()==true) {
                return null;
            } else {
                return agentQueue.peek(); 
            }
        }
    
        public boolean allAgentsFinished() {
            
            if (agentQueue.isEmpty()==true){
                return true;
            }
    
            Queue.Node current = agentQueue.front;
            boolean allFinished = true;
            int agentsChecked = 0;
            
            while (current != null && agentsChecked < agentQueue.size()) {
                if (current.data.hasReachedGoal==true) {
                    allFinished = false;
                    break;
                }
                current = current.next;
                agentsChecked++;
            }
            
            return allFinished;
        }
    
        public void logTurnSummary(Agent agent) {  
            System.out.println("Turn " + currentRound + ": Agent " + agent.id);
            System.out.println("Position: (" + agent.currentX + "," + agent.currentY + ")");
            System.out.println("Move history: " + agent.getMoveHistoryAsString());
        }
}
