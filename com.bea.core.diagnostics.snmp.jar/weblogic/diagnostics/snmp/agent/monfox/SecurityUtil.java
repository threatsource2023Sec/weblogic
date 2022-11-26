package weblogic.diagnostics.snmp.agent.monfox;

public class SecurityUtil {
   static int convertSNMPAgentToolkitSecurityLevel(int secLevel) {
      switch (secLevel) {
         case 0:
            return 0;
         case 1:
            return 1;
         case 2:
         default:
            throw new IllegalArgumentException();
         case 3:
            return 3;
      }
   }
}
