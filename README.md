# Team-NoLaptop-OOP-Project
CS3354.004
# Members
Orlando Pina-Tovar, Bryce Gill, Pratyush Karki, Karsten Salinas
## Running the Phase 1 Console

Requires a Java Development Kit (JDK) with javac and java available.

From the project folder, compile:

```text
javac -d out Board.java Pieces/*.java Game.java
```

Run:

```text
java -cp out Game
```

## Console Controls

- Enter moves as two coordinates, such as E2 E4.
- Lowercase letters and extra spaces are accepted.
- White moves first.
- Invalid moves leave the board and current turn unchanged.
- Type quit to exit.

## Implemented Features

- Board display with coordinates
- Move-input validation and coordinate conversion
- Basic piece movement and captures
- Alternating turns and piece-ownership checks
- Error messages that allow players to retry

## Current Limitations

Check, checkmate, stalemate, castling, en passant, and pawn promotion
are not implemented. King safety is not enforced.