package weblogic.diagnostics.query;

public interface Query {
   boolean executeQuery(VariableResolver var1) throws QueryExecutionException;

   QueryExecutionTrace getLastExecutionTrace();
}
