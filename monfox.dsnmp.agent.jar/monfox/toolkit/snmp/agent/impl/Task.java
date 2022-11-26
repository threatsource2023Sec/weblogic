package monfox.toolkit.snmp.agent.impl;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.engine.SnmpPDU;

public abstract class Task implements Runnable {
   protected SnmpPendingIndication _pi;
   protected SnmpMib _mib;
   protected SnmpAccessPolicy _policy;
   protected SnmpVarBindList _requestVBL;
   public static int a;

   protected Task(SnmpPendingIndication var1, SnmpMib var2, SnmpAccessPolicy var3) {
      this._pi = var1;
      this._mib = var2;
      this._policy = var3;
      this._requestVBL = this._pi.getRequestVBL();
   }

   public abstract void run();

   public static Task getInstance(SnmpPendingIndication var0, SnmpMib var1, SnmpAccessPolicy var2) {
      int var3 = var0.getRequestType();
      switch (var3) {
         case 160:
            return new GetTask(var0, var1, var2);
         case 161:
            return new GetNextTask(var0, var1, var2);
         case 162:
         case 164:
         default:
            return new GetTask(var0, var1, var2);
         case 163:
            return new SetTask(var0, var1, var2);
         case 165:
            return new GetBulkTask(var0, var1, var2);
      }
   }

   public String toString() {
      int var2 = a;
      StringBuffer var1 = new StringBuffer();
      var1.append('{');
      var1.append(a("r\u0017J2c"));
      var1.append(SnmpPDU.typeToString(this._pi.getRequestType()));
      var1.append(',');
      var1.append(a("t\u000bK\";u\u001as3c"));
      var1.append(this._pi.getRequest().getData().getRequestId());
      var1.append('}');
      String var10000 = var1.toString();
      if (var2 != 0) {
         SnmpException.b = !SnmpException.b;
      }

      return var10000;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 6;
               break;
            case 1:
               var10003 = 110;
               break;
            case 2:
               var10003 = 58;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 94;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
