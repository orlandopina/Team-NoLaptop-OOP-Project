# AI Usage — Navy316

## October 25, 2026 — Initial console loop
- Asked: Help build Part 6 incrementally using our class concepts.
- Used or changed: Added an AI-assisted Game class with a Scanner,
  an input loop, and a case-insensitive quit command.
- Result: Ran Game.java directly. Tested E2 E4, hello, and QUIT.
  Input was echoed, the prompt repeated, and QUIT ended the program.
  Board integration and move validation are not implemented yet.
- AI created the commit: No.
## October 25, 2026 — Move-format validation
- Asked: Help validate move input before connecting it to the board.
- Used or changed: Added isValidMoveFormat() and an error message
  that lets the player retry. Accepts lowercase and extra spaces.
- Result: Tested valid coordinates, lowercase input, extra spaces,
  an invalid rank, text, blank input, and quit. All worked as expected.
  This checks formatting only, not whether a move is legal.
- AI created the commit: No.
## October 25, 2026 — Coordinate conversion
- Asked: Help convert chess coordinates into board array indexes.
- Used or changed: Added getRow() and getColumn(), then displayed
  the source and destination indexes after validating input.
- Result: Tested E2 E4, A8 H1, and b1 c3. All indexes matched
  the expected values. Board integration is still pending.
- AI created the commit: No.