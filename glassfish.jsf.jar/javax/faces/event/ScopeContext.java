package javax.faces.event;

import java.util.Map;

public class ScopeContext {
   private String scopeName;
   private Map scope;

   public ScopeContext(String scopeName, Map scope) {
      this.scopeName = scopeName;
      this.scope = scope;
   }

   public String getScopeName() {
      return this.scopeName;
   }

   public Map getScope() {
      return this.scope;
   }
}
