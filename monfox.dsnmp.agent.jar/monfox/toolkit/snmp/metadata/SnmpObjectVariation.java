package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpObjectVariation {
   static final long serialVersionUID = -5000633912054453468L;
   private int _access = -1;
   private SnmpTypeInfo _syntax = null;
   private SnmpTypeInfo _writeSyntax = null;
   private SnmpObjectInfo _objectInfo = null;
   private String _defVal = null;
   private static final String _ident = "$Id: SnmpObjectVariation.java,v 1.1 2008/06/03 15:10:23 sking Exp $";

   public SnmpObjectVariation(SnmpObjectInfo var1) {
      this._objectInfo = var1;
   }

   public void setSyntax(SnmpTypeInfo var1) {
      this._syntax = var1;
   }

   public SnmpTypeInfo getSyntax() {
      return this._syntax;
   }

   public void setWriteSyntax(SnmpTypeInfo var1) {
      this._writeSyntax = var1;
   }

   public SnmpTypeInfo getWriteSyntax() {
      return this._writeSyntax;
   }

   public int getAccess() {
      return this._access;
   }

   public void setAccess(int var1) {
      this._access = var1;
   }

   public String getAccessString() {
      return SnmpObjectInfo.accessToString(this._access);
   }

   public void setDefVal(String var1) {
      this._defVal = var1;
   }

   public String getDefVal() {
      return this._defVal;
   }

   public SnmpObjectInfo getObjectInfo() {
      return this._objectInfo;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this._objectInfo.getName()).append((Object)a("\n\n\u0006\u001d\u0014i\u0011djq"));
      var1.append((Object)this._objectInfo.getOid().toNumericString());
      var1.pushIndent();
      var1.popIndent();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 42;
               break;
            case 1:
               var10003 = 69;
               break;
            case 2:
               var10003 = 68;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 81;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
