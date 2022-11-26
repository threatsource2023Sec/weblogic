package monfox.toolkit.snmp.agent.impl;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.metadata.SnmpMibInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.util.Lock;

public class GetTask extends Task {
   private static Logger a = null;

   protected GetTask(SnmpPendingIndication var1, SnmpMib var2, SnmpAccessPolicy var3) {
      super(var1, var2, var3);
      if (a == null) {
         a = Logger.getInstance(a("RT*?~P@+W\u007fA"));
      }

   }

   public void run() {
      int var1 = 0;

      while(var1 < this._requestVBL.size()) {
         int var2 = var1 + 1;
         if (!this._pi.done(var2)) {
            SnmpVarBind var3 = this.processVB(var2, this._requestVBL.get(var1));
            this._pi.completeGetSubRequest(var3, var2);
            if (this._pi.hasError()) {
               return;
            }
         }

         ++var1;
         if (Task.a != 0) {
            break;
         }
      }

   }

   protected SnmpVarBind processVB(int var1, SnmpVarBind var2) {
      int var12 = Task.a;
      if (a.isDebugEnabled()) {
         a.debug(a("ec\u0011qIfb(P\u0004") + var1 + "," + var2 + ")");
      }

      SnmpOid var3 = var2.getOid();
      SnmpVarBind var4 = new SnmpVarBind(var3, (SnmpValue)null, true);
      if (!this._policy.checkAccess(this._pi, var1, var3)) {
         var4.setToNoSuchObject();
         return var4;
      } else {
         SnmpMibNode var5 = this._mib.get(var3);
         if (var5 == null) {
            SnmpOidInfo var20 = var3.getOidInfo();
            if (var20 instanceof SnmpMibInfo) {
               var4.setToNoSuchInstance();
               if (var12 == 0) {
                  return var4;
               }
            }

            var4.setToNoSuchObject();
            return var4;
         } else {
            if (a.isDebugEnabled()) {
               a.debug(a("{~\u001aw\u0004") + var5 + ")");
            }

            if (var5 instanceof SnmpMibTable) {
               SnmpMibTable var19 = (SnmpMibTable)var5;
               if (var19.validateInstance(var3)) {
                  label270: {
                     SnmpObjectInfo[] var21 = var19.getColumnInfo();
                     if (var21 != null) {
                        label317: {
                           int var22 = var19.columnFromInstance(var3);
                           int var24 = var21[var22 - 1].getAccess();
                           if (var24 == 0 || var24 == 8 || var24 == 1 || var24 == 16) {
                              var4.setToNoSuchObject();
                              if (var12 == 0) {
                                 break label317;
                              }
                           }

                           var4.setToNoSuchInstance();
                        }

                        if (var12 == 0) {
                           break label270;
                        }
                     }

                     var4.setToNoSuchInstance();
                  }

                  if (var12 == 0) {
                     return var4;
                  }
               }

               var4.setToNoSuchObject();
               return var4;
            } else if (!var5.checkAccess(this._pi, var1, this._policy)) {
               var4.setToNoSuchObject();
               return var4;
            } else {
               String var6 = this._pi.getContextName();
               if (!var5.isAvailableForContextName(var6)) {
                  var4.setToNoSuchObject();
                  return var4;
               } else {
                  Lock var7 = null;
                  Lock var8 = null;

                  try {
                     try {
                        if (this._pi.getAgent() != null && this._pi.getAgent().getUseTableLocking() && var5 instanceof SnmpMibLeaf) {
                           try {
                              SnmpMibTable var9 = ((SnmpMibLeaf)var5).getTable();
                              if (var9 != null) {
                                 var8 = var9.getLock();
                                 if (!var8.lock(this, this._pi.getAgent().getTableLockTimeout())) {
                                    var4.setToNoSuchObject();
                                    this._pi.setError(5, var1);
                                    SnmpVarBind var10 = var4;
                                    return var10;
                                 }
                              }
                           } catch (Exception var16) {
                              a.debug(a("pc\f}^5}\u0011qG|\u007f\u00192Xts\u0012w"), var16);
                           }
                        }

                        if (var8 == null && this._pi.getAgent() != null && this._pi.getAgent().getUseNodeLocking()) {
                           var7 = var5.getLock();
                           if (!var7.lock(this, this._pi.getAgent().getNodeLockTimeout())) {
                              var4.setToNoSuchObject();
                              this._pi.setError(5, var1);
                              SnmpVarBind var23 = var4;
                              return var23;
                           }
                        }

                        if (a.isDebugEnabled()) {
                           a.debug(a("et\ftCg|\u0017|K5\u007f\u0011vI;v\u001bf~p`\u000bw_a9\u0017|HpiC") + var1 + ")");
                        }

                        var5.getRequest(this._pi, var1, var4);
                     } catch (Exception var17) {
                        a.debug(a("{~\u001aw\u0002rt\n@Idd\u001baX5a\f}Opb\r{Br1\u001b`^zc"), var17);
                     }

                     return var4;
                  } finally {
                     if (var7 != null) {
                        var7.releaseLock(this);
                     }

                     if (var8 != null) {
                        var8.releaseLock(this);
                     }

                  }
               }
            }
         }
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
               var10003 = 21;
               break;
            case 1:
               var10003 = 17;
               break;
            case 2:
               var10003 = 126;
               break;
            case 3:
               var10003 = 18;
               break;
            default:
               var10003 = 44;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
