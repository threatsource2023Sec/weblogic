package monfox.toolkit.snmp.agent.x.common;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.agent.x.pdu.ResponsePDU;

public class AgentXErrorException extends AgentXException {
   private int a;
   private int b;

   public AgentXErrorException(int var1, int var2) {
      boolean var3 = AgentXException.a;
      super(a("}XPC!dzG_:N\u0005\u0015H'NPG\u0010") + ResponsePDU.getErrorString(var1) + a("\u0010\u001f\\C1YG\u0015\u0010u") + var2);
      this.a = 0;
      this.b = 0;
      this.a = var1;
      this.b = var2;
      if (var3) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public int getError() {
      return this.a;
   }

   public int getIndex() {
      return this.b;
   }

   public boolean isSnmpError() {
      return this.a < 256;
   }

   public AgentXErrorException(String var1) {
      boolean var2 = AgentXException.a;
      super(var1);
      this.a = 0;
      this.b = 0;
      if (SnmpException.b) {
         AgentXException.a = !var2;
      }

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 60;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 53;
               break;
            case 3:
               var10003 = 45;
               break;
            default:
               var10003 = 85;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
