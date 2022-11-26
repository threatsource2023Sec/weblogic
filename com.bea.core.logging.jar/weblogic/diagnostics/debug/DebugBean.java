package weblogic.diagnostics.debug;

public interface DebugBean {
   String getName();

   void setName(String var1);

   DebugScopeBean[] getDebugScopes();

   DebugScopeBean createDebugScope(String var1);

   void destroyDebugScope(DebugScopeBean var1);

   DebugScopeBean lookupDebugScope(String var1);
}
