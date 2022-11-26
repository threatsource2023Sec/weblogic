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
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.SnmpRequestProcessor;
import monfox.toolkit.snmp.agent.x.common.AgentXCommunicationsException;
import monfox.toolkit.snmp.agent.x.common.AgentXErrorException;
import monfox.toolkit.snmp.agent.x.common.AgentXTimeoutException;
import monfox.toolkit.snmp.agent.x.pdu.SearchRange;

class c implements SnmpRequestProcessor {
   private MasterAgentX a = null;
   private Logger b = Logger.getInstance(a("\u0005t\u0004iT"), a("\u0000`\u000fjPl\u007f"), a("\u0000@/Jp\u0019w8Kg$T9Kv"));

   c(MasterAgentX var1) {
      this.a = var1;
   }

   public void handleRequest(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3) {
      if (var1.getRequest().getData().getType() == 160) {
         this.b(var1, var2, var3, false);
         if (!MasterAgentX.n) {
            return;
         }
      }

      if (var1.getRequest().getData().getType() == 161) {
         this.b(var1, var2, var3, true);
      }

   }

   private Map a(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3, boolean var4) {
      boolean var15 = MasterAgentX.n;
      Hashtable var5 = new Hashtable();
      SnmpVarBindList var6 = var1.getRequestVBL();
      int var7 = 0;

      while(var7 < var6.size()) {
         int var8 = var7 + 1;
         SnmpVarBind var9 = var6.get(var7);
         SnmpOid var10 = var9.getOid();
         SnmpMibNode var11 = null;
         if (var4) {
            var11 = var3.getNext(var10);
         } else {
            var3.get(var10);
         }

         if (var11 instanceof monfox.toolkit.snmp.agent.x.master.a) {
            label43: {
               if (!var2.checkAccess(var1, var8, var10)) {
                  SnmpVarBind var12 = new SnmpVarBind(var10, (SnmpValue)null, true);
                  var12.setToNoSuchObject();
                  var1.completeGetSubRequest(var12, var8);
                  if (!var15) {
                     break label43;
                  }
               }

               monfox.toolkit.snmp.agent.x.master.a var16 = (monfox.toolkit.snmp.agent.x.master.a)var11;
               if (!var16.isInstance() || var1.getRequest().getData().getType() != 161) {
                  d var13 = var16.getSubtree();
                  Object var14 = (List)var5.get(var13);
                  if (var14 == null) {
                     var14 = new Vector();
                     var5.put(var13, var14);
                  }

                  ((List)var14).add(new a(var16, var7, var10));
               }
            }
         }

         ++var7;
         if (var15) {
            break;
         }
      }

      return var5;
   }

   private void b(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3, boolean var4) {
      boolean var13 = MasterAgentX.n;
      this.b.debug(a(")F$@h$`/P>a") + var1.getRequest().getData());
      Map var5 = this.a(var1, var2, var3, var4);
      Iterator var6 = var5.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry var7 = (Map.Entry)var6.next();
         d var8 = (d)var7.getKey();
         List var9 = (List)var7.getValue();
         SnmpVarBindList var10 = new SnmpVarBindList();
         int var11 = 0;

         while(true) {
            if (var11 < var9.size()) {
               a var12 = (a)var9.get(var11);
               var10.add(var12.c);
               ++var11;
               if (!var13 || !var13) {
                  continue;
               }
               break;
            }

            this.b.debug(a("\u0000@/Jp\u0019\u0007\raP\u001a") + var8.getContext() + a("\u001c\u001dj.") + var10);
            break;
         }

         label26: {
            if (var1.getRequest().getData().getType() == 160) {
               this.b(var8, var10, var9, var1, var2, var3);
               if (!var13) {
                  break label26;
               }
            }

            if (var1.getRequest().getData().getType() == 161) {
               this.a(var8, var10, var9, var1, var2, var3);
            }
         }

         if (var13) {
            break;
         }
      }

   }

   private int a(List var1, int var2) {
      if (var2 <= 0) {
         return 0;
      } else if (var2 - 1 >= var1.size()) {
         return 0;
      } else {
         a var3 = (a)var1.get(var2 - 1);
         return var3.b + 1;
      }
   }

   private void a(d var1, SnmpVarBindList var2, List var3, SnmpPendingIndication var4, SnmpAccessPolicy var5, SnmpMib var6) {
      boolean var15 = MasterAgentX.n;
      if (this.b.isDebugEnabled()) {
         this.b.debug(a("1U%Ga2T\rAp\u000fB2P>a") + var1.getSubtreeOid() + "," + var2 + "," + var3 + "," + var4);
      }

      try {
         Vector var7 = new Vector();
         int var8 = 0;

         boolean var10000;
         while(true) {
            if (var8 < var3.size()) {
               a var9 = (a)var3.get(var8);
               SnmpOid var10 = var9.c;
               var10000 = var9.a.getOid().contains(var10);
               if (var15) {
                  break;
               }

               if (!var10000 && var9.a.getOid().compareTo(var10) > 0) {
                  var10 = var9.a.getOid();
               }

               SnmpOid var11 = var9.a.getMaxOid();
               if (var9.a.getMaxOid() == null) {
                  SnmpOid var12 = var9.a.getOid().getParent();
                  var12.append(var9.a.getOid().getLast() + 1L);
                  var11 = var12;
               }

               SearchRange var23 = new SearchRange(var10, false, var11);
               var7.add(var23);
               ++var8;
               if (!var15) {
                  continue;
               }
            }

            var10000 = this.b.isDebugEnabled();
            break;
         }

         if (var10000) {
            this.b.debug(a("l\u00079V[-N9P>a") + var7);
         }

         SnmpVarBindList var19 = var1.getSession().performGetNext(var1.getContext(), var1.getTimeoutSecs(), var7);
         if (this.b.isDebugEnabled()) {
            this.b.debug(a("l\u00078Wt\u001eQ(H>a") + var19);
         }

         int var20 = 0;

         while(var20 < var3.size()) {
            a var21 = (a)var3.get(var20);
            int var22 = var21.b + 1;
            if (var15) {
               break;
            }

            label85: {
               if (var20 < var19.size()) {
                  SnmpVarBind var24 = var19.get(var20);
                  if (this.b.isDebugEnabled()) {
                     this.b.debug(a("l\u0007)La\"L#JcaQ(\u001e$") + var24);
                  }

                  boolean var13 = false;
                  if (!var24.isError()) {
                     label111: {
                        SnmpOid var14 = var24.getOid();
                        if (var21.a.getOid().contains(var14)) {
                           if (var5.checkAccess(var4, var21.b + 1, var14)) {
                              if (this.b.isDebugEnabled()) {
                                 this.b.debug(a("l\u0007#Jg-R.Mj&\u0007#J$3B9Tk/T/\u001e$") + var24);
                              }

                              var13 = true;
                              var4.completeGetSubRequest(var24, var21.b + 1);
                              if (!var15) {
                                 break label111;
                              }
                           }

                           this.b.debug(a("l\u0007$KpaF)Ga2T#Fh$\u0007?Wm/@jGq3U/JpaF)Ga2TjTk-N)]"));
                           if (!var15) {
                              break label111;
                           }
                        }

                        if (this.b.isDebugEnabled()) {
                           this.b.debug(a("l\u0007%Qp2N.A$.AjVa0R/Wp$CjVe/@/\u007f") + var21.a.getOid() + a("\u001c\u001dj") + var24);
                        }
                     }
                  }

                  if (!var13) {
                     var4.setUserObject(var21.b + 1, var21.a);
                  }

                  if (!var15) {
                     break label85;
                  }
               }

               this.b.debug(a("/HjAj.R-L$\u0017F8fm/C9\u0004m/\u0007\raPaU/Wt.I9A"));
            }

            ++var20;
            if (var15) {
               break;
            }
         }

      } catch (AgentXErrorException var16) {
         this.b.debug(a("$U8KvaN$\u0004E&B$P\\a`/P$3B;Qa2S"), var16);
         if (var16.isSnmpError()) {
            var4.setError(var16.getError(), this.a(var3, var16.getIndex()));
         } else {
            var4.setError(5, 0);
         }
      } catch (AgentXTimeoutException var17) {
         this.b.debug(a("$U8KvaN$\u0004E&B$P\\aS#Ia.R>"), var17);
         var4.setError(5, 0);
      } catch (AgentXCommunicationsException var18) {
         this.b.debug(a("$U8KvaN$\u0004E&B$P\\aD%Ii4I#Ge5N%Jw"), var18);
         var4.setError(5, 0);
      }
   }

   private void b(d var1, SnmpVarBindList var2, List var3, SnmpPendingIndication var4, SnmpAccessPolicy var5, SnmpMib var6) {
      boolean var11 = MasterAgentX.n;

      try {
         SnmpVarBindList var7 = null;
         var7 = var1.getSession().performGet(var1.getContext(), var1.getTimeoutSecs(), var2);
         int var8 = 0;

         while(var8 < var3.size()) {
            a var9 = (a)var3.get(var8);
            if (var11) {
               break;
            }

            label50: {
               if (var7 == null) {
                  SnmpVarBind var10 = new SnmpVarBind(var9.c, (SnmpValue)null, true);
                  var10.setToNoSuchObject();
                  var4.completeGetSubRequest(var10, var9.b + 1);
                  if (!var11) {
                     break label50;
                  }
               }

               if (var8 < var7.size()) {
                  var4.completeGetSubRequest(var7.get(var8), var9.b + 1);
                  if (!var11) {
                     break label50;
                  }
               }

               this.b.debug(a("/HjAj.R-L$\u0017F8fm/C9\u0004m/\u0007\raPaU/Wt.I9A"));
            }

            ++var8;
            if (var11) {
               break;
            }
         }

      } catch (AgentXErrorException var12) {
         this.b.debug(a("$U8KvaN$\u0004E&B$P\\a`/P$3B;Qa2S"), var12);
         if (var12.isSnmpError()) {
            var4.setError(var12.getError(), this.a(var3, var12.getIndex()));
         } else {
            var4.setError(5, 0);
         }
      } catch (AgentXTimeoutException var13) {
         this.b.debug(a("$U8KvaN$\u0004E&B$P\\aS#Ia.R>"), var13);
         var4.setError(5, 0);
      } catch (AgentXCommunicationsException var14) {
         this.b.debug(a("$U8KvaN$\u0004E&B$P\\aD%Ii4I#Ge5N%Jw"), var14);
         var4.setError(5, 0);
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
               var10003 = 65;
               break;
            case 1:
               var10003 = 39;
               break;
            case 2:
               var10003 = 74;
               break;
            case 3:
               var10003 = 36;
               break;
            default:
               var10003 = 4;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class a {
      monfox.toolkit.snmp.agent.x.master.a a;
      int b;
      SnmpOid c;

      a(monfox.toolkit.snmp.agent.x.master.a var2, int var3, SnmpOid var4) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }
   }
}
