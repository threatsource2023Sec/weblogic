package weblogic.diagnostics.debug;

public interface DebugScopeUtil {
   String DEBUG_SCOPE_PROPERTY = "weblogic.debug.DebugScopes";

   String[] getChildDebugScopes(String var1) throws InvalidDebugScopeException;

   String[] getChildDebugAttributes(String var1) throws InvalidDebugScopeException;
}
