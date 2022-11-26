package weblogic.security.debug;

public interface SecurityLogger {
   String AUTHN = "DebugSecurityAtn";
   String AUTHZ = "DebugSecurityAtz";

   boolean isDebugEnabled();

   void debug(String var1);

   void debug(String var1, Exception var2);
}
