package weblogic.diagnostics.query;

import java.util.HashMap;

public class VariableDeclaratorImpl implements VariableDeclarator {
   private HashMap declarations = new HashMap();

   public int getVariableType(String varName) throws UnknownVariableException {
      if (this.declarations.containsKey(varName)) {
         return (Integer)this.declarations.get(varName);
      } else {
         throw new UnknownVariableException("Unknown variable name " + varName);
      }
   }

   public void declareVariable(int type, String varName) {
      if (type >= 0 && type <= 5) {
         this.declarations.put(varName, type);
      } else {
         throw new IllegalArgumentException();
      }
   }
}
