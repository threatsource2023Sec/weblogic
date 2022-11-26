package monfox.toolkit.snmp.agent.vacm;

import java.util.Iterator;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;

public class VacmAccessPolicy extends SnmpAccessPolicy {
   private String a;
   private Vector b;

   public VacmAccessPolicy(String var1, Vector var2) {
      this.a = var1;
      this.b = var2;
   }

   /** @deprecated */
   public boolean checkAccess(SnmpPendingIndication var1) {
      return true;
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpOid var3) {
      boolean var10 = Vacm.j;
      Vector var4 = new Vector();
      Iterator var5 = this.b.iterator();

      int var10000;
      SnmpMibTableRow var6;
      SnmpOid var7;
      while(true) {
         if (var5.hasNext()) {
            var6 = (SnmpMibTableRow)var5.next();
            var7 = (SnmpOid)var6.getLeaf(2).getValue();
            byte[] var8 = var6.getLeaf(3).getValue().getByteArray();
            var10000 = this.a(var3, var7, var8);
            if (var10) {
               break;
            }

            if (var10000 != 0) {
               var4.add(var6);
            }

            if (!var10) {
               continue;
            }
         }

         var10000 = var4.size();
         break;
      }

      if (var10000 == 0) {
         return false;
      } else if (var4.size() == 1) {
         var6 = (SnmpMibTableRow)var4.elementAt(0);
         return this.a(var6);
      } else {
         var5 = var4.iterator();
         var6 = (SnmpMibTableRow)var5.next();
         var7 = (SnmpOid)var6.getLeaf(2).getValue();

         while(true) {
            if (var5.hasNext()) {
               SnmpMibTableRow var11 = (SnmpMibTableRow)var5.next();
               SnmpOid var9 = (SnmpOid)var11.getLeaf(2).getValue();
               var10000 = var9.getLength();
               if (var10) {
                  break;
               }

               if (var10000 > var7.getLength()) {
                  var6 = var11;
               }

               if (!var10) {
                  continue;
               }
            }

            var10000 = this.a(var6);
            break;
         }

         return (boolean)var10000;
      }
   }

   private boolean a(SnmpMibTableRow var1) {
      int var2 = var1.getLeaf(4).getValue().intValue();
      return var2 == 1;
   }

   private boolean a(SnmpOid var1, SnmpOid var2, byte[] var3) {
      boolean var10 = Vacm.j;
      if (var1.getLength() < var2.getLength()) {
         return false;
      } else {
         int var4 = 0;

         boolean var10000;
         while(true) {
            if (var4 < var2.getLength()) {
               var10000 = this.a(var3, var4);
               if (var10) {
                  break;
               }

               if (!var10000 || var10) {
                  long var5 = 0L;
                  long var7 = 0L;

                  try {
                     var5 = var1.get(var4);
                     var7 = var2.get(var4);
                     if (var5 != var7) {
                        return false;
                     }
                  } catch (SnmpValueException var11) {
                     return false;
                  }
               }

               ++var4;
               if (!var10) {
                  continue;
               }
            }

            var10000 = true;
            break;
         }

         return var10000;
      }
   }

   private boolean a(byte[] var1, int var2) {
      if (var1 != null && var1.length != 0) {
         int var3 = var2 / 8;
         int var4 = var2 % 8;
         if (var3 >= var1.length) {
            return false;
         } else {
            return (var1[var3] & 1 << 7 - var4) != 1;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return a("J3>TK\u007f18JyL=1Pieh&Ocy%\u0013Xgyo") + this.a + "}";
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 28;
               break;
            case 1:
               var10003 = 82;
               break;
            case 2:
               var10003 = 93;
               break;
            case 3:
               var10003 = 57;
               break;
            default:
               var10003 = 10;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
