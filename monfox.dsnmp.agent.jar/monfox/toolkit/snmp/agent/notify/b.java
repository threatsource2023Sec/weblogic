package monfox.toolkit.snmp.agent.notify;

import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;

class b {
   private static Logger a = Logger.getInstance(b("\u0016)y\ni*3}\u001c^\u0003.x\u000eB7"));

   static byte[] a(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0, b("k}4"), false);
      int var2 = var1.countTokens();
      if (var2 == 0) {
         return new byte[0];
      } else {
         int var3 = (var2 - 1) / 8 + 1;
         byte[] var4 = new byte[var3];
         int var5 = 1;

         while(var1.hasMoreTokens()) {
            String var6 = var1.nextToken();
            int var7 = (var5 - 1) / 8;
            int var8 = 7 - (var5 - 1) % 8;
            if (!var6.equals("0") && !var6.equals("*")) {
               var4[var7] = (byte)((var4[var7] | 1 << var8) & 255);
            }

            ++var5;
            if (SnmpNotifier.v) {
               break;
            }
         }

         return var4;
      }
   }

   static boolean a(SnmpOid var0, SnmpVarBindList var1, byte[] var2) {
      boolean var6 = SnmpNotifier.v;
      if (var1 == null) {
         return false;
      } else {
         int var3 = var1.size();
         int var4 = 0;

         boolean var10000;
         while(true) {
            if (var4 < var3) {
               SnmpVarBind var5 = var1.get(var4);
               var10000 = a(var0, var5.getOid(), var2);
               if (var6) {
                  break;
               }

               if (var10000) {
                  return true;
               }

               ++var4;
               if (!var6) {
                  continue;
               }
            }

            var10000 = false;
            break;
         }

         return var10000;
      }
   }

   static boolean a(SnmpOid var0, SnmpOid var1, byte[] var2) {
      boolean var7 = SnmpNotifier.v;
      if (var0.getLength() > var1.getLength()) {
         return false;
      } else {
         int var3 = var0.getLength();
         int var4 = 0;

         boolean var10000;
         while(true) {
            if (var4 < var3) {
               boolean var5 = a(var4 + 1, var2);
               var10000 = var5;
               if (var7) {
                  break;
               }

               if (var5) {
                  try {
                     if (var0.get(var4) != var1.get(var4)) {
                        if (a.isDebugEnabled()) {
                           a.debug(b("hj4\u0014He*u\u000eD-}4RT0%`\bB z") + var0 + b("i(}\u001e\u001a") + var1 + ")");
                        }

                        return false;
                     }
                  } catch (SnmpValueException var8) {
                     if (a.isDebugEnabled()) {
                        a.debug(b("hj4\u0017F1$|@\u0007m4a\u0018S7\"qG") + var0 + b("i(}\u001e\u001a") + var1 + ")");
                     }

                     return false;
                  }
               }

               ++var4;
               if (!var7) {
                  continue;
               }
            }

            var10000 = a.isDebugEnabled();
            break;
         }

         if (var10000) {
            a.debug(b("hj4\u0015N!gy\u001bS&/.Z\u000f62v\u000eU \")") + var0 + b("i(}\u001e\u001a") + var1 + ")");
         }

         return true;
      }
   }

   static boolean a(int var0, byte[] var1) {
      if (var1 == null) {
         return true;
      } else {
         int var2 = (var0 - 1) / 8;
         if (var2 >= var1.length) {
            return true;
         } else {
            int var3 = 7 - (var0 - 1) % 8;
            return (var1[var2] & 1 << var3) != 0;
         }
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 69;
               break;
            case 1:
               var10003 = 71;
               break;
            case 2:
               var10003 = 20;
               break;
            case 3:
               var10003 = 122;
               break;
            default:
               var10003 = 39;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
