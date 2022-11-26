package weblogic.diagnostics.query;

public interface VariableIndexResolver {
   int getVariableIndex(String var1) throws UnknownVariableException;
}
