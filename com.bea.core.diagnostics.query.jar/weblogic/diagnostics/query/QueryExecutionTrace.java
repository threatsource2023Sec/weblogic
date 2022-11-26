package weblogic.diagnostics.query;

import java.util.HashMap;
import java.util.Map;

public final class QueryExecutionTrace {
   private Map evaluatedVariables = new HashMap();

   void addEvaluatedVariable(String varName, Object val) {
      this.evaluatedVariables.put(varName, val);
   }

   public Map getEvaluatedVariables() {
      return this.evaluatedVariables;
   }
}
