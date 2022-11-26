package monfox.toolkit.snmp.engine;

public class SnmpBulkPDU extends SnmpPDU {
   static final long serialVersionUID = -8648320885261756138L;
   protected int _nonRepeaters;
   protected int _maxRepetitions;
   private static final String h = "$Id: SnmpBulkPDU.java,v 1.1 2001/05/21 20:09:47 sking Exp $";

   public void setNonRepeaters(int var1) {
      this._nonRepeaters = var1;
   }

   public void setMaxRepetitions(int var1) {
      this._maxRepetitions = var1;
   }

   public int getNonRepeaters() {
      return this._nonRepeaters;
   }

   public int getMaxRepetitions() {
      return this._maxRepetitions;
   }

   protected void specificToString(StringBuffer var1) {
      var1.append(a("\u001e%KH|W;AGZW9W\u001b")).append(this._nonRepeaters);
      var1.append(a("\u001e&E^|W;ARGF\"KH]\u000f")).append(this._maxRepetitions);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 50;
               break;
            case 1:
               var10003 = 75;
               break;
            case 2:
               var10003 = 36;
               break;
            case 3:
               var10003 = 38;
               break;
            default:
               var10003 = 46;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
