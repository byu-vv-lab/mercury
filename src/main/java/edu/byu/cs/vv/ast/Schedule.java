package edu.byu.cs.vv.ast;

import edu.byu.cs.vv.ast.operations.Operation;

import java.util.ArrayList;

public class Schedule extends ArrayList<Operation> {

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Schedule: {\n");
        for (Operation op : this) {
            builder.append('\t' + op.toString() + '\n');
        }
        builder.append('}');
        return builder.toString();
    }

}
