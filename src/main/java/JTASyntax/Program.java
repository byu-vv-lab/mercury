package JTASyntax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Program {
    private List<Thread> threads = new ArrayList<>();

    public Program () { }

    public List<Thread> getThreads () {
        return Collections.unmodifiableList(threads);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Program program = (Program) o;

        return threads.equals(program.threads);

    }

    @Override
    public int hashCode() {
        return threads.hashCode();
    }

    public void addThread (Thread thread) {
        threads.add(thread);
    }
}
