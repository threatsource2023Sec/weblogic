package weblogic.diagnostics.snmp.agent;

public class SNMPTrapException extends Exception {
   public SNMPTrapException(String msg) {
      super(msg);
   }

   public SNMPTrapException(Exception e) {
      super(e);
   }
}
