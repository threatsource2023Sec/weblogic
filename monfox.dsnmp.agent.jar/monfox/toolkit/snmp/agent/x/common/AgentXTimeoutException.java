package monfox.toolkit.snmp.agent.x.common;

public class AgentXTimeoutException extends AgentXException {
   public AgentXTimeoutException() {
      super(a("jqS\u0019gkl"));
   }

   public AgentXTimeoutException(String var1) {
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
               var10003 = 30;
               break;
            case 1:
               var10003 = 24;
               break;
            case 2:
               var10003 = 62;
               break;
            case 3:
               var10003 = 124;
               break;
            default:
               var10003 = 8;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
