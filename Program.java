package ep1coo;

import java.util.Scanner;


public class Program {
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		GameImpl game = new GameImpl();
	    
	    while(!game.checkVictory(Color.RED) && !game.checkVictory(Color.BLUE)) {
	    	game.printBoard();
	    	System.out.println("Carta da mesa: " + game.getTableCard().getName());
	    	System.out.println("\nBlue cartas: ");
	    	for (Card card : game.getBluePlayer().getCards()) {
	    	    System.out.print(card.getName());
	    	    for (Position move : card.getPositions()) {
		    	    System.out.print(" (" + move.getRow() + " " + move.getCol() +  ") ");
		    	}
	    	    System.out.println();
	    	}
	    	System.out.println("\nRed cartas: ");
	    	for (Card card : game.getRedPlayer().getCards()) {
	    	    System.out.print(card.getName());
	    	    for (Position move : card.getPositions()) {
		    	    System.out.print(" (" + move.getRow() + " " + move.getCol() +  ") ");
		    	}
	    	    System.out.println();
	    	}
	    	
	    	System.out.println("\nAguardando jogador: " + game.getTurn());
	    	
	    	System.out.print("\nSelecione uma carta [nome] : ");
	    	String cardName= sc.nextLine();
	    	System.out.print("\nSelecione uma peça [x y] : ");
	    	String piece = sc.nextLine();
	    	System.out.print("\nSelecione um movimento [z]: ");
	    	int move = sc.nextInt();
	    	sc.nextLine();
	    	String piecePos[] = piece.split("");
	    	Position pieceSelected =  new Position(Integer.parseInt(piecePos[0]), Integer.parseInt(piecePos[2]));
	    	
	    	Card cardSelected = new Card(null, null, null);
	    	
	    	for (Card card : game.getCurrentPlayer().getCards()) {
	    	   if(card.getName().equals(cardName)) {
	    		  cardSelected = card;
	    	   }
	    	}
	    	
	    	game.makeMove(cardSelected, cardSelected.getPositions()[move], pieceSelected);
	    	
	    }
	    if(game.checkVictory(Color.RED)) {
	    	System.out.println("RED venceu!");
	    }
	    else if(game.checkVictory(Color.BLUE)) {
	    	System.out.println("BLUE venceu!");
	    }
		
	}
	
	

}
