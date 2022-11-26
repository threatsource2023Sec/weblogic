package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpMessage;

public class SnmpReportException extends SnmpException {
   private SnmpVarBindList a = null;
   private SnmpMessage b = null;
   private static final String c = "$Id: SnmpReportException.java,v 1.5 2003/11/12 22:42:06 sking Exp $";

   public SnmpReportException(SnmpMessage var1) {
      super(a("z7\\&LL)^$j"));
      this.b = var1;
      if (this.b != null && this.b.getData() != null && this.b.getData().getVarBindList() != null) {
         this.a = this.b.getData().getVarBindList();
      }

   }

   public SnmpMessage getReport() {
      return this.b;
   }

   public SnmpVarBindList getVarBinds() {
      return this.a;
   }

   public String getMessage() {
      return a("{<A9l]y\u0019") + this.a + ")";
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 41;
               break;
            case 1:
               var10003 = 89;
               break;
            case 2:
               var10003 = 49;
               break;
            case 3:
               var10003 = 86;
               break;
            default:
               var10003 = 30;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
