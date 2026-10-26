# Team-NoLaptop-OOP-Project
CS3354.004

## Members
Orlando Pina-Tovar, Bryce Gill, Pratyush Karki, Karsten Salinas

## Environment
- Java Development Kit (JDK) 17 or newer (tested with JDK 21). `javac` and `java` must be on your PATH.
- No build tool. Compile with `javac`.
- Tests use the JUnit console jar included in `lib/`.

## Build and Run
Run all commands from the project root.

**Windows (Command Prompt):**
```text
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out Main
```

**Mac / Linux:**
```text
javac -d out $(find src -name "*.java")
java -cp out Main
```

## How to Play
- Enter a move as two squares, for example `E2 E4`.
- Lowercase letters and extra spaces are accepted (`e2   e4`).
- White moves first, then turns alternate.
- Type `quit` to exit.

Example session:
```text
White to move: E2 E4
Move successful.
Black to move: hello
Invalid format. Enter a move like E2 E4.
Black to move: E9 E4
Invalid format. Enter a move like E2 E4.
Black to move: E7 E5
Move successful.
```
Board symbols: `wp`/`bp` pawn, `R` rook, `N` knight, `B` bishop, `Q` queen, `K` king (prefix `w` = white, `b` = black). `##` is an empty square.

## Running the Tests
```text
dir /s /b src\*.java test\*.java > sources.txt
javac -d out -cp "lib\junit-platform-console-standalone-1.14.4.jar" @sources.txt
java -jar lib\junit-platform-console-standalone-1.14.4.jar execute --class-path out --scan-class-path
```
(On Mac/Linux, use `/` in paths and `find src test -name "*.java"` to list the sources.)
Expected result: 11 tests found, 11 successful, 0 failed.

## Project Structure
- `src/board`: `Board` (8x8 grid, setup, display, moving pieces)
- `src/Pieces`: abstract `Piece` plus `Pawn`, `Rook`, `Knight`, `Bishop`, `Queen`, `King`
- `src/game`: `Game` (console loop, turns, input checks)
- `src/utils`: `Utils` (move-format check, coordinate conversion)
- `test/`: JUnit tests for `Board` and `Utils`
- `docs/`: generated Javadoc (open `docs/index.html`)

## Features
**Implemented**
- 8x8 board with A-H / 1-8 coordinate labels and correct starting position
- Move-input validation and coordinate conversion, tolerant of case and spaces
- Movement rules for all six piece types, including blocked paths and captures
- Alternating turns and ownership checks (cannot move the opponent's pieces or capture your own)
- Clear error messages; invalid input does not change the board or the turn
- Javadoc on all classes, and JUnit tests

**Not implemented / limitations**
- Check, checkmate, and stalemate detection
- Castling, en passant, and pawn promotion
- King safety is not enforced
- `Game` has its own copies of some helpers that also exist in `Utils`