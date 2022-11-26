package monfox.toolkit.snmp.agent.proxy;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

class b extends SnmpMibNode {
   protected SnmpSession _session;
   protected SnmpPeer _peer;
   protected SnmpAgent _agent;
   protected SnmpProxyForwarder _forwarder;
   protected String _target;
   protected String _subtreeName;
   protected SnmpOid _maxOid;
   protected boolean _isInstance = false;
   private Logger a = Logger.getInstance(a("kR#~\u0019"), a("nF(}\u001d\u0002Q?|\u0011v"), a("|o\u0000C\u0019]n\u0015J\u001aZc\u0019A,JO\u0002W,"));

   public b(SnmpOid var1, String var2, SnmpSession var3, boolean var4, SnmpAgent var5, SnmpProxyForwarder var6, String var7) {
      super(var1);
      this._agent = var5;
      this._maxOid = null;
      this._target = var2;
      this._session = var3;
      this._forwarder = var6;
      this._isInstance = var4;
      this._subtreeName = var7;
   }

   public b(SnmpOid var1, SnmpOid var2, String var3, SnmpSession var4, SnmpAgent var5, SnmpProxyForwarder var6, String var7) throws SnmpMibException {
      super(var1);
      if (var2 != null && var2.compareTo(var1) < 0) {
         throw new SnmpMibException(a("\\u\fA=\u000fn\u0004WiFrM_,\\rMG!NoM^(W!\u0002Z-"));
      } else {
         this._agent = var5;
         this._maxOid = var2;
         this._target = var3;
         this._session = var4;
         this._isInstance = false;
         this._forwarder = var6;
         this._subtreeName = var7;
      }
   }

   public String getName() {
      return this._subtreeName;
   }

   public SnmpSession getSession() {
      return this._session;
   }

   public SnmpPeer getPeer() {
      if (this._peer != null) {
         return this._peer;
      } else {
         this._peer = this._agent.getTarget().getPeer(this._target);
         return this._peer;
      }
   }

   public SnmpOid getMaxOid() {
      return this._maxOid;
   }

   public boolean isInstance() {
      return this._isInstance;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("Hd\u0019a,^t\b@=t") + this.getName() + a("r;M") + var3);
      }

      SnmpVarBindList var4;
      try {
         SnmpPeer var5 = this.getPeer();
         if (var5 == null) {
            this.a.error(a("AnM\u0014=Ns\nV=\b!\tV/Fo\bWiFoM`'Bq9R;Hd\u0019r-Ks9R+CdMU&];M") + this._target);
            var1.setError(5, var2);
            return;
         }

         if (var5.isPassThrough()) {
            var5 = var5.createFromPassThrough(var1.getRequest());
         }

         SnmpVarBindList var6 = new SnmpVarBindList();
         var6.add(var3.getOid());
         var4 = this._session.performGet(var5, var6);
      } catch (SnmpErrorException var7) {
         this.a.debug(a("Js\u001f\\;\u000fh\u0003\u0013\u001aZc,T,AuMt,[!\u001fV8Zd\u001eG"), var7);
         var1.setError(var7.getErrorStatus(), var2);
         return;
      } catch (SnmpTimeoutException var8) {
         this.a.debug(a("Js\u001f\\;\u000fh\u0003\u0013\u001aZc,T,AuMG Bd\u0002F="), var8);
         var1.setError(5, var2);
         return;
      } catch (SnmpException var9) {
         this.a.debug(a("Js\u001f\\;\u000fh\u0003\u0013\u001aZc,T,AuMP&Bl\u0018] L`\u0019Z&Ar"), var9);
         var1.setError(5, var2);
         return;
      }

      var3.setOid(var4.get(0).getOid());
      var3.setValue(var4.get(0).getValue());
   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      int var11 = SnmpProxyForwarder.g;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("Hd\u0019},Wu?V8Zd\u001eG\u0012") + this.getName() + a("r;M") + var3);
      }

      if (var1.getUserObject(var2) == this) {
         this.a.debug(a("An\tViNm\u001fV(KxMC;@b\b@:JeA\u0013 Ho\u0002A,\u0001/C"));
         var3.setOid(var3.getOid());
         var3.setValue((SnmpValue)SnmpNull.endOfMibView);
      } else {
         Object var4 = null;
         SnmpOid var5 = null;
         if (this._isInstance) {
            if (this.getOid().compareTo(var3.getOid()) <= 0) {
               var3.setValue((SnmpValue)SnmpNull.endOfMibView);
            } else {
               SnmpVarBind var15 = new SnmpVarBind(this.getOid(), var3.getValue());
               this.getRequest(var1, var2, var15);
               var3.setOid(var15.getOid());
               var3.setValue(var15.getValue());
            }
         } else {
            SnmpOid var6 = var3.getOid();
            if (!this.getOid().contains(var6) && this.getOid().compareTo(var6) > 0) {
               var6 = this.getOid();
            }

            SnmpPeer var7 = this.getPeer();
            if (var7 == null) {
               this.a.error(a("AnM\u0014=Ns\nV=\b!\tV/Fo\bWiFoM`'Bq9R;Hd\u0019r-Ks9R+CdMU&];M") + this._target);
               var1.setError(5, var2);
            } else {
               if (var7.isPassThrough()) {
                  var7 = var7.createFromPassThrough(var1.getRequest());
               }

               SnmpVarBindList var8 = new SnmpVarBindList();
               var8.add(var6);

               do {
                  SnmpVarBindList var9;
                  try {
                     var9 = this._session.performGetNext(var7, var8);
                  } catch (SnmpErrorException var12) {
                     this.a.debug(a("Js\u001f\\;\u000fh\u0003\u0013\u001aZc,T,AuMt,[,#V1[!\u001fV8Zd\u001eG"), var12);
                     if (this._forwarder.isSubtreeProxyBypassModeEnabled()) {
                        var1.setUserObject(var2, this);
                        var3.setOid(var3.getOid());
                        var3.setValue((SnmpValue)SnmpNull.endOfMibView);
                        return;
                     }

                     var1.setError(var12.getErrorStatus(), var2);
                     return;
                  } catch (SnmpTimeoutException var13) {
                     this.a.comms(a("|t\u000fr.Jo\u0019\u0013*@l\u0000F'Fb\fG @oMG Bd\u0002F="), var13);
                     if (this._forwarder.isSubtreeProxyBypassModeEnabled()) {
                        var1.setUserObject(var2, this);
                        var3.setOid(var3.getOid());
                        var3.setValue((SnmpValue)SnmpNull.endOfMibView);
                        return;
                     }

                     var1.setError(5, var2);
                     return;
                  } catch (SnmpException var14) {
                     this.a.debug(a("Js\u001f\\;\u000fh\u0003\u0013\u001aZc,T,AuMP&Bl\u0018] L`\u0019Z&Ar"), var14);
                     if (this._forwarder.isSubtreeProxyBypassModeEnabled()) {
                        var1.setUserObject(var2, this);
                        var3.setOid(var3.getOid());
                        var3.setValue((SnmpValue)SnmpNull.endOfMibView);
                        return;
                     }

                     var1.setError(5, var2);
                     return;
                  }

                  var4 = var9.get(0).getValue();
                  var5 = var9.get(0).getOid();
               } while(this._oid.compareTo(var5) > 0 && var11 == 0);

               label75: {
                  if (this._maxOid == null) {
                     if (this.getOid().contains(var5)) {
                        break label75;
                     }

                     var4 = SnmpNull.endOfMibView;
                     var5 = var3.getOid();
                     if (var11 == 0) {
                        break label75;
                     }
                  }

                  if (this._maxOid.compareTo(var5) < 0) {
                     var4 = SnmpNull.endOfMibView;
                     var5 = var3.getOid();
                  }
               }

               var3.setOid(var5);
               var3.setValue((SnmpValue)var4);
            }
         }
      }
   }

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("_s\bC(]d>V=}d\u001cF,\\u6") + this.getName() + a("r;M") + var3);
      }

      SnmpPeer var4 = this.getPeer();
      if (var4 == null) {
         this.a.error(a("AnM\u0014=Ns\nV=\b!\tV/Fo\bWiFoM`'Bq9R;Hd\u0019r-Ks9R+CdMU&];M") + this._target);
         return 5;
      } else {
         if (var4.isPassThrough()) {
            var4 = var4.createFromPassThrough(var1.getRequest());
         }

         SnmpVarBindList var5 = new SnmpVarBindList();
         var5.add(var3);

         try {
            this._session.performSet(var4, var5);
            return 0;
         } catch (SnmpTimeoutException var8) {
            return 5;
         } catch (SnmpErrorException var9) {
            return var9.getErrorStatus();
         } catch (SnmpException var10) {
            return 5;
         }
      }
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return true;
   }

   public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return true;
   }

   public void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3) {
      return true;
   }

   public String toString() {
      int var2 = SnmpProxyForwarder.g;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("_s\u0002K0\u0012"));
      var1.append('{');
      var1.append(a("@h\t\u000e"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("B`\u0015| K<"));
      var1.append(this.getMaxOid());
      var1.append(',');
      var1.append(a("An\tV\u001dVq\b\u000e"));
      switch (this.getNodeType()) {
         case 1:
            var1.append(a("cD,u"));
            if (var2 == 0) {
               break;
            }
         case 2:
            var1.append(a("}@#t\f"));
            if (var2 == 0) {
               break;
            }
         case 3:
            var1.append(a("|T/l\u001d}D("));
            if (var2 == 0) {
               break;
            }
         default:
            var1.append(a("\u0010>R"));
      }

      var1.append('}');
      String var10000 = var1.toString();
      if (SnmpException.b) {
         ++var2;
         SnmpProxyForwarder.g = var2;
      }

      return var10000;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 47;
               break;
            case 1:
               var10003 = 1;
               break;
            case 2:
               var10003 = 109;
               break;
            case 3:
               var10003 = 51;
               break;
            default:
               var10003 = 73;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
