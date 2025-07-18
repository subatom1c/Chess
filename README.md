# Chess
Terminal chess.
find src -name "*.java" > sources.txt
javac -d out @sources.txt
java -cp out game.Game
