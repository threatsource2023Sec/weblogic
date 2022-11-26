package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpTableInfo extends SnmpMibInfo {
   static final long serialVersionUID = -3429514026801709544L;
   private SnmpTableEntryInfo _entry = null;
   private SnmpObjectInfo[] _columns = null;
   private SnmpObjectInfo[] _indexes = null;
   private static final String _ident = "$Id: SnmpTableInfo.java,v 1.20 2009/01/08 18:01:03 sking Exp $";

   public SnmpTableInfo(SnmpObjectInfo[] var1, SnmpObjectInfo[] var2) {
      this._columns = var1;
      this._indexes = var2;
   }

   public SnmpTableInfo(SnmpObjectInfo[] var1, SnmpObjectInfo[] var2, SnmpTableEntryInfo var3) {
      this._columns = var1;
      this._indexes = var2;
      this._entry = var3;
   }

   public SnmpObjectInfo[] getColumns() {
      return this._columns;
   }

   public SnmpObjectInfo[] getIndexes() {
      if (this._indexes != null) {
         return this._indexes;
      } else {
         return this._entry != null && this._entry.getAugments() != null ? this._entry.getAugments().getIndexes() : null;
      }
   }

   public boolean isImplied() {
      return this._entry != null ? this._entry.isImplied() : false;
   }

   public SnmpTableEntryInfo getEntry() {
      return this._entry;
   }

   public void toString(StringBuffer var1) {
      var1.append(a("w!'T\r>;+Y\u0005f}"));
      var1.append(this.getName());
      var1.append(a("/#*T\u001dn.6\u0005"));
      a(var1, this._columns);
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("#\u0014\u0004z$F`x\u0018"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      if (this._entry != null) {
         var1.append((Object)a("F\u000e\u0011j1#}e")).append((Object)this._entry.getName()).append((Object)"\n");
      }

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
               var10003 = 3;
               break;
            case 1:
               var10003 = 64;
               break;
            case 2:
               var10003 = 69;
               break;
            case 3:
               var10003 = 56;
               break;
            default:
               var10003 = 104;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class Editor {
      SnmpTableInfo _tableInfo;

      public static Editor newInstance(SnmpTableInfo var0) {
         return new Editor(var0);
      }

      Editor(SnmpTableInfo var1) {
         this._tableInfo = var1;
      }

      public void removeColumn(String var1) throws SnmpMetadataException {
         this.a(-1, var1);
      }

      public void removeColumn(int var1) throws SnmpMetadataException {
         this.a(var1, "");
      }

      private void a(int var1, String var2) throws SnmpMetadataException {
         boolean var10 = SnmpOidInfo.a;
         SnmpObjectInfo[] var3 = this._tableInfo.getColumns();
         int var4 = -1;
         SnmpObjectInfo var5 = null;

         for(int var6 = 0; var6 < var3.length; ++var6) {
            if (var3[var6].getOid().getLast() == (long)var1 || var3[var6].getName().equals(var2)) {
               var5 = var3[var6];
               var4 = var6;
               break;
            }
         }

         if (var5 == null) {
            if (var1 >= 0) {
               throw new SnmpMetadataException(a("W 9HQZ'9XKU:tU\u0004\u0011&}\u0012\u0004P!9OE[#|\u0001\u0004") + var1);
            } else {
               throw new SnmpMetadataException(a("W 9HQZ'9XKU:tU\u0004P!9OE[#|\u0001\u0004") + var2);
            }
         } else {
            SnmpObjectInfo[] var11 = this._tableInfo.getIndexes();
            int var7 = 0;

            SnmpObjectInfo[] var10000;
            while(true) {
               if (var7 < var11.length) {
                  var10000 = var11;
                  if (var10) {
                     break;
                  }

                  if (var11[var7] == var5) {
                     throw new SnmpMetadataException(a("Z.wUKMok^IV9|\u001bMW+|C\u0004Z uNIWu9") + var5.getName());
                  }

                  ++var7;
                  if (!var10) {
                     continue;
                  }
               }

               var10000 = new SnmpObjectInfo[var3.length - 1];
               break;
            }

            SnmpObjectInfo[] var12 = var10000;
            int var8 = 0;
            int var9 = 0;

            while(true) {
               if (var9 < var3.length) {
                  if (var10) {
                     break;
                  }

                  if (var9 != var4) {
                     var12[var8] = var3[var9];
                     ++var8;
                  }

                  ++var9;
                  if (!var10) {
                     continue;
                  }
               }

               this._tableInfo._columns = var12;
               break;
            }

            if (this._tableInfo.getEntry() != null) {
               this._tableInfo.getEntry().a(var12);
            }

            this._tableInfo.getMetadata().a((SnmpOidInfo)var5);
            var5.b();
         }
      }

      public void addColumn(String var1, int var2, String var3, String var4, String var5) throws SnmpMetadataException, SnmpValueException {
         boolean var16 = SnmpOidInfo.a;
         SnmpOid var6 = (SnmpOid)this._tableInfo.getOid().clone();
         var6.append(1L);
         var6.append((long)var2);

         try {
            if (this._tableInfo.getMetadata().resolveOid(var6) != null) {
               throw new SnmpMetadataException(a("Z uNIWoVr`\u0019.uIAX+`\u001b@\\)pUA]u9") + var6);
            }
         } catch (Exception var18) {
         }

         int var7 = -1;
         int var8 = -1;
         Object var9 = null;
         int var19;
         if (var3.indexOf(58) > 0) {
            try {
               SnmpTypeInfo var10 = this._tableInfo.getMetadata().getType(var3);
               if (var10 != null) {
                  var7 = var10.getSmiType();
                  var8 = var10.getType();
               }
            } catch (Exception var17) {
            }
         } else {
            var19 = SnmpValue.stringToType(var3);
            var7 = Snmp.stringToSmiType(var3);
            if (var19 == -1) {
               throw new SnmpMetadataException(a("P!oZHP+9O]I*#\u001b") + var3);
            }

            var8 = var19;
            var9 = null;
         }

         var19 = SnmpObjectInfo.stringToAccess(var4);
         SnmpObjectInfo var11 = this._tableInfo.getMetadata().addObject(this._tableInfo.getModule().getName(), var6.toNumericString(), var1, var8, var19);
         var11.setSmiType(var7);
         var11.setDescription(var5);
         SnmpObjectInfo[] var12 = this._tableInfo.getColumns();
         var11.setColumnar(true);
         SnmpObjectInfo[] var13 = new SnmpObjectInfo[var12.length + 1];
         byte var14 = 0;
         int var15 = 0;

         byte var10000;
         while(true) {
            if (var15 < var12.length) {
               var10000 = var14;
               if (var16 || var16) {
                  break;
               }

               if (var14 == 0 && var12[var15].getOid().getLast() > (long)var2) {
                  var13[var15] = var11;
                  var14 = 1;
                  if (!var16) {
                     continue;
                  }
               }

               var13[var15 + var14] = var12[var15];
               ++var15;
               if (!var16) {
                  continue;
               }
            }

            var10000 = var14;
            break;
         }

         if (var10000 == 0) {
            var13[var12.length] = var11;
         }

         this._tableInfo._columns = var13;
         if (this._tableInfo.getEntry() != null) {
            this._tableInfo.getEntry().a(var13);
         }

      }

      public void complete() {
         this._tableInfo.getMetadata().a();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 57;
                  break;
               case 1:
                  var10003 = 79;
                  break;
               case 2:
                  var10003 = 25;
                  break;
               case 3:
                  var10003 = 59;
                  break;
               default:
                  var10003 = 36;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
