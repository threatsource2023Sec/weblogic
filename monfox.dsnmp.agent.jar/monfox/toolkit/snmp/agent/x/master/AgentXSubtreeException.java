package monfox.toolkit.snmp.agent.x.master;

import monfox.toolkit.snmp.agent.x.pdu.ResponsePDU;

public class AgentXSubtreeException extends Exception {
   private int a = 0;

   public AgentXSubtreeException(int var1) {
      super(a("4B\u0014^8-v\u0004R8\u0007@\u0014u>\u0007J\u0003\nl\u0010W\u0003_>H") + ResponsePDU.getErrorString(var1));
      this.a = var1;
   }

   public int getError() {
      return this.a;
   }

   public AgentXSubtreeException(String var1) {
      super(var1);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 117;
               break;
            case 1:
               var10003 = 37;
               break;
            case 2:
               var10003 = 113;
               break;
            case 3:
               var10003 = 48;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
