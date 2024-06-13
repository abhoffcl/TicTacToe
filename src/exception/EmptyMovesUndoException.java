package exception;

public class EmptyMovesUndoException extends RuntimeException{
    public EmptyMovesUndoException(String message) {
        super(message);
    }
}
