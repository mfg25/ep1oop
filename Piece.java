package ep1coo;
/**
 * Classe que contém informações das peças de jogo
 */
public class Piece {
    private final Color color;
    private final boolean isMaster;
    private boolean isAlive;

    /**
     * Construtor que define a cor e o tipo da peça
     * @param color Cor da peça
     * @param isMaster Se o tipo da peça é mestre ou não
     */
    public Piece(Color color, boolean isMaster) {
    	this.isMaster = isMaster;
        this.isAlive = true;
        this.color = color;
    }

    /**
     * Método que devolve a cor da peça
     * @return Enum Color com a cor da peça
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Método que devolve se é um mestre ou não
     * @return Booleano true para caso seja um mestre e false caso contrário
     */
    public boolean isMaster() {
        return this.isMaster;
    }

    /**
     * Método que mata a peça
     * @return Booleano true para caso a peça tenha sido morta e false caso contrário
     */
    public boolean kill() {
        if (this.isAlive) {
            this.isAlive = false;
            return true;
        }
        return false;
    }

    public boolean isDead() {
        return !this.isAlive;
    }
}
