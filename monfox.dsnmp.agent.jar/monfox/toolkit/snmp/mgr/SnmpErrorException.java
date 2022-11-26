package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;

public class SnmpErrorException extends SnmpException {
   static final long serialVersionUID = 8993397742816217242L;
   private int a = 0;
   private int b = 0;
   private SnmpVarBindList c = null;
   private String d = null;
   private static final String e = "$Id: SnmpErrorException.java,v 1.7 2003/11/12 22:42:06 sking Exp $";

   public SnmpErrorException(int var1) {
      super(a("+K.U\"=W1Jp#") + Snmp.errorStatusToString(var1) + "]");
      this.b = var1;
   }

   public SnmpErrorException(int var1, int var2, SnmpVarBindList var3) {
      super(a("+K.U\"=W1Jp#") + Snmp.errorStatusToString(var1) + a("BV7Dv\rV~") + var1 + a("TL']?") + var2 + "]");
      this.b = var1;
      this.a = var2;
      this.c = var3;
   }

   public SnmpErrorException(String var1) {
      super(var1);
   }

   public SnmpErrorException(SnmpErrorException var1, int var2) {
      super(var1.getMessage());
      this.b = var1.b;
      this.a = var2;
   }

   public int getErrorIndex() {
      return this.a;
   }

   /** @deprecated */
   public int getStatus() {
      return this.b;
   }

   public int getErrorStatus() {
      return this.b;
   }

   public String getErrorString() {
      return Snmp.errorStatusToString(this.b);
   }

   public SnmpVarBindList getVarBindList() {
      return this.c;
   }

   public SnmpVarBind getErrorVarBind() {
      try {
         return this.c.get(this.getErrorIndex() - 1);
      } catch (Exception var2) {
         return null;
      }
   }

   public String getMessage() {
      if (this.d == null) {
         SnmpVarBind var1 = this.getErrorVarBind();
         if (var1 != null) {
            this.d = a("+K.U\"=W1JpPV7Dv\rV~") + this.getErrorString() + a("TL-Ag\u0000\u0018") + this.getErrorIndex() + a("T\u00055G?") + var1 + ")";
            if (!SnmpSession.B) {
               return this.d;
            }
         }

         this.d = a("+K.U\"=W1JpPV7Dv\rV~") + this.getErrorString() + a("TL-Ag\u0000\u0018") + this.getErrorIndex() + ")";
      }

      return this.d;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 120;
               break;
            case 1:
               var10003 = 37;
               break;
            case 2:
               var10003 = 67;
               break;
            case 3:
               var10003 = 37;
               break;
            default:
               var10003 = 2;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
