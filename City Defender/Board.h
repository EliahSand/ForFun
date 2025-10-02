#ifndef BOARD_H
#define BOARD_H

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

typedef enum { UNFIRED, MISS, HIT, SHIP } CellState;

extern int board_size;
extern CellState **boardGrid;

void initializeBoard(int size);
void freeBoard(void);
void initializeFleet(int difficulty);
void displayBoard(void);
void displayBoardWithShips(void);

// Implementation

void initializeBoard(int size) {
    board_size = size;
    boardGrid = malloc(size * sizeof(CellState *));
    if (boardGrid == NULL) {
        fprintf(stderr, "Failed to allocate memory for the board rows.\n");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < size; i++) {
        boardGrid[i] = malloc(size * sizeof(CellState));
        if (boardGrid[i] == NULL) {
            fprintf(stderr, "Failed to allocate memory for a board column.\n");
            exit(EXIT_FAILURE);
        }
        for (int j = 0; j < size; j++) {
            boardGrid[i][j] = UNFIRED;
        }
    }
}

void freeBoard(void) {
    for (int i = 0; i < board_size; i++) {
        free(boardGrid[i]);
    }
    free(boardGrid);
    boardGrid = NULL;
    board_size = 0;
}

void initializeFleet(int difficulty) {
    int shipSizes[9];
    int numberOfShips;

    switch (difficulty) {
        case 1:
            initializeBoard(11);
            numberOfShips = 5;
            shipSizes[0] = 2;
            shipSizes[1] = 2;
            shipSizes[2] = 3;
            shipSizes[3] = 4;
            shipSizes[4] = 5;
            break;
        case 2:
            initializeBoard(17);
            numberOfShips = 7;
            shipSizes[0] = 2;
            shipSizes[1] = 2;
            shipSizes[2] = 2;
            shipSizes[3] = 3;
            shipSizes[4] = 3;
            shipSizes[5] = 4;
            shipSizes[6] = 5;
            break;
        case 3:
        default:
            initializeBoard(21);
            numberOfShips = 9;
            shipSizes[0] = 2;
            shipSizes[1] = 2;
            shipSizes[2] = 2;
            shipSizes[3] = 3;
            shipSizes[4] = 3;
            shipSizes[5] = 4;
            shipSizes[6] = 4;
            shipSizes[7] = 5;
            shipSizes[8] = 5;
            break;
    }

    srand((unsigned int)time(NULL));

    for (int i = 0; i < numberOfShips; i++) {
        int shipSize = shipSizes[i];
        bool placed = false;

        while (!placed) {
            int orientation = rand() % 2; // 0 horizontal, 1 vertical
            int row;
            int col;

            if (orientation == 0) {
                row = rand() % board_size;
                col = rand() % (board_size - shipSize + 1);

                bool clear = true;
                for (int j = 0; j < shipSize; j++) {
                    if (boardGrid[row][col + j] == SHIP) {
                        clear = false;
                        break;
                    }
                }

                if (clear) {
                    for (int j = 0; j < shipSize; j++) {
                        boardGrid[row][col + j] = SHIP;
                    }
                    placed = true;
                }
            } else {
                row = rand() % (board_size - shipSize + 1);
                col = rand() % board_size;

                bool clear = true;
                for (int j = 0; j < shipSize; j++) {
                    if (boardGrid[row + j][col] == SHIP) {
                        clear = false;
                        break;
                    }
                }

                if (clear) {
                    for (int j = 0; j < shipSize; j++) {
                        boardGrid[row + j][col] = SHIP;
                    }
                    placed = true;
                }
            }
        }
    }
}

void displayBoard(void) {
    printf("    ");
    for (int i = 0; i < board_size; i++) {
        printf("%2d  ", i);
    }
    printf("\n");

    for (int i = 0; i < board_size; i++) {
        printf("%2d ", i);
        for (int j = 0; j < board_size; j++) {
            printf("| ");
            switch (boardGrid[i][j]) {
                case UNFIRED:
                case SHIP:
                    printf(" ");
                    break;
                case MISS:
                    printf("O");
                    break;
                case HIT:
                    printf("X");
                    break;
            }
            printf(" ");
        }
        printf("|\n");
    }

    printf("    \n");
}

void displayBoardWithShips(void) {
    printf("    ");
    for (int i = 0; i < board_size; i++) {
        printf("%2d  ", i);
    }
    printf("\n");

    for (int i = 0; i < board_size; i++) {
        printf("%2d ", i);
        for (int j = 0; j < board_size; j++) {
            printf("| ");
            switch (boardGrid[i][j]) {
                case UNFIRED:
                    printf(" ");
                    break;
                case MISS:
                    printf("O");
                    break;
                case HIT:
                    printf("X");
                    break;
                case SHIP:
                    printf("S");
                    break;
            }
            printf(" ");
        }
        printf("|\n");
    }

    printf("    \n");
}

#endif
