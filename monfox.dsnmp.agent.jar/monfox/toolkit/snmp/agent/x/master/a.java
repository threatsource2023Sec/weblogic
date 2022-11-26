package monfox.toolkit.snmp.agent.x.master;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.x.common.AgentXCommunicationsException;
import monfox.toolkit.snmp.agent.x.common.AgentXErrorException;
import monfox.toolkit.snmp.agent.x.common.AgentXTimeoutException;

class a extends SnmpMibNode {
   protected MasterAgentX.Session _session;
   protected d _subtree;
   protected SnmpOid _maxOid;
   protected boolean _isInstance = false;
   private Logger a = Logger.getInstance(a("\u0001B\u0000\u0011C"), a("\u0004V\u000b\u0012GhI"), a("\u0004v+2g\u001dB;>g7t+\u0012|!t"));

   public a(SnmpOid var1, MasterAgentX.Session var2, d var3, boolean var4) {
      super(var1);
      this._maxOid = null;
      this._session = var2;
      this._subtree = var3;
      this._isInstance = var4;
   }

   public a(SnmpOid var1, SnmpOid var2, MasterAgentX.Session var3) throws SnmpMibException {
      super(var1);
      if (var2 != null && var2.compareTo(var1) < 0) {
         throw new SnmpMibException(a("6e/.ge~'83,bn0v6bn({$\u007fn1r=1!5w"));
      } else {
         this._maxOid = var2;
         this._session = var3;
         this._isInstance = false;
      }
   }

   public MasterAgentX.Session getSession() {
      return this._session;
   }

   public d getSubtree() {
      return this._subtree;
   }

   public SnmpOid getMaxOid() {
      return this._maxOid;
   }

   public boolean isInstance() {
      return this._isInstance;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      SnmpVarBindList var4;
      try {
         var4 = this._session.performGet(this._subtree.getContext(), this._subtree.getTimeoutSecs(), var3.getOid());
      } catch (AgentXErrorException var6) {
         this.a.debug(a(" c<3aex |R\"t (KeV+(37t?)v6e"), var6);
         if (var6.isSnmpError()) {
            var1.setError(var6.getError(), var2);
            if (!MasterAgentX.n) {
               return;
            }
         }

         var1.setError(5, var2);
         return;
      } catch (AgentXTimeoutException var7) {
         this.a.debug(a(" c<3aex |R\"t (Kee'1v*d:"), var7);
         var1.setError(5, var2);
         return;
      } catch (AgentXCommunicationsException var8) {
         this.a.debug(a(" c<3aex |R\"t (Ker!1~0\u007f'?r1x!2`"), var8);
         var1.setError(5, var2);
         return;
      }

      var3.setOid(var4.get(0).getOid());
      var3.setValue(var4.get(0).getValue());
   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      boolean var10 = MasterAgentX.n;
      if (var1.getUserObject(var2) == this) {
         var3.setOid(var3.getOid());
         var3.setValue((SnmpValue)SnmpNull.endOfMibView);
      } else {
         Object var4 = null;
         SnmpOid var5 = null;
         if (this._isInstance) {
            if (this.getOid().compareTo(var3.getOid()) <= 0) {
               var3.setValue((SnmpValue)SnmpNull.endOfMibView);
            } else {
               SnmpVarBind var14 = new SnmpVarBind(this.getOid(), var3.getValue());
               this.getRequest(var1, var2, var14);
               var3.setOid(var14.getOid());
               var3.setValue(var14.getValue());
            }
         } else {
            SnmpOid var6 = var3.getOid();
            if (!this.getOid().contains(var6) && this.getOid().compareTo(var6) > 0) {
               var6 = this.getOid();
            }

            do {
               SnmpVarBindList var7;
               try {
                  SnmpOid var8;
                  a var10000;
                  label63: {
                     var8 = this.getMaxOid();
                     if (var8 == null) {
                        var10000 = this;
                        if (var10) {
                           break label63;
                        }

                        SnmpOid var9 = this.getOid().getParent();
                        var9.append(this.getOid().getLast() + 1L);
                        var8 = var9;
                     }

                     var10000 = this;
                  }

                  var7 = var10000._session.performGetNext(this._subtree.getContext(), this._subtree.getTimeoutSecs(), var6, var8);
               } catch (AgentXErrorException var11) {
                  this.a.debug(a(" c<3aex |R\"t (KeV+(>\u000bt6(37t?)v6e"));
                  if (var11.isSnmpError()) {
                     var1.setError(var11.getError(), var2);
                     if (!var10) {
                        return;
                     }
                  }

                  var1.setError(5, var2);
                  return;
               } catch (AgentXTimeoutException var12) {
                  this.a.comms(a("\u0004v+2g\u001d1-3~(d 5p$e'3}ee'1v*d:"), var12);
                  var1.setError(5, var2);
                  return;
               } catch (AgentXCommunicationsException var13) {
                  this.a.debug(a(" c<3aex |R\"t (Ker!1~0\u007f'?r1x!2`"), var13);
                  var1.setError(5, var2);
                  return;
               }

               var4 = var7.get(0).getValue();
               var5 = var7.get(0).getOid();
               if (this._oid.compareTo(var5) <= 0) {
                  break;
               }

               var6 = var5;
            } while(!var10);

            label57: {
               if (this._maxOid == null) {
                  if (this.getOid().contains(var5)) {
                     break label57;
                  }

                  var4 = SnmpNull.endOfMibView;
                  var5 = var3.getOid();
                  if (!var10) {
                     break label57;
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

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      SnmpVarBindList var4 = new SnmpVarBindList();
      var4.add(var3);
      MasterAgentX.Transaction var6 = this._session.newTransaction();
      var1.setUserObject(var2, var6);

      try {
         this._session.performTestSet(var6, this._subtree.getContext(), this._subtree.getTimeoutSecs(), var4);
         return 0;
      } catch (AgentXErrorException var8) {
         this.a.debug(a(" c<3aex |R\"t (KeE+/ghB+(37t?)v6e"));
         if (var8.isSnmpError()) {
            var1.setError(var8.getError(), var2);
            return var8.getError();
         } else {
            var1.setError(5, var2);
            return 5;
         }
      } catch (AgentXTimeoutException var9) {
         this.a.comms(a("\u0004v+2g\u001d1-3~(d 5p$e'3}ee'1v*d:"), var9);
         var1.setError(5, var2);
         return 5;
      } catch (AgentXCommunicationsException var10) {
         this.a.debug(a(" c<3aex |R\"t (Ker!1~0\u007f'?r1x!2`"), var10);
         var1.setError(5, var2);
         return 5;
      }
   }

   private boolean a(int var1, SnmpPendingIndication var2, int var3, SnmpVarBind var4) {
      boolean var7 = MasterAgentX.n;
      MasterAgentX.Transaction var5 = (MasterAgentX.Transaction)var2.getUserObject(var3);
      if (var5 == null) {
         this.a.error(a("+~n(a$\u007f==p1x!23#~<|` en3}\u007f1") + var4);
         return false;
      } else {
         try {
            switch (var1) {
               case 9:
                  this._session.performCommitSet(var5);
                  if (!var7) {
                     break;
                  }
               case 10:
                  this._session.performUndoSet(var5);
                  if (!var7) {
                     break;
                  }
               case 11:
                  this._session.performCleanupSet(var5);
            }

            return true;
         } catch (AgentXErrorException var8) {
            this.a.debug(a(" c<3aex |R\"t (KeE+/ghB+(37t?)v6e"), var8);
            return false;
         } catch (AgentXTimeoutException var9) {
            this.a.debug(a("1x#9|0en5}eP)9}1In\bv6ec\u000fv11<9b0t=("), var9);
            return false;
         } catch (AgentXCommunicationsException var10) {
            this.a.debug(a(" c<3aex |R\"t (Ker!1~0\u007f'?r1x!2`"), var10);
            var2.setError(5, var3);
            return false;
         }
      }
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return this.a(9, var1, var2, var3);
   }

   public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return this.a(10, var1, var2, var3);
   }

   public void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      this.a(11, var1, var2, var3);
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3) {
      return true;
   }

   public String toString() {
      boolean var2 = MasterAgentX.n;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("5c!$jx"));
      var1.append('{');
      var1.append(a("*x*a"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("(p6\u0013z!,"));
      var1.append(this.getMaxOid());
      var1.append(',');
      var1.append(a("+~*9G<a+a"));
      switch (this.getNodeType()) {
         case 1:
            var1.append(a("\tT\u000f\u001a"));
            if (!var2) {
               break;
            }
         case 2:
            var1.append(a("\u0017P\u0000\u001bV"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("\u0016D\f\u0003G\u0017T\u000b"));
            if (!var2) {
               break;
            }
         default:
            var1.append(a("z.q"));
      }

      var1.append('}');
      return var1.toString();
   }

   private static String a(String var0) {
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
               var10003 = 17;
               break;
            case 2:
               var10003 = 78;
               break;
            case 3:
               var10003 = 92;
               break;
            default:
               var10003 = 19;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
