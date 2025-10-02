# City Defender

Defend your city from an unseen fleet in this Battleship-inspired command line game. Each turn you pick a card from your hand to unleash a different type of attack—single shot, large blast, line strike, radar sweep, or the devastating heavy bomb. Clear the board before you run out of turns to win.

## How to Build

```bash
make -f MAKEFILE
```

This compiles the game into an executable named `CityDefender`.

## How to Play

```bash
./CityDefender
```

1. Choose a difficulty (Easy, Medium, Hard) which sets board size and number of turns.  
2. Review the available attack cards shown in your hand.  
3. Select a card, then enter target coordinates in `row,column` format. Some cards ask for extra input (e.g., attack direction).  
4. Continue playing until every ship is destroyed (victory) or you run out of turns (defeat, with ships revealed).

Good luck!
