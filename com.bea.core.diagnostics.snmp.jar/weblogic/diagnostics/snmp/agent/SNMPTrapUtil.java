package weblogic.diagnostics.snmp.agent;

public class SNMPTrapUtil {
   private SNMPTrapSender trapSender;

   public static SNMPTrapUtil getInstance() {
      return SNMPTrapUtil.Factory.SINGLETON;
   }

   public SNMPTrapSender getSNMPTrapSender() {
      return this.trapSender;
   }

   public void setSNMPTrapSender(SNMPTrapSender ts) {
      this.trapSender = ts;
   }

   private SNMPTrapUtil() {
   }

   // $FF: synthetic method
   SNMPTrapUtil(Object x0) {
      this();
   }

   private static final class Factory {
      private static final SNMPTrapUtil SINGLETON = new SNMPTrapUtil();
   }
}
