package infra;

class DatabaseException extends RuntimeException {
    DatabaseException(String message) {
        super(message);
    }
}
