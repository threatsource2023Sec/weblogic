package monfox.toolkit.snmp.agent.impl;

import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;

public class GetBulkTask extends GetNextTask {
   private static Logger a = null;

   protected GetBulkTask(SnmpPendingIndication var1, SnmpMib var2, SnmpAccessPolicy var3) {
      super(var1, var2, var3);
      if (a == null) {
         a = Logger.getInstance(a("\b,o=}\u001a%p=m\n8nUl\u001b"));
      }

   }

   public void run() {
      int var14 = Task.a;
      int var1 = this._pi.getNonRepeaters();
      int var2 = this._pi.getMaxRepetitions();
      int var3 = this._requestVBL.size();
      if (a.isDebugEnabled()) {
         a.debug(a("!\u0006UBZ?\u001a\u0006") + var1 + a("c\u0004Zhm*\u0019H-") + var2 + a("c\u001fYCV5\f\u0006") + var3);
      }

      int var4;
      int var10000;
      label104: {
         var4 = 0;
         if (var1 > 0) {
            while(var4 < var1) {
               int var5 = var4 + 1;
               var10000 = this._pi.done(var5);
               if (var14 != 0) {
                  break label104;
               }

               if (var10000 == 0) {
                  SnmpVarBind var6 = this._requestVBL.get(var4);
                  SnmpVarBind var7 = this.processVB(var5, var6);
                  if (this._pi.hasError()) {
                     return;
                  }

                  this._pi.completeGetBulkNonRepeater(var7, var5);
               }

               ++var4;
               if (var14 != 0) {
                  break;
               }
            }
         }

         var10000 = var2;
      }

      if (var10000 > 0) {
         Vector var15 = new Vector();
         int var16 = var4;

         label78: {
            while(var16 < this._requestVBL.size()) {
               var15.add(this._requestVBL.get(var16));
               ++var16;
               if (var14 != 0) {
                  break label78;
               }

               if (var14 != 0) {
                  break;
               }
            }

            var16 = var4 + 1;
         }

         SnmpVarBindList var17 = new SnmpVarBindList();
         int[] var8 = new int[var15.size()];
         int var9 = 0;

         while(var9 < var2 && var15.size() > 0) {
            int var10 = 0;

            label64: {
               while(var10 < var15.size()) {
                  SnmpVarBind var11 = (SnmpVarBind)var15.get(var10);
                  if (var14 != 0) {
                     break label64;
                  }

                  if (var11 != null) {
                     int var12 = var10 + 1;
                     var8[var10] = var9 + 1;
                     SnmpVarBind var13 = this.processVB(var12, var11);
                     if (this._pi.hasError()) {
                        return;
                     }

                     var17.add(var13);
                     if (SnmpNull.endOfMibView.equals(var13.getValue())) {
                        var15.set(var10, (Object)null);
                        if (var14 == 0) {
                           break;
                        }
                     }

                     var15.set(var10, var13);
                  }

                  ++var10;
                  if (var14 != 0) {
                     break;
                  }
               }

               ++var9;
            }

            if (var14 != 0) {
               break;
            }
         }

         this._pi.completeGetBulkRepeater(var17, var8);
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
               var10003 = 79;
               break;
            case 1:
               var10003 = 105;
               break;
            case 2:
               var10003 = 59;
               break;
            case 3:
               var10003 = 16;
               break;
            default:
               var10003 = 63;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
