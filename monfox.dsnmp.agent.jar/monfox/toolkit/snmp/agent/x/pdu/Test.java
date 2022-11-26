package monfox.toolkit.snmp.agent.x.pdu;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpVarBind;

public class Test {
   public static void main(String[] var0) throws Exception {
      int var5 = AgentXPDU.i;
      SnmpFramework.loadMibs(a("-j\u0018\u0001\u0004L\t\u0018\u00180"));
      SnmpVarBind var1 = new SnmpVarBind(a("\r]&\u0015\u0017\rG'\u007fB"), a("3]u\u0015\u0017\rG'"));
      System.out.println(a("1m\u0011kR") + var1.getOid().toNumericString());
      EncBuffer var2 = new EncBuffer();
      var2.addHeader(1, 1, 1);
      var2.addVarBind(var1);
      System.out.println(a(";j\u0016kR") + var2);
      DecBuffer var3 = new DecBuffer(var2.getBytes());
      var3.decodeHeader();
      System.out.println(a("\bA'\"\u001b\u0011Jh") + var3.getVersion() + "\n");
      System.out.println(a("\n]%4O") + var3.getType() + "\n");
      System.out.println(a("\u0018H46\u0001C") + var3.getFlags() + "\n");
      SnmpVarBind var4 = var3.nextVarBind();
      System.out.println(a("\bFh") + var4);
      if (var5 != 0) {
         SnmpException.b = !SnmpException.b;
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
               var10003 = 126;
               break;
            case 1:
               var10003 = 36;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 81;
               break;
            default:
               var10003 = 114;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
