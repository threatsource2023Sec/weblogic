package monfox.toolkit.snmp.agent.impl;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;

public class GetNextTask extends GetTask {
   private static Logger a = null;

   protected GetNextTask(SnmpPendingIndication var1, SnmpMib var2, SnmpAccessPolicy var3) {
      super(var1, var2, var3);
      if (a == null) {
         a = Logger.getInstance(a("sK\u0001/bqV\u0001/~q_\u0000G\u007f`"));
      }

   }

   protected SnmpVarBind processVB(int param1, SnmpVarBind param2) {
      // $FF: Couldn't be decompiled
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 52;
               break;
            case 1:
               var10003 = 14;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 2;
               break;
            default:
               var10003 = 44;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
