package weblogic.spring.monitoring.actions;

public class ScopeArgActionState extends BaseActionState {
   private String scopeName;

   public String getScopeName() {
      return this.scopeName;
   }

   public void setScopeName(String scopeName) {
      this.scopeName = scopeName;
   }
}
