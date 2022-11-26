package monfox.toolkit.snmp.metadata;

public class SnmpSupportedModule {
   static final long serialVersionUID = -7199150535983852419L;
   private SnmpNotificationGroupInfo[] _notificationGroups;
   private SnmpObjectGroupInfo[] _objectGroups;
   private SnmpObjectVariation[] _objectVariations;
   private String _moduleName;

   public SnmpSupportedModule(SnmpObjectGroupInfo[] var1, SnmpNotificationGroupInfo[] var2, SnmpObjectVariation[] var3) {
      this._objectGroups = var1;
      this._notificationGroups = var2;
      this._objectVariations = var3;
   }

   public void setName(String var1) {
      this._moduleName = var1;
   }

   public String getName() {
      return this._moduleName;
   }

   public SnmpObjectGroupInfo[] getIncludedObjectGroups() {
      return this._objectGroups;
   }

   public SnmpNotificationGroupInfo[] getIncludedNotificationGroups() {
      return this._notificationGroups;
   }

   public SnmpObjectVariation[] getObjectVariations() {
      return this._objectVariations;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      boolean var3 = SnmpOidInfo.a;
      var1.append(a("\u0007K+x~\u0006J>l\\\u001bZ.dtIE{a\u007f\u0017R.lt\u0007\u001ef(j"));
      int var2 = 0;

      label67:
      while(true) {
         int var10000;
         int var10001;
         if (var2 < this._notificationGroups.length) {
            var1.append(this._notificationGroups[var2].getName());
            var10000 = var2;
            var10001 = this._notificationGroups.length - 1;
            if (!var3) {
               if (var2 < var10001) {
                  var1.append(",");
               }

               ++var2;
               if (!var3) {
                  continue;
               }

               var2 = 0;
               var10000 = var2;
               var10001 = this._objectGroups.length;
            }
         } else {
            var2 = 0;
            var10000 = var2;
            var10001 = this._objectGroups.length;
         }

         while(true) {
            label73: {
               if (var10000 < var10001) {
                  var1.append(this._objectGroups[var2].getName());
                  var10000 = var2;
                  var10001 = this._objectGroups.length - 1;
                  if (var3) {
                     break label73;
                  }

                  if (var2 < var10001) {
                     var1.append(",");
                  }

                  ++var2;
                  if (!var3) {
                     var10000 = var2;
                     var10001 = this._objectGroups.length;
                     continue;
                  }
               }

               var1.append("}");
               var1.append(a("X\u001e-ic\u001d_/a~\u001aM{51\u000f\u001e"));
               var2 = 0;
               var10000 = var2;
               var10001 = this._objectVariations.length;
            }

            while(true) {
               if (var10000 >= var10001) {
                  break label67;
               }

               var1.append(this._objectVariations[var2]);
               if (var3) {
                  return;
               }

               if (var2 < this._objectVariations.length - 1) {
                  var1.append(",");
               }

               ++var2;
               if (var3) {
                  break label67;
               }

               var10000 = var2;
               var10001 = this._objectVariations.length;
            }
         }
      }

      var1.append("}");
      var1.append("}");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 116;
               break;
            case 1:
               var10003 = 62;
               break;
            case 2:
               var10003 = 91;
               break;
            case 3:
               var10003 = 8;
               break;
            default:
               var10003 = 17;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
