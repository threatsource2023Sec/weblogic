package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpNotificationInfo extends SnmpMibInfo {
   static final long serialVersionUID = 1339390705491176959L;
   private SnmpObjectInfo[] _objects = null;
   private static final String _ident = "$Id: SnmpNotificationInfo.java,v 1.10 2007/06/21 00:42:40 sking Exp $";

   public SnmpNotificationInfo(SnmpObjectInfo[] var1) {
      this._objects = var1;
   }

   public SnmpObjectInfo[] getObjects() {
      return this._objects;
   }

   public void toString(StringBuffer var1) {
      var1.append(a("\u001c\u0010\u000fp]\u001b\u001c\u001amR\u001d\u0011FbU\u0013\u0012\u001e$"));
      var1.append(this.getName());
      var1.append(a("^\u0010\u0019s^\u0011\u000b\b$"));
      a(var1, this._objects);
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a("R14Mr468Xo;059\u0006R"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      a(var1, this._objects);
      var1.popIndent();
   }

   void c() {
      boolean var5 = SnmpOidInfo.a;
      int var1 = 0;
      int var2 = 0;

      int var10000;
      while(true) {
         if (var2 < this._objects.length) {
            var10000 = this._objects[var2].a();
            if (var5) {
               break;
            }

            if (var10000 != 0) {
               ++var1;
            }

            ++var2;
            if (!var5) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      if (var10000 > 0) {
         SnmpObjectInfo[] var6 = new SnmpObjectInfo[this._objects.length - var1];
         int var3 = 0;
         int var4 = 0;

         SnmpNotificationInfo var7;
         label32: {
            while(var4 < this._objects.length) {
               var7 = this;
               if (var5) {
                  break label32;
               }

               if (!this._objects[var4].a()) {
                  var6[var3] = this._objects[var4];
                  ++var3;
               }

               ++var4;
               if (var5) {
                  break;
               }
            }

            var7 = this;
         }

         var7._objects = var6;
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
               var10003 = 114;
               break;
            case 1:
               var10003 = 127;
               break;
            case 2:
               var10003 = 123;
               break;
            case 3:
               var10003 = 25;
               break;
            default:
               var10003 = 59;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
