package monfox.toolkit.snmp.agent.x.master;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.x.common.AgentXErrorException;

class b {
   private Map a = new Hashtable();
   private Map b = new Hashtable();
   private Logger c = Logger.getInstance(a("szoY6"), a("vndZ2\u001aq"), a("vNDz\u0012o`Op\u0003O{Ds\u000fD]Sm"));

   public boolean isValidIndexType(int var1) {
      switch (var1) {
         case 2:
         case 4:
         case 5:
         case 6:
         case 64:
         case 65:
         case 66:
         case 67:
            return true;
         case 68:
         case 70:
         case 255:
         default:
            return false;
      }
   }

   monfox.toolkit.snmp.agent.x.master.b.b a(String var1, SnmpOid var2) {
      if (var1 == null) {
         var1 = "";
      }

      Map var3 = (Map)this.a.get(var1);
      if (var3 == null) {
         Hashtable var5 = new Hashtable();
         this.a.put(var1, var5);
         return null;
      } else {
         monfox.toolkit.snmp.agent.x.master.b.b var4 = (monfox.toolkit.snmp.agent.x.master.b.b)var3.get(var2);
         return var4;
      }
   }

   void a(String var1, monfox.toolkit.snmp.agent.x.master.b.b var2) {
      if (var1 == null) {
         var1 = "";
      }

      Object var3 = (Map)this.a.get(var1);
      if (var3 == null) {
         var3 = new Hashtable();
         this.a.put(var1, var3);
      }

      ((Map)var3).put(var2.getOid(), var2);
   }

   public SnmpVarBindList allocateIndexes(MasterAgentX.Session var1, String var2, SnmpVarBindList var3, boolean var4, boolean var5) throws AgentXErrorException {
      boolean var14 = MasterAgentX.n;
      SnmpVarBindList var6 = new SnmpVarBindList();
      int var7 = 0;

      SnmpOid var9;
      label136: {
         label110: {
            int var11;
            label109:
            while(true) {
               SnmpVarBind var8;
               SnmpValue var10;
               monfox.toolkit.snmp.agent.x.master.b.b var12;
               SnmpVarBindList var10000;
               if (var7 < var3.size()) {
                  var8 = var3.get(var7);
                  var9 = var8.getOid();
                  var10 = var8.getValue();
                  var11 = var10.getType();
                  var12 = this.a(var2, var9);
                  if (!var14) {
                     label87: {
                        if (var12 == null) {
                           if (!this.isValidIndexType(var11)) {
                              this.c.debug(a("^GWu\n^M\u0001}\bSLY4\u0012NYD.F") + var11);
                              throw new AgentXErrorException(258, var7 + 1);
                           }

                           var12 = new monfox.toolkit.snmp.agent.x.master.b.b(var9, var11);
                           this.a(var2, var12);
                           if (!var14) {
                              break label87;
                           }
                        }

                        if (var12.getType() != var11) {
                           this.c.debug(a("@[Nz\u0001\u0017@Op\u0003O\tWu\nBL\u0001`\u001fGL\u001b4") + var11);
                           throw new AgentXErrorException(258, var7 + 1);
                        }
                     }

                     label117: {
                        if (!var4 && !var5) {
                           int var13 = var12.check(var10);
                           if (var13 != 0) {
                              this.c.debug(a("THOz\tC\t@x\nXJ@`\u0003\u0017@Op\u0003O\u0013\u0001") + var8);
                              throw new AgentXErrorException(var13, var7 + 1);
                           }

                           var6.add(new SnmpVarBind(var9, var10));
                           if (!var14) {
                              break label117;
                           }
                        }

                        SnmpValue var17;
                        if (var4) {
                           var17 = var12.generateNewIndex();
                           if (var17 == null) {
                              break label136;
                           }

                           var6.add(new SnmpVarBind(var9, var17));
                           if (var14) {
                              break label136;
                           }

                           if (!var14) {
                              break label117;
                           }
                        }

                        if (var5) {
                           var17 = var12.generateAnyIndex();
                           if (var17 == null) {
                              break label110;
                           }

                           var6.add(new SnmpVarBind(var9, var17));
                           if (var14) {
                              break label110;
                           }
                        }
                     }

                     ++var7;
                     if (!var14) {
                        continue;
                     }

                     var7 = 0;
                  } else {
                     if (var12 == null) {
                        break;
                     }

                     try {
                        var12.allocate(var1, var10);
                     } catch (AgentXErrorException var16) {
                        this.c.error(a("R[S{\u0014\u0017@O4\u0007[ENw\u0007C@OsF^GEq\u001e\u001b\t@x\u0014RHEmFAHM}\u0002V]DpFDF\u0001g\u000eX\\MpFYFU4\tTJTf"), var16);
                        throw new AgentXErrorException(var16.getError(), var7 + 1);
                     }

                     ++var7;
                     if (var14) {
                        var10000 = var6;
                        return var10000;
                     }
                  }
               } else {
                  var7 = 0;
               }

               do {
                  if (var7 >= var6.size()) {
                     var10000 = var6;
                     return var10000;
                  }

                  var10000 = var6;
                  if (var14) {
                     return var10000;
                  }

                  var8 = var6.get(var7);
                  var9 = var8.getOid();
                  var10 = var8.getValue();
                  var11 = var10.getType();
                  var12 = this.a(var2, var9);
                  if (var12 == null) {
                     break label109;
                  }

                  try {
                     var12.allocate(var1, var10);
                  } catch (AgentXErrorException var15) {
                     this.c.error(a("R[S{\u0014\u0017@O4\u0007[ENw\u0007C@OsF^GEq\u001e\u001b\t@x\u0014RHEmFAHM}\u0002V]DpFDF\u0001g\u000eX\\MpFYFU4\tTJTf"), var15);
                     throw new AgentXErrorException(var15.getError(), var7 + 1);
                  }

                  ++var7;
               } while(!var14);

               var10000 = var6;
               return var10000;
            }

            this.c.debug(a("YF\u0001b\u0007[\\D4\u0015R]\u0001r\tE\tHz\u0002RQ\u0001<\u0015_FTx\u0002\u0017GN`FXJBa\u0014\u001e\u0013\u0001") + var11);
            throw new AgentXErrorException(268, var7 + 1);
         }

         this.c.debug(a("THOz\tC\tBf\u0003V]D4\bR^\u0001}\bSLY.F") + var9);
         throw new AgentXErrorException(260, var7 + 1);
      }

      this.c.debug(a("THOz\tC\tBf\u0003V]D4\bR^\u0001}\bSLY.F") + var9);
      throw new AgentXErrorException(260, var7 + 1);
   }

   public void deallocateIndexes(MasterAgentX.Session var1, String var2, SnmpVarBindList var3) throws AgentXErrorException {
      boolean var11 = MasterAgentX.n;
      int var4 = 0;

      while(true) {
         SnmpVarBind var5;
         SnmpOid var6;
         SnmpValue var7;
         int var8;
         monfox.toolkit.snmp.agent.x.master.b.b var9;
         if (var4 < var3.size()) {
            var5 = var3.get(var4);
            var6 = var5.getOid();
            var7 = var5.getValue();
            var8 = var7.getType();
            var9 = this.a(var2, var6);
            if (!var11) {
               if (var9 == null) {
                  this.c.debug(a("^GEq\u001e\u0017GN`FVEM{\u0005V]DpF\r\t") + var5);
                  throw new AgentXErrorException(261, var4 + 1);
               }

               int var10 = var9.checkAllocated(var7, var1);
               if (var10 != 0) {
                  this.c.debug(a("^GEq\u001e\u0017GN`FVEM{\u0005V]DpF\r\t") + var5);
                  throw new AgentXErrorException(261, var4 + 1);
               }

               ++var4;
               if (!var11) {
                  continue;
               }

               var4 = 0;
            } else {
               if (var9 == null) {
                  this.c.debug(a("DANa\nS\tO{\u0012\u0017FBw\u0013E\u0013\u0001") + var5);
                  throw new AgentXErrorException(268, var4 + 1);
               }

               var9.deallocate(var7);
               ++var4;
               if (var11) {
                  return;
               }
            }
         } else {
            var4 = 0;
         }

         do {
            if (var4 >= var3.size()) {
               return;
            }

            var5 = var3.get(var4);
            var6 = var5.getOid();
            var7 = var5.getValue();
            var8 = var7.getType();
            var9 = this.a(var2, var6);
            if (var9 == null) {
               this.c.debug(a("DANa\nS\tO{\u0012\u0017FBw\u0013E\u0013\u0001") + var5);
               throw new AgentXErrorException(268, var4 + 1);
            }

            var9.deallocate(var7);
            ++var4;
         } while(!var11);

         return;
      }
   }

   void a(MasterAgentX.Session var1) {
      boolean var5 = MasterAgentX.n;
      List var2 = (List)this.b.get(var1);
      if (var2 != null) {
         Iterator var3 = var2.iterator();

         while(true) {
            if (var3.hasNext()) {
               monfox.toolkit.snmp.agent.x.master.b.a var4 = (monfox.toolkit.snmp.agent.x.master.b.a)var3.next();
               MasterAgentX.Session var10000 = var4.getSession();
               if (var5) {
                  break;
               }

               if (var10000 == var1) {
                  var4.deallocate();
               }

               if (!var5) {
                  continue;
               }
            }

            this.b.remove(var1);
            break;
         }

      }
   }

   private void a(MasterAgentX.Session var1, monfox.toolkit.snmp.agent.x.master.b.a var2) {
      Object var3 = (List)this.b.get(var1);
      if (var3 == null) {
         var3 = new Vector();
         this.b.put(var1, var3);
      }

      ((List)var3).add(var2);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 55;
               break;
            case 1:
               var10003 = 41;
               break;
            case 2:
               var10003 = 33;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 102;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class b {
      private SnmpOid a;
      private int b;
      private Map c = new Hashtable();
      private int d = 1;

      public b(SnmpOid var2, int var3) {
         this.a = var2;
         this.b = var3;
      }

      public int check(SnmpValue var1) {
         return this.check(var1, false);
      }

      public int check(SnmpValue var1, boolean var2) {
         if (var1.getType() != this.b) {
            return 258;
         } else if (var1 == null) {
            return 258;
         } else {
            monfox.toolkit.snmp.agent.x.master.b.a var3 = (monfox.toolkit.snmp.agent.x.master.b.a)this.c.get(var1);
            if (var3 == null) {
               return 0;
            } else if (var3.isAllocated()) {
               return 259;
            } else {
               return var2 ? 259 : 0;
            }
         }
      }

      public int checkAllocated(SnmpValue var1, MasterAgentX.Session var2) {
         if (var1 == null) {
            return 261;
         } else {
            monfox.toolkit.snmp.agent.x.master.b.a var3 = (monfox.toolkit.snmp.agent.x.master.b.a)this.c.get(var1);
            if (var3 == null) {
               return 261;
            } else {
               return var3.getSession() != var2 ? 261 : 0;
            }
         }
      }

      public void deallocate(SnmpValue var1) {
         monfox.toolkit.snmp.agent.x.master.b.a var2 = (monfox.toolkit.snmp.agent.x.master.b.a)this.c.get(var1);
         if (var2 != null) {
            var2.deallocate();
         }
      }

      public monfox.toolkit.snmp.agent.x.master.b.a allocate(MasterAgentX.Session var1, SnmpValue var2) throws AgentXErrorException {
         int var3 = this.check(var2);
         if (var3 != 0) {
            throw new AgentXErrorException(var3, 0);
         } else {
            monfox.toolkit.snmp.agent.x.master.b.a var4 = b.this.new a(var2, var1);
            this.c.put(var2, var4);
            b.this.a(var1, var4);
            return var4;
         }
      }

      public synchronized SnmpValue generateNewIndex() {
         try {
            while(true) {
               SnmpValue var1 = SnmpValue.getInstance(this.b, "" + this.d++);
               int var2 = this.check(var1, true);
               if (var2 == 0) {
                  if (!MasterAgentX.n) {
                     return var1;
                  }
               } else if (var2 != 259) {
                  b.this.c.debug(a("GI\u0015OYP\b\u001aMZKK\u001aUS\u0004F\u001eV\u0016MF\u001fDN\b\b\u000bSYGM\bR_JO[GWMD\u000eSS\u001e") + var2);
                  return null;
               }
            }
         } catch (Exception var3) {
            b.this.c.debug(a("GI\u0015OYP\b\u001aMZKK\u001aUS\u0004F\u001eV\u0016MF\u001fDN\b\b\u001eYUAX\u000fHYJ\b\u0012O\u0016TZ\u0014BSW[\u0012OQ"), var3);
            return null;
         }
      }

      public synchronized SnmpValue generateAnyIndex() {
         // $FF: Couldn't be decompiled
      }

      public SnmpOid getOid() {
         return this.a;
      }

      public int getType() {
         return this.b;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 36;
                  break;
               case 1:
                  var10003 = 40;
                  break;
               case 2:
                  var10003 = 123;
                  break;
               case 3:
                  var10003 = 33;
                  break;
               default:
                  var10003 = 54;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class a {
      SnmpValue a;
      MasterAgentX.Session b;

      a(SnmpValue var2, MasterAgentX.Session var3) {
         this.a = var2;
         this.b = var3;
      }

      public SnmpValue getValue() {
         return this.a;
      }

      public MasterAgentX.Session getSession() {
         return this.b;
      }

      public boolean isAllocated() {
         return this.b != null;
      }

      public void deallocate() {
         this.b = null;
      }
   }
}
