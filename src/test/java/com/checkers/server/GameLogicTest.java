package com.checkers.server;

import com.checkers.server.logic.Color;
import com.checkers.server.logic.Game;
import com.checkers.server.logic.Player;
import com.checkers.server.logic.Position;

import java.io.*;

public class GameLogicTest {
	public static void main(String[] args) throws IOException {
		Player p1 = new Player(1, Color.BLACK);
		Player p2 = new Player(2, Color.WHITE);
		Game game = new Game(p1,p2);

		ClassLoader classLoader = new GameLogicTest().getClass().getClassLoader();
		File file = new File(classLoader.getResource("test1.csv").getFile());
		BufferedReader csvReader = new BufferedReader(new FileReader(file));
		String row;
		int count=1;
		while ((row = csvReader.readLine()) != null) {
			System.out.println("--------------  line  " + count++ + " --------------");
			String[] data = row.split(",");
			game.move(p1,new Position(Integer.parseInt(data[0]),Integer.parseInt(data[1])),
					new Position(Integer.parseInt(data[2]),Integer.parseInt(data[3])));
			if(game.getGameState().isGameOver()){
				System.out.println("Winner is " + game.getGameState().getWinnerColor());
				break;
			}
			game.move(p2,new Position(Integer.parseInt(data[4]),Integer.parseInt(data[5])),
					new Position(Integer.parseInt(data[6]),Integer.parseInt(data[7])));
			if(game.getGameState().isGameOver()){
				System.out.println("Winner is " + game.getGameState().getWinnerColor());
				break;
			}
		}
		csvReader.close();
	}
}
