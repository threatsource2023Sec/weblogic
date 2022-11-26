package monfox.toolkit.snmp.agent.impl;

import java.util.Enumeration;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.SnmpRowStatus;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.util.Lock;

public class SetTask extends Task {
   public static final int TESTSET_OP = 1;
   public static final int COMMIT_OP = 2;
   public static final int UNDO_OP = 3;
   public static final int CLEANUP_OP = 4;
   public static final int RELEASE_OP = 5;
   private static Logger a = null;
   private Vector b;
   private Vector c = new Vector();
   private SnmpMibNode[] d;
   private int e = -1;
   private static Object f = new Object();

   public SetTask(SnmpPendingIndication var1, SnmpMib var2, SnmpAccessPolicy var3) {
      super(var1, var2, var3);
      if (a == null) {
         a = Logger.getInstance(a("_e?gdIq>\u000feX"));
      }

      this.d = new SnmpMibNode[this._requestVBL.size()];
   }

   public void run() {
      try {
         label68: {
            if (this._pi.getAgent() != null && this._pi.getAgent().getUseNodeLocking()) {
               this.b();
               if (Task.a == 0) {
                  break label68;
               }
            }

            this.c();
         }

         if (this._pi.hasError()) {
            return;
         }

         this.d();
         if (this._pi.hasError()) {
            this.e();
         }

         this.f();
      } catch (Exception var5) {
         a.error(a("_E\u001fjs~R\u00048"), var5);
      } finally {
         this.a();
      }

   }

   public void process(int var1) {
      int var2 = Task.a;
      if (var1 == 1) {
         if (this._pi.getAgent() != null && this._pi.getAgent().getUseNodeLocking()) {
            this.b();
            if (var2 == 0) {
               return;
            }
         }

         this.c();
         if (var2 == 0) {
            return;
         }
      }

      if (var1 == 2) {
         this.d();
         if (var2 == 0) {
            return;
         }
      }

      if (var1 == 3) {
         this.e();
         if (var2 == 0) {
            return;
         }
      }

      if (var1 == 4) {
         this.f();
         if (var2 == 0) {
            return;
         }
      }

      if (var1 == 5) {
         this.a();
      }

   }

   private void a() {
      Enumeration var1 = this.c.elements();

      while(var1.hasMoreElements()) {
         Lock var2 = (Lock)var1.nextElement();
         if (var2 != null) {
            var2.releaseLock(this);
         }

         if (Task.a != 0) {
            break;
         }
      }

   }

   private void b() {
      synchronized(f) {
         this.c();
      }
   }

   private void c() {
      int var8 = Task.a;
      int var1 = 0;

      while(var1 < this._requestVBL.size()) {
         if (!this._pi.done(var1 + 1) && this.d[var1] == null) {
            SnmpVarBind var2 = this._requestVBL.get(var1);
            SnmpOid var3 = var2.getOid();
            if (!this._policy.checkAccess(this._pi, var1 + 1, var3)) {
               this._pi.setError(6, var1 + 1);
               if (a.isDebugEnabled()) {
                  a.debug(a("|R\u000e:W~EKb") + var3 + a("%\u001aK:Y`I\b3\u0016mC\b/E\u007f\u0000\u000f/XeE\u000f"));
               }

               return;
            }

            SnmpMibNode var4 = this._mib.get(var3, true);
            if (var4 == null) {
               this._pi.setError(11, var1 + 1);
               if (a.isDebugEnabled()) {
                  a.debug(a("|R\u000e:W~EKb") + var3 + a("%\u001aK$Y,m\"\b\u0016bO\u000f/"));
               }

               return;
            }

            Lock var5 = null;
            if (this._pi.getAgent() != null && this._pi.getAgent().getUseTableLocking()) {
               SnmpMibTable var6 = null;
               if (var4 instanceof SnmpMibLeaf) {
                  var6 = ((SnmpMibLeaf)var4).getTable();
               } else if (var4 instanceof SnmpMibTable) {
                  var6 = (SnmpMibTable)var4;
               }

               try {
                  if (var6 != null) {
                     label140: {
                        var5 = var6.getLock();
                        if (var5.lock(this, this._pi.getAgent().getTableLockTimeout())) {
                           this.c.add(var5);
                           if (var8 == 0) {
                              break label140;
                           }
                        }

                        this._pi.setError(5, var1 + 1);
                        if (a.isDebugEnabled()) {
                           a.debug(a("|R\u000e:W~EKb") + var3 + a("%\u001aK)WbN\u0004>\u0016`O\b!\u0016xA\t&S,F\u00048\u0016\u007fE\u001f"));
                        }

                        return;
                     }
                  }
               } catch (Exception var9) {
                  a.debug(a("iR\u0019%D,L\u0004)]eN\fjBmB\u0007/"), var9);
               }
            }

            if (var5 == null && this._pi.getAgent() != null && this._pi.getAgent().getUseNodeLocking()) {
               label143: {
                  Lock var10 = var4.getLock();
                  if (var10.lock(this, this._pi.getAgent().getNodeLockTimeout())) {
                     this.c.add(var10);
                     if (var8 == 0) {
                        break label143;
                     }
                  }

                  this._pi.setError(5, var1 + 1);
                  if (a.isDebugEnabled()) {
                     a.debug(a("|R\u000e:W~EKb") + var3 + a("%\u001aK)WbN\u0004>\u0016`O\b!\u0016bO\u000f/\u0016jO\u0019jEiT"));
                  }

                  return;
               }
            }

            label135: {
               if (var4 instanceof SnmpMibTable) {
                  this.a((SnmpMibTable)var4, var2, var1 + 1);
                  if (var8 == 0) {
                     break label135;
                  }
               }

               if (var4 instanceof SnmpMibLeaf) {
                  label84: {
                     SnmpMibLeaf var11 = (SnmpMibLeaf)var4;
                     if (var11.isInTable() && var11.getTable().hasRowStatus()) {
                        this.a(var11.getRow(), var2, var1 + 1);
                        if (var8 == 0) {
                           break label84;
                        }
                     }

                     this.a(var4, var2, var1 + 1);
                  }

                  if (var8 == 0) {
                     break label135;
                  }
               }

               this.a(var4, var2, var1 + 1);
            }

            if (a.isDebugEnabled()) {
               a.debug(a("!\rK$YhEQj\u0016") + var4.getOid());
               a.debug(a("!\rK/D~O\u0019p\u0016") + this._pi.getErrorStatus());
            }

            if (this._pi.hasError()) {
               return;
            }
         }

         ++var1;
         if (var8 != 0) {
            break;
         }
      }

   }

   private void a(SnmpMibNode var1, SnmpVarBind var2, int var3) {
      if (a.isDebugEnabled()) {
         a.debug(a("|R\u000e:W~E%%Ri\u0000C") + var1.getOid() + ")");
      }

      String var4 = this._pi.getContextName();
      if (!var1.isAvailableForContextName(var4)) {
         this._pi.setError(2, var3);
         if (a.isDebugEnabled()) {
            a.debug(a("|R\u000e:W~E%%Ri\u0000C") + var1.getOid() + a("%\u001aK%TfE\b>\u0016bO\u001fjWzA\u0002&WnL\u000ejPcRK)YbT\u000e2B"));
         }

      } else if (!var1.checkAccess(this._pi, var3, this._policy)) {
         this._pi.setError(17, var3);
         if (a.isDebugEnabled()) {
            a.debug(a("|R\u000e:W~E%%Ri\u0000C") + var1.getOid() + a("%\u001aK$YhEK+UoE\u00189\u0016hE\u0005#Sh"));
         }

      } else {
         int var5 = var1.prepareSetRequest(this._pi, var3, var2);
         if (var5 != 0) {
            if (!this._pi.hasError()) {
               this._pi.setError(var5, var3);
            }

            if (!a.isDebugEnabled()) {
               return;
            }

            a.debug(a("|R\u000e:W~E%%Ri\u0000C") + var1.getOid() + a("%\u001aK,WeL\u000e."));
            if (Task.a == 0) {
               return;
            }
         }

         this.d[var3 - 1] = var1;
         if (a.isDebugEnabled()) {
            a.debug(a("|R\u000e:W~E%%Ri\u0000C") + var1.getOid() + a("%\u001aK:W\u007fS\u000e."));
         }

      }
   }

   private void a(SnmpMibTable var1, SnmpVarBind var2, int var3) {
      SnmpOid var4 = var2.getOid();
      if (var1.hasRowStatus()) {
         if (a.isDebugEnabled()) {
            a.debug(a("|R\u000e:W~E%/A^O\u001cj\u001eeN\u0018>WbC\u000ew") + var4 + ")");
         }

         if (!var1.validateInstance(var4)) {
            this._pi.setError(11, var3);
            if (a.isDebugEnabled()) {
               a.debug(a("|R\u000e:W~E%/A^O\u001cj\u001e") + var4 + a("%\u001aK#XzA\u0007#R,I\u00059BmN\b/"));
            }

         } else {
            SnmpOid var10 = var1.indexFromInstance(var4);
            if (a.isDebugEnabled()) {
               a.debug(a("|R\u000e:W~E%/A^O\u001cj\u001eeN\u000f/N1") + var10 + ")");
            }

            SnmpMibTableRow var11;
            try {
               var11 = var1.initRow(var10);
            } catch (SnmpException var9) {
               this._pi.setError(11, var3);
               if (a.isDebugEnabled()) {
                  a.debug(a("|R\u000e:W~E%/A^O\u001cj\u001eeN\u000f/N1") + var10 + a("%\u001aK8Y{\u0000\u0002$_x\u0000\r+_`E\u000f"), var9);
               }

               return;
            }

            this.a(var11, var2, var3);
         }
      } else {
         byte var5 = 11;
         if (var1.validateInstance(var4)) {
            SnmpObjectInfo[] var6 = var1.getColumnInfo();
            if (var6 != null) {
               int var7 = var1.columnFromInstance(var4);
               int var8 = var6[var7 - 1].getAccess();
               if (var8 == 0 || var8 == 8 || var8 == 1 || var8 == 16) {
                  var5 = 17;
               }
            }
         }

         this._pi.setError(var5, var3);
         if (a.isDebugEnabled()) {
            a.debug(a("|R\u000e:W~E%/A^O\u001cj\u001e") + var4 + a("%\u001aK$Y,R\u0004=exA\u001f?E"));
         }

      }
   }

   private void a(SnmpMibTableRow var1, SnmpVarBind var2, int var3) {
      int var7 = Task.a;
      if (a.isDebugEnabled()) {
         a.debug(a("|R\u000e:W~E9%A,\b\u001f+T`EV") + var1.getTable().getOid() + a(" I\u0005.St\u001d") + var1.getIndex() + ")");
         a.debug(a("|R\u000e:W~E9%A,\b") + var1 + ")");
      }

      RowTask var4 = new RowTask(var1);
      int var5 = var4.add(var2, var3);
      if (var5 != 0) {
         this._pi.setError(var5, var3);
      } else {
         int var6 = var3;

         boolean var10000;
         while(true) {
            if (var6 < this._requestVBL.size()) {
               var10000 = this._pi.done(var6 + 1);
               if (var7 != 0) {
                  break;
               }

               if (!var10000) {
                  var5 = var4.add(this._requestVBL.get(var6), var6 + 1);
                  if (var5 != 0) {
                     this._pi.setError(var5, var6 + 1);
                     return;
                  }
               }

               ++var6;
               if (var7 == 0) {
                  continue;
               }
            }

            var10000 = a.isDebugEnabled();
            break;
         }

         if (var10000) {
            a.debug(a("|R\u000e:W~E9%A,\b") + var4.toString() + ")");
         }

         this.prepareRow(var4, var3);
      }
   }

   public void prepareRow(RowTask var1, int var2) {
      int var7 = Task.a;
      int var3 = var1.getNewRowStatus();
      int var4 = var1.getOldRowStatus();
      byte var5 = 0;
      int var6 = var1.getRowStatusVBIndex();
      if (a.isDebugEnabled()) {
         a.debug(a("|R\u000e:W~E9%A,\b\b?D~E\u0005>\u000b") + SnmpRowStatus.intToString(var4) + a(" A\b>_cNV") + SnmpRowStatus.intToString(var3) + ")");
      }

      label162:
      switch (var4) {
         case 0:
            switch (var3) {
               case 0:
                  a.debug(a("Ho.\u0019iBo?\u0015sTi8\u001e\f,I\u0005)YbS\u00029BiN\u001f\u001cW`U\u000e"));
                  var5 = 18;
                  if (var7 == 0) {
                     break label162;
                  }
               case 6:
                  this.a(var1);
                  if (var7 == 0) {
                     break label162;
                  }
               case 3:
               default:
                  var5 = 10;
                  if (var7 == 0) {
                     break label162;
                  }
                  break;
               case 1:
               case 2:
                  a.debug(a("Mc?\u0003`I\u0000\u00048\u0016Bo?\u0015\u007fB\u007f8\u000fdZi(\u000f\f,I\u0005)YbS\u00029BiN\u001f\u001cW`U\u000e"));
                  var5 = 12;
                  if (var7 == 0) {
                     break label162;
                  }
               case 4:
                  if (!this.a(var1)) {
                     return;
                  }

                  if (this.b(var1)) {
                     var3 = 1;
                     if (var7 == 0) {
                        break label162;
                     }
                  }

                  a.debug(a("Or.\u000bbI\u007f*\u0004rSg$b\u0007%\u001aK#XoO\u00059_\u007fT\u000e$BZA\u0007?S"));
                  var5 = 12;
                  if (var7 != 0) {
                     return;
                  }
                  break label162;
               case 5:
                  if (this.a(var1)) {
                     if (this.b(var1)) {
                        var3 = 2;
                        if (var7 == 0) {
                           break label162;
                        }
                     }

                     var3 = 3;
                     if (var7 == 0) {
                        break label162;
                     }
                  }

                  return;
            }
         case 1:
            switch (var3) {
               case 1:
                  if (var1.isSettableWhenActive()) {
                     if (!this.a(var1)) {
                        return;
                     }
                     break label162;
                  } else {
                     if (var1.size() <= 1) {
                        break label162;
                     }

                     a.debug(a("Mc?\u0003`I\u0000C8B2\u0011Bp\u0016eN\b%X\u007fI\u0018>SbT=+ZyE"));
                     var5 = 12;
                     if (var7 == 0) {
                        break label162;
                     }
                  }
               case 2:
                  if (!this.a(var1)) {
                     return;
                  }
                  break label162;
               case 4:
               case 5:
                  a.debug(a("Or.\u000bbI\u007f*\u0004rSg$eaMi?b\u0007%\u001aK#XoO\u00059_\u007fT\u000e$BZA\u0007?S"));
                  var5 = 12;
                  if (var7 == 0) {
                     break label162;
                  }
               case 0:
                  if (var1.isSettableWhenActive()) {
                     if (!this.a(var1)) {
                        return;
                     }
                     break label162;
                  } else {
                     a.debug(a("Ho.\u0019iBo?\u0015sTi8\u001e\f,I\u0005)YbS\u00029BiN\u001f\u001cW`U\u000e"));
                     var5 = 12;
                     var6 = var2;
                     if (var7 == 0) {
                        break label162;
                     }
                  }
               case 6:
                  this.a(var1);
                  if (var7 == 0) {
                     break label162;
                  }
               case 3:
               default:
                  var5 = 10;
                  if (var7 == 0) {
                     break label162;
                  }
            }
         case 2:
            switch (var3) {
               case 1:
                  if (!this.a(var1)) {
                     return;
                  }

                  if (!this.b(var1)) {
                     a.debug(a("Mc?\u0003`I\u000f*\tbEv.p\u0016eN\b%X\u007fI\u0018>SbT=+ZyE"));
                     var5 = 12;
                     if (var7 != 0) {
                        return;
                     }
                  }
                  break label162;
               case 2:
                  if (!this.a(var1)) {
                     return;
                  }
                  break label162;
               case 4:
               case 5:
                  a.debug(a("Or.\u000bbI\u007f*\u0004rSg$eaMi?b\u0004%\u001aK#XoO\u00059_\u007fT\u000e$BZA\u0007?S"));
                  var5 = 12;
                  if (var7 == 0) {
                     break label162;
                  }
               case 0:
                  if (!this.a(var1)) {
                     return;
                  }
                  break label162;
               case 6:
                  this.a(var1);
                  if (var7 == 0) {
                     break label162;
                  }
               case 3:
               default:
                  var5 = 10;
                  if (var7 == 0) {
                     break label162;
                  }
            }
         case 3:
            switch (var3) {
               case 1:
                  if (!this.a(var1)) {
                     return;
                  }

                  if (!this.b(var1)) {
                     var5 = 12;
                     if (var7 != 0) {
                        return;
                     }
                  }
                  break label162;
               case 2:
                  if (!this.a(var1)) {
                     return;
                  }

                  if (!this.b(var1)) {
                     var5 = 12;
                     if (var7 != 0) {
                        return;
                     }
                  }
                  break label162;
               case 4:
               case 5:
                  var5 = 12;
                  if (var7 == 0) {
                     break label162;
                  }
               case 0:
                  if (this.a(var1)) {
                     if (this.b(var1)) {
                        var3 = 2;
                        if (var7 == 0) {
                           break label162;
                        }
                     }

                     var3 = 3;
                     if (var7 == 0) {
                        break label162;
                     }
                  }

                  return;
               case 6:
                  this.a(var1);
                  if (var7 == 0) {
                     break label162;
                  }
               case 3:
               default:
                  var5 = 10;
                  if (var7 == 0) {
                     break label162;
                  }
            }
         default:
            switch (var3) {
               case 0:
               case 1:
               case 2:
               case 4:
               case 5:
               case 6:
                  var5 = 12;
                  if (var7 == 0) {
                     break;
                  }
               case 3:
               default:
                  var5 = 10;
            }
      }

      if (var3 == 0) {
         var3 = var4;
      }

      if (var5 != 0 && !this._pi.hasError()) {
         this._pi.setError(var5, var6);
      }

      if (!this._pi.hasError()) {
         var1.setNewRowStatus(var3);
         if (this.b == null) {
            this.b = new Vector();
         }

         this.b.add(var1);
         if (a.isDebugEnabled()) {
            a.debug(a("|R\u000e:W~E9%A,\b") + SnmpRowStatus.intToString(var1.getOldRowStatus()) + a("!\u001e") + SnmpRowStatus.intToString(var1.getNewRowStatus()) + ")");
         }

      }
   }

   private boolean a(RowTask var1) {
      int var5 = Task.a;
      int var2 = var1.row.getRowStatusColumn() - 1;
      int var3 = 0;

      while(var3 < var1.vbs.length) {
         if (var1.vbs[var3] != null) {
            label21: {
               if (var3 == var2) {
                  this.d[var1.vbi[var3] - 1] = var1.row.getRowStatusLeaf();
                  if (var5 == 0) {
                     break label21;
                  }
               }

               SnmpMibLeaf var4 = var1.row.getLeaf(var3 + 1);
               this.a((SnmpMibNode)var4, var1.vbs[var3], var1.vbi[var3]);
               if (this._pi.hasError()) {
                  return false;
               }
            }
         }

         ++var3;
         if (var5 != 0) {
            break;
         }
      }

      return true;
   }

   private boolean b(RowTask var1) {
      int var6 = Task.a;
      int var2 = var1.row.getRowStatusColumn() - 1;
      int var3 = 0;

      int var10000;
      while(true) {
         if (var3 < var1.row.size()) {
            var10000 = var3;
            if (var6 != 0) {
               break;
            }

            if (var3 != var2) {
               SnmpMibLeaf var4 = var1.row.getLeaf(var3 + 1);
               int var5 = var4.getAccess();
               if ((var5 == 1 || var5 == 5) && !var4.hasValue() && var1.vbs[var3] == null) {
                  a.debug(a("~E\n.OJO\u0019\u0019S~V\u0002)SW") + var4.getOid() + a("Q\u001aK") + false);
                  return false;
               }
            }

            ++var3;
            if (var6 == 0) {
               continue;
            }
         }

         var3 = var1.row.getTable().readyForService(this._pi, var1.row, var1.vbs);
         var10000 = a.isDebugEnabled();
         break;
      }

      if (var10000 != 0) {
         a.debug(a("~E\n.OJO\u0019\u0019S~V\u0002)S6\u0000") + var3);
      }

      return (boolean)var3;
   }

   private void d() {
      int var4 = Task.a;
      int var1 = 0;

      Object var10000;
      while(true) {
         if (var1 < this.d.length) {
            var10000 = this.d[var1];
            if (var4 != 0) {
               break;
            }

            if (var10000 != null) {
               label119: {
                  if (this.d[var1] instanceof SnmpRowStatus) {
                     this._pi.completeSetSubRequest(var1 + 1);
                     if (var4 == 0) {
                        break label119;
                     }
                  }

                  boolean var2 = this.d[var1].commitSetRequest(this._pi, var1 + 1, this._requestVBL.get(var1));
                  if (!var2) {
                     if (!this._pi.hasError()) {
                        this._pi.setError(14, var1 + 1);
                     }

                     if (a.isDebugEnabled()) {
                        a.debug(a("oO\u0006'_x\u0000C") + this.d[var1].getOid() + a("%\u001aK,WeL\u000e."));
                     }

                     return;
                  }

                  this._pi.completeSetSubRequest(var1 + 1);
                  if (a.isDebugEnabled()) {
                     a.debug(a("oO\u0006'_x\u0000C") + this.d[var1].getOid() + ")");
                  }
               }
            }

            ++var1;
            if (var4 == 0) {
               continue;
            }

            if (this.b == null) {
               return;
            }

            var1 = 0;
         } else {
            if (this.b == null) {
               return;
            }

            var1 = 0;
         }

         if (var1 >= this.b.size()) {
            return;
         }

         var10000 = this.b.elementAt(var1);
         break;
      }

      while(true) {
         RowTask var7 = (RowTask)var10000;
         if (var7.getOldRowStatus() == 0 && var7.getNewRowStatus() != 6) {
            try {
               var7.row.getTable().addRow(var7.row);
            } catch (SnmpMibException var5) {
               this._pi.setError(14, var7.getRowStatusVBIndex());
               this.e = var1;
               if (a.isDebugEnabled()) {
                  a.debug(a("oO\u0006'_xr\u0004=\u0016$") + var7.row + a("%\u001aK,WeL\u000e."), var5);
               }

               return;
            }
         }

         if (var7.getOldRowStatus() != var7.getNewRowStatus()) {
            try {
               var7.row.getRowStatusLeaf().setValue(var7.getNewRowStatus());
            } catch (SnmpException var6) {
               this._pi.setError(14, var7.getRowStatusVBIndex());
               this.e = var1;
               if (a.isDebugEnabled()) {
                  a.debug(a("oO\u0006'_xr\u0004=\u0016$") + var7.row + a("%\u001aK,WeL\u000e."), var6);
               }

               return;
            }
         }

         if (var7.getNewRowStatus() == 6) {
            var7.row.getTable().removeRow(var7.row.getIndex());
         }

         if (a.isDebugEnabled()) {
            a.debug(a("oO\u0006'_xr\u0004=\u0016$") + var7.row + ")");
         }

         ++var1;
         if (var4 != 0 || var1 >= this.b.size()) {
            return;
         }

         var10000 = this.b.elementAt(var1);
      }
   }

   private void e() {
      int var1;
      int var5;
      label108: {
         var5 = Task.a;
         if (this.e != -1) {
            var1 = this._requestVBL.size();
            if (var5 == 0) {
               break label108;
            }
         }

         var1 = this._pi.getErrorIndex() - 1;
      }

      int var2 = 0;

      Object var10000;
      while(true) {
         if (var2 < var1) {
            var10000 = this.d[var2];
            if (var5 != 0) {
               break;
            }

            if (var10000 != null && !(this.d[var2] instanceof SnmpRowStatus)) {
               boolean var3 = this.d[var2].undoSetRequest(this._pi, var2 + 1, this._requestVBL.get(var2));
               if (!var3) {
                  if (!this._pi.hasError()) {
                     this._pi.setError(15, var2 + 1);
                  }

                  if (a.isDebugEnabled()) {
                     a.debug(a("yN\u000f%\u0016$") + this.d[var2].getOid() + a("%\u001aK,WeL\u000e."));
                  }
               }

               if (a.isDebugEnabled()) {
                  a.debug(a("yN\u000f%\u0016$") + this.d[var2].getOid() + ")");
               }
            }

            ++var2;
            if (var5 == 0) {
               continue;
            }
         }

         if (this.b == null || this.e == -1) {
            return;
         }

         var2 = 0;
         if (var2 >= this.e) {
            return;
         }

         var10000 = this.b.elementAt(var2);
         break;
      }

      while(true) {
         RowTask var8 = (RowTask)var10000;
         if (var8.getNewRowStatus() == 6 && var8.getOldRowStatus() != 0) {
            try {
               var8.row.getTable().addRow(var8.row);
            } catch (SnmpMibException var7) {
               this._pi.setError(15, var8.getRowStatusVBIndex());
               if (a.isDebugEnabled()) {
                  a.debug(a("yN\u000f%dcWKb") + var8.row + a("%\u001aK,WeL\u000e."), var7);
               }
            }
         }

         if (var8.getOldRowStatus() != var8.getNewRowStatus()) {
            try {
               var8.row.getRowStatusLeaf().setValue(var8.getOldRowStatus());
            } catch (SnmpException var6) {
               this._pi.setError(15, var8.getRowStatusVBIndex());
               if (a.isDebugEnabled()) {
                  a.debug(a("yN\u000f%dcWKb") + var8.row + a("%\u001aK,WeL\u000e."), var6);
               }
            }
         }

         if (var8.getOldRowStatus() == 0) {
            var8.row.getTable().removeRow(var8.row.getIndex());
         }

         if (a.isDebugEnabled()) {
            a.debug(a("yN\u000f%dcWKb") + var8.row + ")");
         }

         ++var2;
         if (var5 != 0 || var2 >= this.e) {
            return;
         }

         var10000 = this.b.elementAt(var2);
      }
   }

   private void f() {
      int var3 = Task.a;
      int var1 = 0;

      while(true) {
         RowTask var2;
         if (var1 < this.d.length) {
            SnmpMibNode var10000 = this.d[var1];
            if (var3 == 0) {
               if (var10000 != null) {
                  this.d[var1].cleanupSetRequest(this._pi, var1 + 1, this._requestVBL.get(var1));
                  if (a.isDebugEnabled()) {
                     a.debug(a("oL\u000e+XyPKb") + this.d[var1].getOid() + ")");
                  }
               }

               ++var1;
               if (var3 == 0) {
                  continue;
               }

               if (this.b == null) {
                  return;
               }

               var1 = 0;
            } else {
               var2 = (RowTask)var10000;
               if (!var2.row.isInTable()) {
                  var2.row.destroy();
               }

               ++var1;
               if (var3 != 0) {
                  return;
               }
            }
         } else {
            if (this.b == null) {
               return;
            }

            var1 = 0;
         }

         do {
            if (var1 >= this.b.size()) {
               return;
            }

            var2 = (RowTask)this.b.elementAt(var1);
            if (!var2.row.isInTable()) {
               var2.row.destroy();
            }

            ++var1;
         } while(var3 == 0);

         return;
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
               var10003 = 12;
               break;
            case 1:
               var10003 = 32;
               break;
            case 2:
               var10003 = 107;
               break;
            case 3:
               var10003 = 74;
               break;
            default:
               var10003 = 54;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class RowTask {
      public SnmpMibTableRow row;
      public SnmpVarBind[] vbs;
      public int[] vbi;
      private int a;
      private int b;
      private SnmpMibTable c;
      private SnmpOid d;

      public RowTask(SnmpMibTableRow var2) {
         int var3 = Task.a;
         super();
         this.row = var2;
         this.vbs = new SnmpVarBind[var2.size()];
         this.vbi = new int[var2.size()];
         this.a = var2.getRowStatus();
         this.b = 0;
         this.c = var2.getTable();
         this.d = var2.getIndex();
         if (SnmpException.b) {
            ++var3;
            Task.a = var3;
         }

      }

      public int add(SnmpVarBind var1, int var2) {
         SnmpOid var3 = var1.getOid();
         if (this.c.validateInstance(var3) && this.d.equals(this.c.indexFromInstance(var3))) {
            int var4 = this.c.columnFromInstance(var3);
            return this.add(var1, var2, var4);
         } else {
            return 0;
         }
      }

      public int add(SnmpVarBind var1, int var2, int var3) {
         if (var3 == this.c.getRowStatusColumn()) {
            SnmpValue var4 = var1.getValue();
            int var5 = this.row.getRowStatusLeaf().valueOk(var4);
            if (var5 != 0) {
               return var5;
            }

            this.b = var4.intValue();
         }

         this.vbs[var3 - 1] = var1;
         this.vbi[var3 - 1] = var2;
         return 0;
      }

      public boolean hasNewRowStatus() {
         return this.b != 0;
      }

      public int getNewRowStatus() {
         return this.b;
      }

      public void setNewRowStatus(int var1) {
         this.b = var1;
      }

      public int getOldRowStatus() {
         return this.a;
      }

      public int getRowStatusVBIndex() {
         return this.vbi[this.c.getRowStatusColumn() - 1];
      }

      public int size() {
         int var1 = 0;
         int var2 = 0;

         while(var2 < this.vbs.length) {
            if (this.vbs[var2] != null) {
               ++var1;
            }

            ++var2;
            if (Task.a != 0) {
               break;
            }
         }

         return var1;
      }

      public boolean isSettableWhenActive() {
         int var1 = 0;

         while(var1 < this.vbs.length) {
            if (this.vbs[var1] != null) {
               int var2 = var1 + 1;
               if (var2 != this.c.getRowStatusColumn() && !this.c.isSettableWhenActive(var2)) {
                  return false;
               }
            }

            ++var1;
            if (Task.a != 0) {
               break;
            }
         }

         return true;
      }

      public String toString() {
         int var3 = Task.a;
         StringBuffer var1 = new StringBuffer();
         var1.append(a("K/>?\u0004"));
         int var2 = 0;

         int var10000;
         while(true) {
            if (var2 < this.vbs.length) {
               var10000 = var2;
               if (var3 != 0) {
                  break;
               }

               if (var2 > 0) {
                  var1.append(',');
               }

               label41: {
                  if (this.vbs[var2] != null) {
                     var1.append(this.vbs[var2].getValue());
                     if (var3 == 0) {
                        break label41;
                     }
                  }

                  var1.append(a("S8!n"));
               }

               ++var2;
               if (var3 == 0) {
                  continue;
               }
            }

            var1.append(a("@a;`\u0016\u00006"));
            var10000 = 0;
            break;
         }

         var2 = var10000;

         while(true) {
            if (var2 < this.vbi.length) {
               if (var3 != 0) {
                  break;
               }

               if (var2 > 0) {
                  var1.append(',');
               }

               var1.append(this.vbi[var2]);
               ++var2;
               if (var3 == 0) {
                  continue;
               }
            }

            var1.append("}");
            break;
         }

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
                  var10003 = 61;
                  break;
               case 1:
                  var10003 = 77;
                  break;
               case 2:
                  var10003 = 77;
                  break;
               case 3:
                  var10003 = 2;
                  break;
               default:
                  var10003 = 127;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
