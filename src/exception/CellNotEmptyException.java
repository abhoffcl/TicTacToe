package exception;

public class CellNotEmptyException extends RuntimeException {
    public CellNotEmptyException(String message) {
        super(message);
    }
}
