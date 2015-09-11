package JTASyntax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Thread {
    public final String name;
    private List<Expression> commands = new ArrayList<>();

    public Thread (String name) { this.name = name; }

    public List<Expression> getExpressions () {
        return Collections.unmodifiableList(commands);
    }

    public void addExpression (Expression expression) {
        commands.add(expression);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thread thread = (Thread) o;

        if (!name.equals(thread.name)) return false;
        return commands.equals(thread.commands);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + commands.hashCode();
        return result;
    }

}
