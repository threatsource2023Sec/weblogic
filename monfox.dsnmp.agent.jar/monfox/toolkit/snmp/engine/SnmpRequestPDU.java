package monfox.toolkit.snmp.engine;

import monfox.toolkit.snmp.Snmp;

public final class SnmpRequestPDU extends SnmpPDU {
   static final long serialVersionUID = -1364417655155222513L;
   protected int _errorStatus = 0;
   protected int _errorIndex = 0;
   private static final String h = "$Id: SnmpRequestPDU.java,v 1.2 2002/04/30 13:58:38 samin Exp $";

   public SnmpRequestPDU() {
   }

   public SnmpRequestPDU(int var1, int var2, byte[] var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4);
      this._errorStatus = var5;
      this._errorIndex = var6;
   }

   public int getErrorStatus() {
      return this._errorStatus;
   }

   public void setErrorStatus(int var1) {
      this._errorStatus = var1;
   }

   public int getErrorIndex() {
      return this._errorIndex;
   }

   public void setErrorIndex(int var1) {
      this._errorIndex = var1;
   }

   protected void specificToString(StringBuffer var1) {
      var1.append(a("\u0002D\u001bd*z\u0016T6\u001c|\u0005O1<5")).append(Snmp.errorStatusToString(this._errorStatus));
      var1.append(a("\u0002D\u001bd*z\u0016T6\u0006f\u0000^<r")).append(this._errorIndex);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 8;
               break;
            case 1:
               var10003 = 100;
               break;
            case 2:
               var10003 = 59;
               break;
            case 3:
               var10003 = 68;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
