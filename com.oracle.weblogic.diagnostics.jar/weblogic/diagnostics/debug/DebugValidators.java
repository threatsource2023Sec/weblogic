package weblogic.diagnostics.debug;

public class DebugValidators {
   public static void validateDebugScope(String debugScopeName) throws InvalidDebugScopeException {
      ServerDebugService debugService = ServerDebugService.getInstance();
      debugService.testDebugScopeName(debugScopeName);
   }
}
