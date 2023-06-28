# Wordle Bot 1.0

---

For now, this program runs a command line interface to solve Wordle.

The program will start by guessing the word "soare". Your instructions
are then to report the accuracy of the word "soare" and every subsequent guess
by submitting a 5-character string of b's, y's, and g's. A b represents a black
(or gray), y represents yellow, and g represents green. Every time you submit
information about the last guess, the program returns a new guess or prints
an error that can only be caused by faulty information from the user.

The dictionary used by this program is the same one used by New York Times.
There are two mutually exclusive text files containing all the words.
`wordle-La.txt` contains all of the words that could possibly be the solution.
`wordle-Ta.txt` contains guessable words that cannot be the solution.