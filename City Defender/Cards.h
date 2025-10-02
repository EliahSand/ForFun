#ifndef CARDS_H
#define CARDS_H

#include <stdio.h>
#include <stdlib.h>

#include "Board.h"

typedef void *(*ShotFunction)(int, int);

typedef struct Hand {
    ShotFunction *cards;
    int available;
} Hand;

extern Hand playerHand;

void initializeDeck(void);
void freeDeck(void);
void displayDeck(void);
void useCard(ShotFunction *shotFunction);

void *simpleShot(int x, int y);
void *largeShot(int x, int y);
void *lineShot(int x, int y);
void *radarShot(int x, int y);
void *heavyBombShot(int x, int y);
void initializeDeck(void) {
    playerHand.available = 5;
    playerHand.cards = malloc(5 * sizeof(ShotFunction));
    if (playerHand.cards == NULL) {
        fprintf(stderr, "Failed to allocate memory for the deck.\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < 5; i++) {
        playerHand.cards[i] = &simpleShot;
    }
}

void freeDeck(void) {
    free(playerHand.cards);
    playerHand.cards = NULL;
    playerHand.available = 0;
}

void displayDeck(void) {
    printf("Available cards:\n");
    for (int i = 0; i < playerHand.available; i++) {
        if (playerHand.cards[i] == &simpleShot) {
            printf("%d: SimpleShot\n", i + 1);
        } else if (playerHand.cards[i] == &largeShot) {
            printf("%d: LargeShot\n", i + 1);
        } else if (playerHand.cards[i] == &lineShot) {
            printf("%d: LineShot\n", i + 1);
        } else if (playerHand.cards[i] == &radarShot) {
            printf("%d: RadarShot\n", i + 1);
        } else if (playerHand.cards[i] == &heavyBombShot) {
            printf("%d: HeavyBombShot\n", i + 1);
        }
    }
    printf("\n");
}

void useCard(ShotFunction *shotFunction) {
    int selectedCard;
    int x;
    int y;

    displayDeck();

    printf("Select a card (1 - %d): ", playerHand.available);
    scanf("%d", &selectedCard);

    if (selectedCard < 1 || selectedCard > playerHand.available) {
        printf("Invalid card selection.\n");
        return;
    }

    printf("Enter coordinates in the following format: Y,X: ");
    scanf("%d,%d", &x, &y);

    if (x < 0 || x >= board_size || y < 0 || y >= board_size) {
        printf("Coordinates are out of bounds.\n");
        return;
    }

    *shotFunction = playerHand.cards[selectedCard - 1];

    ShotFunction nextShotFunction = (*shotFunction)(x, y);

    if (nextShotFunction != NULL) {
        playerHand.cards[selectedCard - 1] = nextShotFunction;
    } else {
        printf("IMPORTANT!\n");
        printf("The heavy bomb destroyed this cannon; try another card.\n");
    }
}

void *simpleShot(int x, int y) {
    if (boardGrid[x][y] == SHIP || boardGrid[x][y] == HIT) {
        printf("HIT at (%d , %d)!\n", x, y);
        boardGrid[x][y] = HIT;
    } else {
        boardGrid[x][y] = MISS;
        printf("MISS at (%d , %d).\n", x, y);
    }

    int randomValue = rand() % 100;
    if (randomValue < 65) {
        return &simpleShot;
    } else if (randomValue < 85) {
        return &largeShot;
    } else if (randomValue < 90) {
        return &lineShot;
    } else {
        return &radarShot;
    }
}

void *largeShot(int x, int y) {
    printf("Large shot at (%d , %d).\n", x, y);
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if (x + i >= 0 && x + i < board_size && y + j >= 0 && y + j < board_size) {
                if (boardGrid[x + i][y + j] == SHIP || boardGrid[x + i][y + j] == HIT) {
                    printf("HIT at (%d, %d).\n", x + i, y + j);
                    boardGrid[x + i][y + j] = HIT;
                } else {
                    boardGrid[x + i][y + j] = MISS;
                }
            }
        }
    }

    int randomValue = rand() % 100;
    if (randomValue < 80) {
        return &simpleShot;
    } else if (randomValue < 83) {
        return &largeShot;
    } else if (randomValue < 93) {
        return &lineShot;
    } else if (randomValue < 98) {
        return &radarShot;
    } else {
        return &heavyBombShot;
    }
}

void *lineShot(int x, int y) {
    int direction;
    printf("Select direction: 1 for vertical, 2 for horizontal: ");
    scanf("%d", &direction);

    if (direction == 1) {
        for (int i = -2; i <= 2; i++) {
            if (x + i >= 0 && x + i < board_size) {
                if (boardGrid[x + i][y] == SHIP || boardGrid[x + i][y] == HIT) {
                    printf("HIT at (%d, %d).\n", x + i, y);
                    boardGrid[x + i][y] = HIT;
                } else {
                    boardGrid[x + i][y] = MISS;
                }
            }
        }
    } else {
        for (int i = -2; i <= 2; i++) {
            if (y + i >= 0 && y + i < board_size) {
                if (boardGrid[x][y + i] == SHIP || boardGrid[x][y + i] == HIT) {
                    printf("HIT at (%d, %d).\n", x, y + i);
                    boardGrid[x][y + i] = HIT;
                } else {
                    boardGrid[x][y + i] = MISS;
                }
            }
        }
    }

    int randomNumber = rand() % 100;
    if (randomNumber < 85) {
        return &simpleShot;
    } else if (randomNumber < 90) {
        return &largeShot;
    } else if (randomNumber < 92) {
        return &lineShot;
    } else if (randomNumber < 98) {
        return &radarShot;
    } else {
        return &heavyBombShot;
    }
}

void *radarShot(int x, int y) {
    printf("Radar shot at (%d, %d).\n", x, y);
    for (int i = -2; i <= 2; i++) {
        for (int j = -2; j <= 2; j++) {
            if (x + i >= 0 && x + i < board_size && y + j >= 0 && y + j < board_size) {
                if (boardGrid[x + i][y + j] == SHIP || boardGrid[x + i][y + j] == HIT) {
                    printf("Ship at (%d, %d)!\n", x + i, y + j);
                } else {
                    printf("No ship at (%d, %d).\n", x + i, y + j);
                }
            }
        }
    }

    int randomNumber = rand() % 100;
    if (randomNumber < 75) {
        return &simpleShot;
    } else if (randomNumber < 90) {
        return &largeShot;
    } else if (randomNumber < 95) {
        return &lineShot;
    } else if (randomNumber < 97) {
        return &radarShot;
    } else {
        return &heavyBombShot;
    }
}

void *heavyBombShot(int x, int y) {
    printf("Heavy bomb (500 kg) at (%d, %d).\n", x, y);
    for (int i = -5; i <= 5; i++) {
        for (int j = -5; j <= 5; j++) {
            if (x + i >= 0 && x + i < board_size && y + j >= 0 && y + j < board_size) {
                if (boardGrid[x + i][y + j] == SHIP || boardGrid[x + i][y + j] == HIT) {
                    printf("HIT at (%d, %d)!\n", x + i, y + j);
                } else {
                    boardGrid[x + i][y + j] = MISS;
                }
            }
        }
    }
    printf("Cannon destroyed after the heavy bomb!\n");
    return NULL;
}

#endif
