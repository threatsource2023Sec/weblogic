package monfox.toolkit.snmp.agent;

import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.vacm.SnmpCommunityTable;
import monfox.toolkit.snmp.agent.vacm.Vacm;
import monfox.toolkit.snmp.engine.SnmpBulkPDU;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.TransportEntity;

public class SnmpPendingIndication {
   private SnmpEngine a;
   private TransportEntity b;
   private SnmpMessage c;
   private SnmpContext d;
   private String e;
   private int f;
   private int g;
   private SnmpVarBindList h;
   private int i;
   private int j;
   private int k;
   private int l;
   private SnmpVarBindList m;
   private int[] n;
   private List o;
   private List p;
   private Logger q;
   private SnmpAgent r;

   public SnmpPendingIndication(SnmpEngine var1, TransportEntity var2, SnmpMessage var3, SnmpAgent var4) {
      label23: {
         super();
         this.d = null;
         this.e = null;
         this.o = new Vector();
         this.p = null;
         this.q = null;
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.r = var4;
         this.f = this.c.getData().getType();
         this.g = this.c.getVersion();
         this.h = var3.getData().getVarBindList();
         this.q = Logger.getInstance(a("eED+iSEM2WQbG?PUJ]2VX"));
         if (this.f == 165) {
            SnmpBulkPDU var5 = (SnmpBulkPDU)this.c.getData();
            this.i = var5.getNonRepeaters();
            this.j = var5.getMaxRepetitions();
            if (this.i > this.h.size()) {
               this.i = this.h.size();
            }

            if (this.i < 0) {
               this.i = 0;
            }

            if (this.j < 0) {
               this.j = 0;
            }

            if (!SnmpMibNode.b) {
               break label23;
            }
         }

         this.i = 0;
         this.j = 0;
      }

      this.k = 0;
      this.l = 0;
      this.m = null;
      this.n = new int[this.h.size()];
      this.a();
   }

   private void a() {
      boolean var10 = SnmpMibNode.b;

      try {
         SnmpContext var1 = null;
         if (this.getRequest().getVersion() == 3) {
            var1 = this.getRequest().getContext();
         } else {
            try {
               byte[] var2;
               String var3;
               int var4;
               int var10000;
               label54: {
                  var2 = this.getRequest().getData().getCommunity();
                  var3 = new String(var2);
                  this.e = var3;
                  var4 = -1;
                  if (this.r.isCommunityAtContextFormSupported() && var2 != null) {
                     int var5 = 0;

                     while(var5 < var2.length) {
                        var10000 = var2[var5];
                        if (var10) {
                           break label54;
                        }

                        if (var10000 == 64) {
                           var4 = var5;
                           if (!var10) {
                              break;
                           }
                        }

                        ++var5;
                        if (var10) {
                           break;
                        }
                     }
                  }

                  var10000 = var4;
               }

               if (var10000 > 0) {
                  String var14 = new String(var2, 0, var4);
                  String var15 = new String(var2, var4 + 1, var2.length - var4 - 1);
                  this.e = var14;
                  var1 = new SnmpContext((SnmpEngineID)null, var15);
               } else {
                  SnmpAccessControlModel var13 = this.r.getAccessControlModel();
                  if (var13 instanceof Vacm) {
                     Vacm var6 = (Vacm)var13;
                     SnmpCommunityTable var7 = var6.getCommunityTable();
                     String var8 = var7.getContextName(var3);
                     SnmpEngineID var9 = var7.getContextEngineID(var3);
                     if (var9 != null || var8 != null) {
                        var1 = new SnmpContext(var9, var8);
                     }
                  }
               }
            } catch (Exception var11) {
               this.q.debug(a("SY[4K\u0016BG{ZYE_>KBBG<\u0019UDD6LXB]\"\u0019BD\t8VX_L#M"), var11);
            }
         }

         this.d = var1;
      } catch (Exception var12) {
         this.q.error(a("SY[4K\u0016BG{ZYE]>AB\u000bY)VUNZ(PXL"), var12);
      }

   }

   public String getCommunity() {
      return this.e;
   }

   public String getContextName() {
      return this.d != null ? this.d.getContextName() : "";
   }

   public SnmpContext getContext() {
      return this.d;
   }

   public SnmpEngine getEngine() {
      return this.a;
   }

   public TransportEntity getSource() {
      return this.b;
   }

   public SnmpMessage getRequest() {
      return this.c;
   }

   public int getRequestType() {
      return this.f;
   }

   public int getVersion() {
      return this.g;
   }

   public SnmpVarBindList getRequestVBL() {
      return this.h;
   }

   public int getErrorStatus() {
      return this.k;
   }

   public int getErrorIndex() {
      return this.l;
   }

   public SnmpVarBindList getResponseVBL() {
      return this.m;
   }

   public void setResponseVBL(SnmpVarBindList var1) {
      this.m = var1;
      this.b();
   }

   private void b() {
      int var1 = 0;

      while(var1 < this.n.length) {
         this.n[var1] = 1;
         ++var1;
         if (SnmpMibNode.b) {
            break;
         }
      }

   }

   public int getNonRepeaters() {
      return this.i;
   }

   public int getMaxRepetitions() {
      return this.j;
   }

   public void setError(int var1, int var2) {
      label11: {
         if (this.g == 0) {
            this.k = a(var1);
            if (!SnmpMibNode.b) {
               break label11;
            }
         }

         this.k = var1;
      }

      this.l = var2;
   }

   public boolean hasError() {
      return this.k != 0;
   }

   public boolean done() {
      boolean var2 = SnmpMibNode.b;
      if (this.k != 0) {
         return true;
      } else {
         int var1 = 0;

         boolean var10000;
         while(true) {
            if (var1 < this.n.length) {
               var10000 = this.done(var1 + 1);
               if (var2) {
                  break;
               }

               if (!var10000) {
                  return false;
               }

               ++var1;
               if (!var2) {
                  continue;
               }
            }

            var10000 = true;
            break;
         }

         return var10000;
      }
   }

   public boolean done(int var1) {
      if (var1 >= 1 && var1 <= this.n.length) {
         if (this.f == 165) {
            if (var1 <= this.i) {
               return this.n[var1 - 1] == 1;
            } else if (this.j == 0) {
               return this.n[var1 - 1] == 0;
            } else {
               return this.n[var1 - 1] > 0;
            }
         } else {
            return this.n[var1 - 1] == 1;
         }
      } else {
         return true;
      }
   }

   public void completeGetSubRequest(SnmpVarBind var1, int var2) {
      if (this.f == 160 || this.f == 161) {
         if (var2 >= 1 && var2 <= this.n.length) {
            if (!this.done(var2)) {
               label37: {
                  SnmpValue var3 = var1.getValue();
                  if (this.g == 0) {
                     if (var3 == null) {
                        this.k = 2;
                        this.l = var2;
                        return;
                     }

                     int var4 = a(var3);
                     if (var4 != 0) {
                        this.k = 2;
                        this.l = var2;
                        return;
                     }

                     if (!SnmpMibNode.b) {
                        break label37;
                     }
                  }

                  if (var3 == null) {
                     var1.setToNoSuchInstance();
                  }
               }

               if (this.m == null) {
                  this.m = new SnmpVarBindList();
               }

               this.m.set(var2 - 1, var1);
               this.n[var2 - 1] = 1;
            }
         }
      }
   }

   public void completeSetSubRequest(int var1) {
      if (this.f == 163) {
         if (var1 >= 1 && var1 <= this.n.length) {
            if (!this.done(var1)) {
               this.n[var1 - 1] = 1;
            }
         }
      }
   }

   public void completeGetBulkNonRepeater(SnmpVarBind var1, int var2) {
      if (this.f == 165) {
         if (var2 >= 1 && var2 <= this.i) {
            if (!this.done(var2)) {
               SnmpValue var3 = var1.getValue();
               if (var3 == null) {
                  var1.setToNoSuchInstance();
               }

               if (this.m == null) {
                  this.m = new SnmpVarBindList();
               }

               this.m.set(var2 - 1, var1);
               this.n[var2 - 1] = 1;
            }
         }
      }
   }

   public void completeGetBulkRepeater(SnmpVarBindList var1, int var2) {
      boolean var6 = SnmpMibNode.b;
      if (this.f == 165) {
         if (var2 > this.i && var2 <= this.n.length) {
            if (!this.done(var2)) {
               int var3 = this.i;
               int var4 = this.i;

               while(true) {
                  if (var4 < var2 - 1) {
                     var3 += this.n[var4];
                     ++var4;
                     if (!var6 || !var6) {
                        continue;
                     }
                     break;
                  }

                  var4 = var1.size();
                  break;
               }

               if (var4 > this.j) {
                  var4 = this.j;
               }

               if (this.m == null) {
                  this.m = new SnmpVarBindList();
               }

               int var5 = 0;

               while(true) {
                  if (var5 < var4) {
                     this.m.insert(var5 + var3, var1.get(var5));
                     ++var5;
                     if (var6) {
                        break;
                     }

                     if (!var6) {
                        continue;
                     }
                  }

                  this.n[var2 - 1] = var4;
                  break;
               }

            }
         }
      }
   }

   public void completeGetBulkRepeater(SnmpVarBindList var1, int[] var2) {
      if (this.f == 165) {
         if (this.m == null) {
            this.m = new SnmpVarBindList();
         }

         this.m.add(var1);
         int var3 = 0;

         while(var3 < var2.length) {
            this.n[this.i + var3] = var2[var3];
            ++var3;
            if (SnmpMibNode.b) {
               break;
            }
         }

      }
   }

   public SnmpAgent getAgent() {
      return this.r;
   }

   public synchronized void addPostProcessor(Runnable var1) {
      if (this.p == null) {
         this.p = new Vector();
      }

      this.p.add(var1);
   }

   public synchronized void removePostProcessor(Runnable var1) {
      if (this.p != null) {
         this.p.remove(var1);
      }
   }

   public synchronized void performPostProcessing() {
      if (this.p != null) {
         ListIterator var1 = this.p.listIterator();

         while(var1.hasNext()) {
            try {
               Runnable var2 = (Runnable)var1.next();
               this.q.debug(a("D^G5PXL\t+VE_\t+KYHL(JYY\u0013") + var2);
               var2.run();
            } catch (Exception var3) {
               this.q.debug(a("FDZ/\u0019FYF8\\EX@5^\u0016NQ8\\F_@4W"), var3);
               if (SnmpMibNode.b) {
                  break;
               }
            }
         }

      }
   }

   public void setUserObject(int var1, Object var2) {
      boolean var3 = SnmpMibNode.b;

      while(true) {
         if (this.o.size() <= var1) {
            this.o.add((Object)null);
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         this.o.set(var1, var2);
         break;
      }

   }

   public Object getUserObject(int var1) {
      return var1 >= this.o.size() ? null : this.o.get(var1);
   }

   private static int a(int var0) {
      switch (var0) {
         case 0:
            return 0;
         case 1:
            return 1;
         case 2:
            return 2;
         case 3:
            return 3;
         case 4:
            return 4;
         case 5:
            return 5;
         case 6:
            return 2;
         case 7:
            return 3;
         case 8:
            return 3;
         case 9:
            return 3;
         case 10:
            return 3;
         case 11:
            return 2;
         case 12:
            return 3;
         case 13:
            return 5;
         case 14:
            return 5;
         case 15:
            return 5;
         case 16:
            return 2;
         case 17:
            return 2;
         case 18:
            return 2;
         default:
            return 2;
      }
   }

   private static int a(SnmpValue var0) {
      if (var0 instanceof SnmpNull) {
         int var1 = ((SnmpNull)var0).getTag();
         if (var1 == 128 || var1 == 129 || var1 == 130) {
            return 2;
         }
      }

      return 0;
   }

   protected void sendResponse() {
      this.r.getResponder().a(this);
   }

   public String toString() {
      StringBuffer var1;
      label73: {
         int var10000;
         int var2;
         SnmpVarBind var3;
         boolean var4;
         label74: {
            label75: {
               var4 = SnmpMibNode.b;
               var1 = new StringBuffer();
               var1.append(a("eED+iSEM2WQbG?PW_@4W\fP]\"IS\u0016"));
               var1.append(SnmpPDU.typeToString(this.f));
               var1.append(',');
               var1.append(a("@N[(PYE\u0014"));
               var1.append(this.b(this.g));
               var1.append(',');
               var1.append(a("DNX\r{z\u0016"));
               if (this.h == null) {
                  var1.append(a("X^E7"));
                  if (!var4) {
                     break label75;
                  }
               }

               var2 = 0;

               while(var2 < this.h.size()) {
                  var10000 = var2;
                  if (var4) {
                     break label74;
                  }

                  if (var2 > 0) {
                     var1.append(',');
                  }

                  label60: {
                     var3 = this.h.get(var2);
                     if (var3 == null) {
                        var1.append(a("X^E7"));
                        if (!var4) {
                           break label60;
                        }
                     }

                     var3.toString(var1);
                  }

                  ++var2;
                  if (var4) {
                     break;
                  }
               }
            }

            var1.append(',');
            var1.append(a("DNZ\r{z\u0016"));
            if (this.m == null) {
               var1.append(a("X^E7"));
               if (!var4) {
                  break label73;
               }
            }

            var10000 = 0;
         }

         var2 = var10000;

         while(var2 < this.m.size()) {
            if (var4) {
               return var1.toString();
            }

            if (var2 > 0) {
               var1.append(',');
            }

            label41: {
               var3 = this.m.get(var2);
               if (var3 == null) {
                  var1.append(a("X^E7"));
                  if (!var4) {
                     break label41;
                  }
               }

               var3.toString(var1);
            }

            ++var2;
            if (var4) {
               break;
            }
         }
      }

      var1.append(',');
      var1.append(a("SY[4Ke_H/LE\u0016"));
      var1.append(Snmp.errorStatusToString(this.k));
      var1.append(',');
      var1.append(a("SY[4K\u007fEM>A\u000b"));
      var1.append(this.l);
      var1.append('}');
      return var1.toString();
   }

   private String b(int var1) {
      switch (var1) {
         case 0:
            return "1";
         case 1:
            return a("\u0004H");
         case 2:
         default:
            return "?";
         case 3:
            return "3";
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
               var10003 = 54;
               break;
            case 1:
               var10003 = 43;
               break;
            case 2:
               var10003 = 41;
               break;
            case 3:
               var10003 = 91;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
