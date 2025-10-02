#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "Board.h"
#include "Cards.h"

int board_size = 0;
CellState **boardGrid = NULL;
Hand playerHand;

static bool isFleetDestroyed(void) {
    for (int i = 0; i < board_size; i++) {
        for (int j = 0; j < board_size; j++) {
            if (boardGrid[i][j] == SHIP) {
                return false;
            }
        }
    }
    return true;
}

int main(void) {
    int turns = 0;
    int difficulty = 0;
    ShotFunction shotFunction = NULL;

    printf("Welcome to City Defender 2!\n");
    printf("Choose difficulty:\n  Easy = 1\n  Medium = 2\n  Hard = 3\n");

    scanf("%d", &difficulty);
    while (difficulty < 1 || difficulty > 3) {
        printf("Invalid choice, try again.\n");
        printf("Choose difficulty:\n  Easy = 1\n  Medium = 2\n  Hard = 3\n");
        scanf("%d", &difficulty);
    }

    if (difficulty == 1) {
        turns = 30;
    } else if (difficulty == 2) {
        turns = 25;
    } else {
        turns = 15;
    }

    initializeFleet(difficulty);
    initializeDeck();

    while (turns > 0 && !isFleetDestroyed()) {
        printf("/////////////////////\n");
        printf("\n");
        printf("/////////////////////\n");
        printf("You have %d turns left!\n", turns);
        printf("\n");

        displayBoard();
        printf("\n");

        useCard(&shotFunction);
        if (isFleetDestroyed()) {
            printf("Victory! All ships are destroyed.\n");
            displayBoard();
            break;
        }
        turns--;

        if (turns == 0 && !isFleetDestroyed()) {
            printf("\n");
            printf("Defeat! You ran out of turns.\n");
            printf("Remaining ships are shown as 'S' below.\n");
            displayBoardWithShips();
        }
    }

    freeDeck();
    freeBoard();
    return 0;
}
