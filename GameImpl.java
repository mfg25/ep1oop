package ep1coo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import chess.ChessPiece;
import chess.Color;


/**
 * Classe que contém métodos que serão chamados para a execução do jogo
 */
public class GameImpl implements Game{
    private final int BOARD_SIZE = 5;
    private Color turn;
    private Spot[][] board;
    private Player redPlayer;
    private Player bluePlayer;
    private Piece blueMaster;
    private Piece redMaster;
    private Card tableCard;
    private Card[] deck;
    
    public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_CYAN = "\u001B[36m";


    public GameImpl(){
        this.board = Spot.createBoard(this.BOARD_SIZE);
        this.deck = Card.createCards();

        blueMaster = board[4][2].getPiece();
        redMaster = board[0][2].getPiece();
        
        this.bluePlayer = new Player("Blue", Color.BLUE, this.deck[2], this.deck[3]);
        this.redPlayer = new Player("Red", Color.RED, this.deck[0], this.deck[1]);
        
        this.tableCard = this.deck[4];
        this.turn = this.tableCard.getColor() == Color.RED ? Color.RED : Color.BLUE;
    }

    public GameImpl(String redPlayerName, String bluePlayerName){
        this.board = Spot.createBoard(this.BOARD_SIZE);
        this.deck = Card.createCards();

        blueMaster = board[4][2].getPiece();
        redMaster = board[0][2].getPiece();
       
        this.bluePlayer = new Player(bluePlayerName, Color.BLUE, this.deck[0], this.deck[1]);
        this.redPlayer = new Player(redPlayerName, Color.RED, this.deck[2], this.deck[3]);

        this.tableCard = this.deck[4];
        this.turn = this.tableCard.getColor() == Color.RED ? Color.RED : Color.BLUE;
    }

    public GameImpl(String redNamePlayer, String blueNamePlayer, Card[] cards){
        this.board = Spot.createBoard(this.BOARD_SIZE);
        redMaster = board[0][2].getPiece();
        blueMaster = board[4][2].getPiece();

        ArrayList<Card> cardsList = new ArrayList<Card>(Arrays.asList(cards));

        if (cardsList.contains(null)) {
            throw new IllegalArgumentException("Cards cannot be null");
        }

        Collections.shuffle(cardsList);

        this.deck = cardsList.subList(0, 5).toArray(new Card[5]);

        this.redPlayer = new Player(redNamePlayer, Color.RED, this.deck[0], this.deck[1]);
        this.bluePlayer = new Player(blueNamePlayer, Color.BLUE, this.deck[2], this.deck[3]);

        this.tableCard = this.deck[4];

        this.turn = this.tableCard.getColor() == Color.RED ? Color.RED : Color.BLUE;
    }
    
    
    /**
     * Método que devolve a cor da posição do tabuleiro. Se possui uma cor, significa que é um templo. Caso contrário, é um espaço normal
     * @param position Posição do tabuleiro
     * @return O enum Color que representa a cor da posição
     */
    public Color getSpotColor(Position position){
        return this.board[position.getRow()][position.getCol()].getColor();
    }

    /**
     * Método que devolve a peça que está na posição do tabuleiro
     * @param position Posição do tabuleiro
     * @return Um objeto Piece que representa a peça na posição indicada. Se não tiver peça, devolve null
     */
    public Piece getPiece(Position position){
        return this.board[position.getRow()][position.getCol()].getPiece();
    }

    public Card getTableCard(){
        return this.tableCard;
    }
    
    public void setTableCard(Card card) {
    	this.tableCard = card;
    }

    /**
     * Método que devolve as informações sobre o jogador com as peças azuis
     * @return Um objeto Player que representa o jogador azul
     */
    public Player getBluePlayer(){
        return this.bluePlayer;
    }
    
    
    /**
     * Método que devolve as informações sobre o jogador com as peças vermelhas
     * @return Um objeto Player que representa o jogador vermelho
     */
    public Player getRedPlayer(){
        return this.redPlayer;
    }
    
    public Color getTurn() {
    	return this.turn;
    }
    
  
	
	/**
     * Método que confere se um jogador de uma determinada cor venceu o jogo. Critérios de vitória:
     * — Derrotou a peça de mestre adversária
     * — Posicionou o seu mestre na posição da base adversária
     * @param color Cor das peças do jogador que confere a condição de vitória
     * @return Um booleano true para caso esteja em condições de vencer e false caso contrário
     */
    public boolean checkVictory(Color color){
    	System.out.println("---------");
        Spot checkTemple = color == Color.RED ? board[0][2] : board[4][2];

        Piece checkMaster = color == Color.RED ? blueMaster : redMaster;

        System.out.println("Checando se " + color + " venceu.");
        if(checkMaster.isDead()) {
        	System.out.println(checkMaster.getColor() + " master morreu.");
        	return true;
        }

        if(checkTemple.getPiece() != null && getSpotColor(checkTemple.getPosition()) == color && checkTemple.getPiece().isMaster()) {
        	return true;
        }
        System.out.println("---------");
        return false;
    };
    
    public Player getCurrentPlayer() {
    	return getTurn() == Color.RED ? getRedPlayer() : getBluePlayer();
    }

    public Color oppositeColor(Color currentColor) {
    	return currentColor == Color.RED ?  Color.BLUE : Color.RED;
    }
    
    /**
     * Método que move uma peça
     * @param card A carta de movimento que será usada
     * @param cardMove A posição da carta para onde a peça irá se mover
     * @param currentPos A posição da peça que irá se mover
     * @exception IncorrectTurnOrderException Caso não seja a vez de um jogador fazer um movimento
     * @exception IllegalMovementException Caso uma peça seja movida para fora do tabuleiro ou para uma posição onde já tem uma peça da mesma cor
     * @exception InvalidCardException Caso uma carta que não está na mão do jogador seja usada
     * @exception InvalidPieceException Caso uma peça que não está no tabuleiro seja usada
     */
    public void makeMove(Card card, Position cardMove, Position currentPos) throws IncorrectTurnOrderException, IllegalMovementException, InvalidCardException, InvalidPieceException{
    	
    	Player currentPlayer = turn == Color.RED ? this.redPlayer : this.bluePlayer;
  
        Spot currentSpot = board[currentPos.getRow()][currentPos.getCol()];

        int endRow = currentPos.getRow() + cardMove.getRow();
        int endCol = currentPos.getCol() + cardMove.getCol();
        
        if(currentSpot.getPiece().getColor() != turn) {
        	throw new IncorrectTurnOrderException("Seleção inválida, vez do jogador: " + turn);
        }

        if(currentSpot.getPiece() == null) {
        	throw new InvalidPieceException("A peça não está no tabuleiro.");
        }

        if(!card.hasMove(cardMove)) {
        	throw new IllegalMovementException("Movimento não permitido pela carta.");
        }

        boolean playerHasCard = false;
        for (Card c : currentPlayer.getCards()) {
            if (c.getName().equals(card.getName())) {
            	playerHasCard = true;
                break;
            }
        }

        if(!playerHasCard) {
        	throw new InvalidCardException("A carta selecionada não está com o jogador.");
        }

        if (endRow < 0 || endRow  >= BOARD_SIZE || endCol < 0 || endCol >= BOARD_SIZE) {
        	throw new IllegalMovementException("Movimento inválido, ultrapassou os limites do tabuleiro.");
        }

        Spot endSpot = board[endRow][endCol];

        endSpot.movePiece(currentSpot.getPiece());
        
        currentSpot.removePiece();
        
        
        //troca carta com a da mesa
        getCurrentPlayer().swapCard(card, getTableCard());
        setTableCard(card);
        
        this.turn = oppositeColor(turn);
        
        return;
    };

    public void printBoard(){
    	System.out.println("   0   1   2   3   4 ");
        for(int i = 0; i < BOARD_SIZE; i++){
        	System.out.print(i + " ");
            for(int j = 0; j < BOARD_SIZE; j++){
                Spot spot = board[i][j];
                if(spot.getPiece() == null){
                    System.out.print("[  ]");
                }
                else if(spot.getPiece().isMaster() == false){
                    if(spot.getPiece().getColor() == Color.BLUE) System.out.print(ANSI_CYAN + "[BS]" + ANSI_RESET);
                    else System.out.print(ANSI_RED + "[RS]" + ANSI_RESET);
                }
                else if(spot.getPiece().isMaster() == true){
                    if(spot.getPiece().getColor() == Color.BLUE) System.out.print(ANSI_CYAN + "[BM]" + ANSI_RESET);
                    else System.out.print(ANSI_RED + "[RM]" + ANSI_RESET);
                }
            }
            System.out.println("");
        }
        
        return;
    }
    
  
	
}
