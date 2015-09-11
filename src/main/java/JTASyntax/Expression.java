package JTASyntax;

public class Expression {
    public final boolean is_send;
    public final String sender;
    public final String receiver;
    public final String value;

    private Expression (boolean is_send, String sender, String receiver, String value) {
        this.is_send = is_send;
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
    }

    public static Expression new_send (String sender, String receiver, String value) {
        return new Expression (true, sender, receiver, value);
    }

    public static Expression new_recv (String sender, String receiver) {
        return new Expression (false, sender, receiver, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expression that = (Expression) o;

        if (is_send != that.is_send) return false;
        if (is_send && (!value.equals(that.value))) return false;
        if (!sender.equals(that.sender)) return false;
        return receiver.equals(that.receiver);
    }

    @Override
    public int hashCode() {
        int result = (is_send ? 1 : 0);
        result = 31 * result + sender.hashCode();
        result = 31 * result + receiver.hashCode();
        return result;
    }
}
