package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpTableEntryInfo extends SnmpMibInfo {
   static final long serialVersionUID = -3429514026801709544L;
   private SnmpTableEntryInfo _augments = null;
   private boolean _implied = false;
   private SnmpObjectInfo[] _columns = null;
   private SnmpObjectInfo[] _indexes = null;
   private static final String _ident = "$Id: SnmpTableEntryInfo.java,v 1.6 2007/05/18 22:53:13 sking Exp $";

   public SnmpTableEntryInfo(SnmpObjectInfo[] var1, SnmpObjectInfo[] var2) {
      this._columns = var1;
      this._indexes = var2;
   }

   public SnmpTableEntryInfo(SnmpObjectInfo[] var1, SnmpTableEntryInfo var2) {
      this._columns = var1;
      this._augments = var2;
   }

   public SnmpObjectInfo[] getColumns() {
      return this._columns;
   }

   public SnmpObjectInfo[] getIndexes() {
      return this._indexes;
   }

   public SnmpTableEntryInfo getAugments() {
      return this._augments;
   }

   public void setAugments(SnmpTableEntryInfo var1) {
      this._augments = var1;
   }

   public boolean isImplied() {
      return this._augments != null ? this._augments.isImplied() : this._implied;
   }

   public void setImplied(boolean var1) {
      this._implied = var1;
   }

   void a(SnmpObjectInfo[] var1) {
      this._columns = var1;
   }

   public void toString(StringBuffer var1) {
      var1.append(a("U9xuJd6nkV\u001c#txBDe"));
      var1.append(this.getName());
      var1.append(a("\r;uuZL6i$"));
      a(var1, this._columns);
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("\u0001\f[[cdu_W{s\u0001:$\u000f"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      a(var1, this._columns);
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
               var10003 = 33;
               break;
            case 1:
               var10003 = 88;
               break;
            case 2:
               var10003 = 26;
               break;
            case 3:
               var10003 = 25;
               break;
            default:
               var10003 = 47;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
