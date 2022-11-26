package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;

class e extends SnmpMibNode {
   private SnmpMibNode a;

   public e(SnmpMibNode var1) {
      this.a = var1;
   }

   public SnmpMibNode getStartNode() {
      return this.a;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return 17;
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return false;
   }

   public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return false;
   }

   public void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3) {
      return false;
   }

   public SnmpOid getOid() {
      return this.a.getMaxOid();
   }

   public SnmpOid getMaxOid() {
      return this.a.getMaxOid();
   }

   public int getNodeType() {
      return 22;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("fAkr\"QNaZ!>"));
      var1.append('{');
      var1.append(a("lFk\u0000"));
      var1.append(this.a.getMaxOid());
      var1.append('}');
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 3;
               break;
            case 1:
               var10003 = 47;
               break;
            case 2:
               var10003 = 15;
               break;
            case 3:
               var10003 = 61;
               break;
            default:
               var10003 = 68;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
