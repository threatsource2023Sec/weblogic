package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpObjectInfo extends SnmpMibInfo {
   static final long serialVersionUID = -5000633912054453468L;
   private int _smiType = -1;
   private int _type = -1;
   private boolean _implied = false;
   private int _access = -1;
   private boolean _isColumnar = false;
   private SnmpTypeInfo _typeInfo = null;
   private SnmpTableInfo _tableInfo = null;
   private String _defVal = null;
   private static final String _ident = "$Id: SnmpObjectInfo.java,v 1.26 2011/07/01 19:35:17 sking Exp $";

   public SnmpObjectInfo(String var1, String var2) throws SnmpValueException {
      this._type = SnmpValue.stringToType(var1);
      if (this._type == -1) {
         throw new SnmpValueException(a("\u001dD^)\u0010\u001fD\u00153\u0006\u0018O\u000fg") + var1);
      } else {
         this._access = stringToAccess(var2);
         if (this._access == -1) {
            throw new SnmpValueException(a("\u001dD^)\u0010\u001fD\u0015&\u001c\u000bOF4EH\r") + var2 + a("O\n\u001d1\u001e\u0004CQg\t\tF@\"\fR\nG(S\u001fE\u00195\bDXVk\u0011\t\u0006[.S\u0006E\u001c"));
         }
      }
   }

   public SnmpObjectInfo(String var1) throws SnmpValueException {
      this._type = SnmpValue.stringToType(var1);
      if (this._type == -1) {
         throw new SnmpValueException(a("\u001dD^)\u0010\u001fD\u00153\u0006\u0018O\u000fg") + var1);
      } else {
         this._access = 0;
      }
   }

   public SnmpObjectInfo(int var1) {
      this._type = var1;
      this._access = 0;
   }

   public SnmpObjectInfo(int var1, int var2) {
      this._type = var1;
      this._access = var2;
   }

   public int getType() {
      return this._type;
   }

   public SnmpTypeInfo getTypeInfo() {
      return this._typeInfo;
   }

   public void setTypeInfo(SnmpTypeInfo var1) {
      this._typeInfo = var1;
   }

   /** @deprecated */
   public boolean isImplied() {
      return this._implied;
   }

   /** @deprecated */
   public void setImplied(boolean var1) {
      this._implied = var1;
   }

   public String getTypeString() {
      return SnmpValue.typeToString(this._type);
   }

   public String getTypeShortString() {
      return SnmpValue.typeToShortString(this._type);
   }

   public int getAccess() {
      return this._access;
   }

   public void setAccess(int var1) {
      this._access = var1;
   }

   public String getAccessString() {
      return accessToString(this._access);
   }

   public boolean isRead() {
      return (this._access & 1) != 0;
   }

   public boolean isWrite() {
      return (this._access & 2) != 0;
   }

   public boolean isCreate() {
      return (this._access & 5) == 5 || (this._access & 3) == 3;
   }

   public boolean isScalar() {
      return !this._isColumnar;
   }

   public boolean isColumnar() {
      return this._isColumnar;
   }

   public void setColumnar(boolean var1) {
      this._isColumnar = var1;
   }

   public SnmpTableInfo getTableInfo() {
      return this._tableInfo;
   }

   public void setTableInfo(SnmpTableInfo var1) {
      this._tableInfo = var1;
   }

   public int getSmiType() {
      return this._smiType;
   }

   public void setSmiType(int var1) {
      this._smiType = var1;
   }

   public void setSmiType(String var1) {
      this._smiType = Snmp.stringToSmiType(var1);
   }

   public String getSmiTypeString() {
      return Snmp.smiTypeToString(this._smiType);
   }

   public String getSmiTypeShortString() {
      return Snmp.smiTypeToShortString(this._smiType);
   }

   public void setDefVal(String var1) {
      this._defVal = var1;
   }

   public String getDefVal() {
      return this._defVal;
   }

   public SnmpObjectInfo(SnmpObjectInfo var1) {
      this._smiType = var1._smiType;
      this._type = var1._type;
      this._implied = var1._implied;
      this._access = var1._access;
      this._isColumnar = var1._isColumnar;
      this._typeInfo = var1._typeInfo;
      this._tableInfo = var1._tableInfo;
      this._defVal = var1._defVal;
   }

   public void toString(StringBuffer var1) {
      var1.append(a("\u0007H_\"\u001c\u001c\u0017N)\u001e\u0005O\b"));
      var1.append(this.getName());
      var1.append(a("D^L7\u001aU"));
      var1.append(this.getTypeShortString());
      var1.append(a("DKV$\u001a\u001bY\b"));
      var1.append(this.getAccessString());
      var1.append(a("DIZ+\n\u0005DT5B"));
      var1.append(this.isColumnar());
      if (this._tableInfo != null) {
         var1.append(a("D^T%\u0013\r\u0017") + this._tableInfo.getName());
      }

      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("Hew\r:+~\u0015z_"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      var1.append((Object)a("\u001cSE\"_H\n\u0015z")).append((Object)this.getTypeString()).append((Object)"\n");
      var1.append((Object)a("\tIV\"\f\u001b\n\u0015z")).append((Object)this.getAccessString()).append((Object)"\n");
      var1.append((Object)a("\u000bEY2\u0012\u0006KGz")).append(this.isColumnar());
      SnmpTypeInfo var2 = this.getTypeInfo();
      if (var2 != null) {
         label12: {
            if (var2.getModule() == null) {
               var2.toString(var1);
               if (!SnmpOidInfo.a) {
                  break label12;
               }
            }

            var1.append((Object)a("\u001cSE\"\r\rL\u0015z"));
            var1.append((Object)var2.getModule().getName());
            var1.append((Object)":").append((Object)var2.getName()).append((Object)"\n");
         }
      }

      var1.popIndent();
   }

   public static String accessToString(int var0) {
      switch (var0) {
         case 0:
            return a("\u0006EAj\u001e\u000bIP4\f\u0001HY\"");
         case 1:
            return a("\u001aOT#R\u0007DY>");
         case 2:
            return a("\u001fX\\3\u001aEE[+\u0006");
         case 3:
            return a("\u001aOT#R\u001fX\\3\u001a");
         case 4:
         case 6:
         case 7:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return "?";
         case 5:
            return a("\u001aOT#R\u000bXP&\u000b\r");
         case 8:
            return a("\tIV\"\f\u001bCW+\u001aELZ5R\u0006EA.\u0019\u0011");
         case 16:
            return a("\u0006EAj\u0016\u0005ZY\"\u0012\rDA\"\u001b");
      }
   }

   public static String accessToShortString(int var0) {
      switch (var0) {
         case 0:
            return a("\u0006K");
         case 1:
            return a("\u001aE");
         case 2:
            return a("\u001fE");
         case 3:
            return a("\u001a]");
         case 4:
         case 6:
         case 7:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            return "?";
         case 5:
            return a("\u001aI");
         case 8:
            return a("\u0006E");
         case 16:
            return a("\u0006C");
      }
   }

   public static int stringToAccess(String var0) {
      String var1 = var0.toLowerCase();
      if (a("\u001aE").equals(var1)) {
         return 1;
      } else if (a("\u001a]").equals(var1)) {
         return 3;
      } else if (a("\u001fE").equals(var1)) {
         return 2;
      } else if (a("\u001aI").equals(var1)) {
         return 5;
      } else if (a("\u0006K").equals(var1)) {
         return 0;
      } else if (a("\u0006E").equals(var1)) {
         return 8;
      } else {
         return a("\u0006C").equals(var1) ? 16 : -1;
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
               var10003 = 104;
               break;
            case 1:
               var10003 = 42;
               break;
            case 2:
               var10003 = 53;
               break;
            case 3:
               var10003 = 71;
               break;
            default:
               var10003 = 127;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
