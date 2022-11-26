package monfox.toolkit.snmp.agent.proxy;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.SnmpRequestProcessor;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

class a implements SnmpRequestProcessor {
   private SnmpAgent a = null;
   private SnmpProxySubtreeTable b;
   private SnmpProxyForwarder c;
   private Logger d = Logger.getInstance(a("n\"t\u0001C"), a("k6\u007f\u0002G\u0007!h\u0003Ks"), a("y\u001fW<CX\u001eB5@_\u0013N>vO#_=fO\u0002N\u001caE\u0012_?`E\u0003"));

   a(SnmpAgent var1, SnmpProxySubtreeTable var2, SnmpProxyForwarder var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public void handleRequest(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3) {
      this.d.debug(a("B\u0010T(\u007fO#_=fO\u0002Nv3I\u0019_/xC\u001f]luE\u0003\u001a?fH\u0005H)v\n\u0017U>dK\u0003S\"t"));
      if (this.b.getNumOfRows() == 0) {
         this.d.debug(a("D\u001e\u001a?fH\u0005H)vYQH)tC\u0002N)aO\u0015"));
      } else {
         int var4 = var1.getRequest().getData().getType();
         switch (var4) {
            case 160:
            case 161:
            case 163:
               this.b(var1, var2, var3, var4);
            case 162:
            default:
         }
      }
   }

   private Map a(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3, int var4) {
      int var15 = SnmpProxyForwarder.g;
      this.d.debug(a("M\u0014N\u001ffH\u0005H)vg\u0010J\n|X#_=fO\u0002N"));
      Hashtable var5 = new Hashtable();
      SnmpVarBindList var6 = var1.getRequestVBL();
      int var7 = 0;

      while(var7 < var6.size()) {
         int var8 = var7 + 1;
         SnmpVarBind var9 = var6.get(var7);
         SnmpOid var10 = var9.getOid();
         SnmpMibNode var11 = null;
         if (var4 == 161) {
            var11 = var3.getNext(var10);
         } else {
            var11 = var3.get(var10);
         }

         if (var11 instanceof b) {
            label61: {
               if (this.d.isDebugEnabled()) {
                  this.d.debug(a("Y\u0004X8aO\u0014\u001a<aE\tCl}E\u0015_luE\u0004T(H") + ((b)var11).getName() + a("wK\u001a") + var11);
               }

               if (!var2.checkAccess(var1, var8, var11.getOid())) {
                  SnmpVarBind var12;
                  label48: {
                     var12 = new SnmpVarBind(var10, (SnmpValue)null, true);
                     if (var4 == 161) {
                        var12.setToEndOfMibView();
                        if (var15 == 0) {
                           break label48;
                        }
                     }

                     var12.setToNoSuchObject();
                  }

                  var1.completeGetSubRequest(var12, var8);
                  if (var15 == 0) {
                     break label61;
                  }
               }

               b var16 = (b)var11;
               if (!var16.isInstance() || var1.getRequest().getData().getType() != 161) {
                  label62: {
                     SnmpPeer var13 = var16.getPeer();
                     if (var13 != null) {
                        Object var14 = (List)var5.get(var13);
                        if (var14 == null) {
                           var14 = new Vector();
                           var5.put(var13, var14);
                        }

                        ((List)var14).add(new monfox.toolkit.snmp.agent.proxy.a.a(var16, var7, var10, var9.getValue()));
                        if (var15 == 0) {
                           break label62;
                        }
                     }

                     this.d.warn(a("D\u001e\u001a<vO\u0003\u001a*|_\u001f^luE\u0003\u001a?fH\u0005H)v\n\u001fU(v\u0010Q") + var16.getName());
                  }
               }
            }
         }

         ++var7;
         if (var15 != 0) {
            break;
         }
      }

      return var5;
   }

   private void b(SnmpPendingIndication var1, SnmpAccessPolicy var2, SnmpMib var3, int var4) {
      int var13 = SnmpProxyForwarder.g;
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("B\u0010T(\u007fO\"S!cF\u0014h)b_\u0014I8)\n") + var1.getRequest().getData());
      }

      Map var5 = this.a(var1, var2, var3, var4);
      Iterator var6 = var5.entrySet().iterator();

      label53:
      while(var6.hasNext()) {
         Map.Entry var7 = (Map.Entry)var6.next();
         SnmpPeer var8 = (SnmpPeer)var7.getKey();
         List var9 = (List)var7.getValue();
         SnmpVarBindList var10 = new SnmpVarBindList();
         int var11 = 0;

         while(var11 < var9.size()) {
            monfox.toolkit.snmp.agent.proxy.a.a var12 = (monfox.toolkit.snmp.agent.proxy.a.a)var9.get(var11);
            var10.add(new SnmpVarBind(var12.c, var12.d));
            ++var11;
            if (var13 != 0) {
               continue label53;
            }

            if (var13 != 0) {
               break;
            }
         }

         SnmpPeer var14 = var8;
         if (var8.isPassThrough()) {
            var14 = var8.createFromPassThrough(var1.getRequest());
            this.d.debug(a("Z\u0010I?>^\u0019H#fM\u0019\u001a<vO\u0003\u0016lpX\u0014[8zD\u0016\u001a*|X\u0006[>wC\u001f]lcO\u0014H"));
         }

         label59: {
            this.d.debug(a("y\u001fW<CX\u001eB5@_\u0013N>vO*") + var8 + a("wK\u001aF") + var10);
            if (var4 == 160) {
               this.b(var14, var10, var9, var1, var2, var3);
               if (var13 == 0) {
                  break label59;
               }
            }

            if (var4 == 161) {
               this.a(var14, var10, var9, var1, var2, var3);
               if (var13 == 0) {
                  break label59;
               }
            }

            if (var4 == 163) {
               this.c(var14, var10, var9, var1, var2, var3);
            }
         }

         if (var1.getErrorStatus() != 0) {
            return;
         }

         if (var13 != 0) {
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
         monfox.toolkit.snmp.agent.proxy.a.a var3 = (monfox.toolkit.snmp.agent.proxy.a.a)var1.get(var2 - 1);
         return var3.b + 1;
      }
   }

   private void a(SnmpPeer var1, SnmpVarBindList var2, List var3, SnmpPendingIndication var4, SnmpAccessPolicy var5, SnmpMib var6) {
      int var16 = SnmpProxyForwarder.g;
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("Z\u0003U/vY\u0002})gd\u0014B8H") + var1 + a("wK\u001a\n\\x&{\u001eWc?}F") + var2);
      }

      try {
         new Vector();
         SnmpVarBindList var8 = new SnmpVarBindList();
         int var9 = 0;

         while(var9 < var3.size()) {
            monfox.toolkit.snmp.agent.proxy.a.a var10 = (monfox.toolkit.snmp.agent.proxy.a.a)var3.get(var9);
            SnmpOid var11 = var10.c;
            if (!var10.a.getOid().contains(var11) && var10.a.getOid().compareTo(var11) > 0) {
               var11 = var10.a.getOid();
            }

            var8.add(var11);
            ++var9;
            if (var16 != 0) {
               break;
            }
         }

         SnmpVarBindList var20 = this.a.getSession().performGetNext(var1, var8);
         int var21 = 0;

         while(var21 < var3.size()) {
            monfox.toolkit.snmp.agent.proxy.a.a var22 = (monfox.toolkit.snmp.agent.proxy.a.a)var3.get(var21);
            int var12 = var22.b + 1;
            if (var16 != 0) {
               break;
            }

            label65: {
               if (var21 < var20.size()) {
                  SnmpVarBind var13 = var20.get(var21);
                  boolean var14 = false;
                  if (!var13.isError()) {
                     SnmpOid var15 = var13.getOid();
                     if (var22.a.getOid().contains(var15) && var5.checkAccess(var4, var22.b + 1, var15)) {
                        var14 = true;
                        var4.completeGetSubRequest(var13, var22.b + 1);
                     }
                  }

                  if (!var14) {
                     var4.setUserObject(var22.b + 1, var22.a);
                  }

                  if (var16 == 0) {
                     break label65;
                  }
               }

               this.d.debug(a("D\u001e\u001a)}E\u0004]$3|\u0010H\u000ezD\u0015IlzDQ}\tG\n\u0003_?cE\u001fI)"));
            }

            ++var21;
            if (var16 != 0) {
               break;
            }
         }

      } catch (SnmpErrorException var17) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl@D\u001cJ\u001caE\tC\u001ffH\u0005H)v\n6_83X\u0014K9vY\u0005"), var17);
         if (this.c.isSubtreeProxyBypassModeEnabled()) {
            this.a(var4, var3, var2);
         } else {
            var4.setError(var17.getErrorStatus(), this.a(var3, var17.getErrorIndex()));
         }
      } catch (SnmpTimeoutException var18) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl@_\u0013N>vOQ\u001a8zG\u0014U9g"), var18);
         if (this.c.isSubtreeProxyBypassModeEnabled()) {
            this.a(var4, var3, var2);
         } else {
            var4.setError(5, 0);
         }
      } catch (SnmpException var19) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl@_\u0013{+vD\u0005\u001a/|G\u001cO\"zI\u0010N%|D\u0002"), var19);
         if (this.c.isSubtreeProxyBypassModeEnabled()) {
            this.a(var4, var3, var2);
         } else {
            var4.setError(5, 0);
         }
      }
   }

   private void a(SnmpPendingIndication var1, List var2, SnmpVarBindList var3) {
      int var8 = SnmpProxyForwarder.g;
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("H\bJ-`Y\u0018T+3Y\u0004X-tO\u001fNl}E\u0015_?)\n{") + var3);
      }

      int var4 = 0;

      while(var4 < var2.size()) {
         label21: {
            monfox.toolkit.snmp.agent.proxy.a.a var5 = (monfox.toolkit.snmp.agent.proxy.a.a)var2.get(var4);
            int var6 = var5.b + 1;
            if (var4 < var3.size()) {
               var3.get(var4);
               var1.setUserObject(var5.b + 1, var5.a);
               if (var8 == 0) {
                  break label21;
               }
            }

            this.d.debug(a("D\u001e\u001a)}E\u0004]$3|\u0010H\u000ezD\u0015I"));
         }

         ++var4;
         if (var8 != 0) {
            break;
         }
      }

   }

   private void b(SnmpPeer var1, SnmpVarBindList var2, List var3, SnmpPendingIndication var4, SnmpAccessPolicy var5, SnmpMib var6) {
      int var11 = SnmpProxyForwarder.g;
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("Z\u0003U/vY\u0002})gq") + var1 + a("wK\u001a\n\\x&{\u001eWc?}F") + var2);
      }

      try {
         SnmpVarBindList var7 = null;
         var7 = this.a.getSession().performGet(var1, var2);
         int var8 = 0;

         while(var8 < var3.size()) {
            monfox.toolkit.snmp.agent.proxy.a.a var9 = (monfox.toolkit.snmp.agent.proxy.a.a)var3.get(var8);
            if (var11 != 0) {
               break;
            }

            label46: {
               if (var7 == null) {
                  SnmpVarBind var10 = new SnmpVarBind(var9.c, (SnmpValue)null, true);
                  var10.setToNoSuchObject();
                  var4.completeGetSubRequest(var10, var9.b + 1);
                  if (var11 == 0) {
                     break label46;
                  }
               }

               if (var8 < var7.size()) {
                  var4.completeGetSubRequest(var7.get(var8), var9.b + 1);
                  if (var11 == 0) {
                     break label46;
                  }
               }

               this.d.debug(a("D\u001e\u001a)}E\u0004]$3|\u0010H\u000ezD\u0015IlzDQ}\tG\n\u0003_?cE\u001fI)"));
            }

            ++var8;
            if (var11 != 0) {
               break;
            }
         }
      } catch (SnmpErrorException var12) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl@_\u0013{+vD\u0005\u001a\u000bv^QH)b_\u0014I8)\n") + var1, var12);
         var4.setError(var12.getErrorStatus(), this.a(var3, var12.getErrorIndex()));
      } catch (SnmpTimeoutException var13) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl`_\u0013N>vOQJ>|R\b\u001a>v[]\u001a?fH\\[+vD\u0005\u001a8zG\u0014U9g\u0010Q") + var1, var13);
         var4.setError(5, 0);
         return;
      } catch (SnmpException var14) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl`_\u0013N>vOQi9qk\u0016_\"g\n\u0012U!~_\u001fS/r^\u0018U\"`\u0010Q") + var1, var14);
         var4.setError(5, 0);
         return;
      }

   }

   private void c(SnmpPeer var1, SnmpVarBindList var2, List var3, SnmpPendingIndication var4, SnmpAccessPolicy var5, SnmpMib var6) {
      int var11 = SnmpProxyForwarder.g;
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("Z\u0003U/vY\u0002i)gq") + var1 + a("wK\u001a\n\\x&{\u001eWc?}F") + var2);
      }

      try {
         SnmpVarBindList var7 = null;
         var7 = this.a.getSession().performSet(var1, var2);
         int var8 = 0;

         while(var8 < var3.size()) {
            monfox.toolkit.snmp.agent.proxy.a.a var9 = (monfox.toolkit.snmp.agent.proxy.a.a)var3.get(var8);
            if (var11 != 0) {
               break;
            }

            label46: {
               if (var7 == null) {
                  SnmpVarBind var10 = new SnmpVarBind(var9.c, (SnmpValue)null, true);
                  var10.setToNoSuchObject();
                  var4.completeGetSubRequest(var10, var9.b + 1);
                  if (var11 == 0) {
                     break label46;
                  }
               }

               if (var8 < var7.size()) {
                  var4.completeGetSubRequest(var7.get(var8), var9.b + 1);
                  if (var11 == 0) {
                     break label46;
                  }
               }

               this.d.debug(a("D\u001e\u001a)}E\u0004]$3|\u0010H\u000ezD\u0015IlzDQi\tG\n\u0003_?cE\u001fI)"));
            }

            ++var8;
            if (var11 != 0) {
               break;
            }
         }
      } catch (SnmpErrorException var12) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl@_\u0013{+vD\u0005\u001a\u000bv^QH)b_\u0014I8)\n") + var1, var12);
         var4.setError(var12.getErrorStatus(), this.a(var3, var12.getErrorIndex()));
      } catch (SnmpTimeoutException var13) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl`_\u0013N>vOQJ>|R\b\u001a>v[]\u001a?fH\\[+vD\u0005\u001a8zG\u0014U9g\u0010Q") + var1, var13);
         var4.setError(5, 0);
         return;
      } catch (SnmpException var14) {
         this.d.debug(a("O\u0003H#a\n\u0018Tl`_\u0013N>vOQi9qk\u0016_\"g\n\u0012U!~_\u001fS/r^\u0018U\"`\u0010Q") + var1, var14);
         var4.setError(5, 0);
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
               var10003 = 42;
               break;
            case 1:
               var10003 = 113;
               break;
            case 2:
               var10003 = 58;
               break;
            case 3:
               var10003 = 76;
               break;
            default:
               var10003 = 19;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class a {
      b a;
      int b;
      SnmpOid c;
      SnmpValue d;

      a(b var2, int var3, SnmpOid var4, SnmpValue var5) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
         this.d = var5;
      }
   }
}
