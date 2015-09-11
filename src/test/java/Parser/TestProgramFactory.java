package Parser;

import JTASyntax.Expression;
import JTASyntax.Program;
import JTASyntax.Thread;

public class TestProgramFactory {

    public static Program dlg1 () {
        Program program = new Program();
        Thread thread1 = new Thread ("0");
        thread1.addExpression(Expression.new_recv("-1", "0"));
        thread1.addExpression(Expression.new_recv("2", "0"));
        program.addThread(thread1);

        Thread thread2 = new Thread ("1");
        thread2.addExpression(Expression.new_send("1", "0", "1"));
        program.addThread(thread2);

        Thread thread3 = new Thread ("2");
        thread3.addExpression(Expression.new_send("2", "0", "1"));
        program.addThread(thread3);

        return program;
    }


}
