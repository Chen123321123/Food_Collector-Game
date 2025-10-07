# Food Collector

A desktop **Java** 2D arcade-style game: navigate a maze, **avoid enemies**, **collect ingredients**, **complete recipes**, and reach the exit before you’re caught.

- Language: Java (desktop application)
- Architecture: grid-based, tick updates (player moves first → enemies move next)
- Assets: levels/recipes loaded from config files (extensible)

- Arrow keys to move; at most one cell per tick
- Enemies pursue the player each tick (greedy chase), cannot pass through walls
- Collect ingredients → complete recipes to score; traps reduce score (score < 0 = lose)
- After all **regular** recipe goals are met, the exit opens; reach it to win
