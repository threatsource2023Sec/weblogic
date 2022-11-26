package weblogic.diagnostics.query;

public interface VariableDeclarator {
   int STRING_TYPE = 0;
   int INT_TYPE = 1;
   int LONG_TYPE = 2;
   int FLOAT_TYPE = 3;
   int DOUBLE_TYPE = 4;
   int BOOLEAN_TYPE = 5;

   int getVariableType(String var1) throws UnknownVariableException;
}
