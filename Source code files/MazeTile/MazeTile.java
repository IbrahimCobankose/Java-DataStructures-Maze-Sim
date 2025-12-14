package MazeTile;


public class MazeTile {
        public int x, y;
        public char type; 
        public boolean hasAgent;
        public int agentId = -1;
        
    
        public MazeTile(int x, int y, char type) {
            this.x = x;                                
            this.y = y;
            this.type = type;                           
            this.hasAgent = false;                      
        }
    
        public boolean isTraversable() {               
            if(type != 'W' ){
                return true;
            }else{
                return false;
            }
        }
    
        @Override
        public String toString() {              
            if (hasAgent==true && agentId!=-1)      
            {
                return String.valueOf(agentId);
            }
            switch (type) {
                
                case 'W': return "▓";
                case 'T': return "T";
                case 'P': return "P";
                case 'G': return "G";
                case 'E': return " ";
                default : return null;
            }
        }
}
