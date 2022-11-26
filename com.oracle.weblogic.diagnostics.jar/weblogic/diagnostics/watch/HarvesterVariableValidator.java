package weblogic.diagnostics.watch;

import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableIndexResolver;

public final class HarvesterVariableValidator implements VariableIndexResolver {
   String watchName = null;
   private int indexCounter = 0;

   HarvesterVariableValidator(String watchName) {
      this.watchName = watchName;
   }

   public int getVariableIndex(String varName) throws UnknownVariableException {
      HarvesterVariablesParser.parse(varName, this.watchName);
      return this.indexCounter++;
   }
}
