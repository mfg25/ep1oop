package ep1coo;
/**
 * Classe contendo ações e informações sobre cada espaço (quadrado) no tabuleiro
 */
public class Spot {
    private final Position position;
    private Piece piece;
    private Color color;

    /**
     * Construtor para espaços com peça e com cor
     * @param piece Peça que inicia nesse espaço do tabuleiro
     * @param pos Posição do espaço no tabuleiro
     * @param color Cor do espaço no tabuleiro (Templo)
     */

    
    public Spot(Piece piece, Position pos, Color color) {
        this(piece, pos);
        this.color = color;
    }

    /**
     * Construtor para espaços com peça e sem cor
     * @param piece Peça que inicia nesse espaço do tabuleiro
     * @param pos Posição do espaço no tabuleiro
     */
    public Spot(Piece piece, Position pos) {
        this.piece = piece;
        this.position = pos;
        this.color = Color.NONE;
    }

    /**
     * Construtor para espaços sem peça e sem cor
     * @param pos Posição do espaço no tabuleiro
     */
    public Spot(Position pos) {
        this.position = pos;
        this.color = Color.NONE;
        this.piece = null;
    }

    /**
     * Método que cria um Tabuleiro com os espaços e peças iniciais
     * @param size Tamanho do tabuleiro
     */
    public static Spot[][] createBoard(int size) {
        Spot[][] board = new Spot[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    if (j == 2) {
                        board[i][j] = new Spot(new Piece(Color.BLUE, true), new Position(i, j), Color.BLUE);
                    } else {
                        board[i][j] = new Spot(new Piece(Color.BLUE, false), new Position(i, j), Color.NONE);
                    }
                } else if (i == 4) {
                    if (j == 2) {
                        board[i][j] = new Spot(new Piece(Color.RED, true), new Position(i, j), Color.RED);
                    } else {
                        board[i][j] = new Spot(new Piece(Color.RED, false), new Position(i, j), Color.NONE);
                    }
                } else {
                    board[i][j] = new Spot(new Position(i, j));
                }
            }
        }
        return board;
    }

    /**
     * Método que devolve a posição (coordenadas) do espaço
     * @return Objeto Position contendo a posição (coordenadas) do espaço
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Método que devolve a peça contida neste espaço
     * @return Objeto Piece caso tenha uma peça ou null caso o espaço esteja vazio
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Método que define a peça contida neste espaço
     * @param piece A peça para ocupar este espaço
     */
    public void setPiece(Piece piece) {
        this.occupySpot(piece);
        this.piece = piece;
    }

    /**
     * Método que remove a peça contida neste espaço
     */
    public void removePiece() {
        releaseSpot();
    }

    /**
     * Método que define a peça contida neste espaço
     * @param newPiece A peça para ocupar este espaço
     * @return Objeto Piece caso tenha uma peça ou null caso o espaço esteja vazio
     */
    public Piece movePiece(Piece newPiece) {       
        Piece oldPiece = this.piece;
        occupySpot(newPiece);
        return oldPiece;
    }

    /**
     * Método que devolve a cor do espaço
     * @return Enum Color com a cor do espaço. Caso o espaço não tenha cor, o valor do enum será NONE
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Método que ocupa o espaço atual com a peça passada
     * @param piece A peça para ocupar este espaço
     * @exception IllegalMovementException Caso o espaço já esteja ocupado por uma peça da mesma cor
     */
    protected void occupySpot(Piece piece) throws IllegalMovementException {
        if(this.piece != null && this.piece.getColor() == piece.getColor()) {
            throw new IllegalMovementException("Essa posição está ocupada por uma peça da mesma cor.");
        }

        System.out.println("Peça da cor " + piece.getColor() + " movida para [" + this.position.getCol() + ", " + this.position.getRow() + "].");
        if(this.piece != null) {
            System.out.println("Peça da cor " + this.piece.getColor() + " foi eliminada.");
            this.piece.kill();
        }
        
        this.piece = piece;
    }

    /**
     * Método que "libera" o espaço atual, ou seja, deixa-o vazio
     */
    protected void releaseSpot() {
    	this.color = Color.NONE;
        this.piece = null;
    }
}
