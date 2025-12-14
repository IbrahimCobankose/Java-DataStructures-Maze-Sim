Java Data Structures Maze Sim
A turn-based multi-agent maze escape simulation implemented in Java.

This project demonstrates the practical application of fundamental data structures—specifically Stacks, Queues, and Circular Linked Lists—built from scratch to solve game logic problems without relying on the standard Java Collections Framework.

📌 Project Overview
The simulation manages multiple autonomous agents navigating a procedurally generated, dynamic maze environment. The core challenge lies in the shifting nature of the maze and the implementation of custom data structures to handle game state, turns, and movement history efficiently.

Core Technical Concepts
Turn Management (Queue): A custom Queue implementation handles the round-robin scheduling of multiple agents, ensuring fair turn distribution.

Backtracking Logic (Stack): A Stack-based history system records every move an agent makes. This allows for complex behaviors such as "undoing" moves or automatically backtracking when a trap is triggered.

Dynamic Map Manipulation (Circular Linked List): To simulate a shifting environment, specific rows of the maze rotate periodically. This is achieved efficiently using Circular Linked Lists, allowing for grid manipulation without heavy array copying overhead.

🚀 Features
Procedural Maze Generation: Creates a unique, solvable maze grid for every session.

Multi-Agent Simulation: Supports simultaneous simulation of multiple agents with unique IDs and states.

Interactive Gameplay:

Traps: Force agents to backtrack to previous positions.

Power-ups: Allow special movements like teleportation.

Comprehensive Logging: Automatically generates detailed logs (game_summary.txt) tracking metrics such as total moves, backtracks, recursion depth, and turn history.

📂 Project Structure
main: Contains the entry point of the application (MazeEscape.java).

DataStructures: Custom implementations of Stack, Queue, and CircularLinkedList.

Agent: Logic for player movement and state.

MazeManager: Handles grid generation and dynamic row rotation.

GameController: Orchestrates the simulation loop and rules.

🛠️ How to Run
Clone the repository:

Bash

git clone https://github.com/YOUR_USERNAME/Java-DataStructures-Maze-Sim.git
Navigate to the source directory:

Bash

cd Java-DataStructures-Maze-Sim/src
Compile the project:

Bash

javac main/MazeEscape.java
Run the simulation:

Bash

java main.MazeEscape
👥 Contributors
This project was developed by:

İbrahim Çobanköse

Emir Karayılan

Enes Türkmenoğlu
