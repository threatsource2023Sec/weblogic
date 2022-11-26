package monfox.toolkit.snmp.mgr;

import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpTimeTicks;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpPDU;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.SnmpTransportException;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

public class SnmpPendingInform {
   private SnmpSession a;
   private SnmpEngine b;
   private TransportEntity c;
   private SnmpMessage d;
   private SnmpOid e;
   private int f;
   private int g;
   private SnmpVarBindList h;
   private String i = null;
   private int j = 0;
   private int k = 0;
   private SnmpVarBindList l = null;
   private SnmpVarBindList m = null;
   private int n;
   private Logger o = Logger.getInstance(a("kB\n\ng"), a("bV\u0016"), a("|\u007f)7gJ\u007f .YHX*!X]|"));

   SnmpPendingInform(SnmpSession var1, SnmpEngine var2, TransportEntity var3, SnmpMessage var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.h = var4.getData().getVarBindList();
      if (var4.getContext() != null) {
         this.i = var4.getContext().getContextName();
      }

      this.f = this.d.getData().getType();
      this.g = this.d.getVersion();
      this.l = new SnmpVarBindList(this.h);
      SnmpVarBindList var5 = new SnmpVarBindList(this.h);
      if (var5.size() >= 2) {
         SnmpValue var6 = var5.get(0).getValue();
         if (var6 instanceof SnmpTimeTicks) {
            label16: {
               this.n = var6.intValue();
               var5.remove(0);
               SnmpValue var7 = var5.get(0).getValue();
               if (var7 instanceof SnmpOid) {
                  this.e = (SnmpOid)var7;
                  var5.remove(0);
                  if (!SnmpSession.B) {
                     break label16;
                  }
               }

               this.e = new SnmpOid(0L);
            }
         }
      }

      this.m = var5;
   }

   public SnmpSession getSession() {
      return this.a;
   }

   public SnmpEngine getEngine() {
      return this.b;
   }

   public TransportEntity getSource() {
      return this.c;
   }

   public SnmpOid getTrapOid() {
      return this.e;
   }

   public int getSysUpTime() {
      return this.n;
   }

   public SnmpMessage getRequest() {
      return this.d;
   }

   public byte[] getCommunity() {
      return this.d.getData().getCommunity();
   }

   public String getContext() {
      return this.i;
   }

   public int getVersion() {
      return this.g;
   }

   public SnmpVarBindList getRequestVarBindList() {
      return this.h;
   }

   public SnmpVarBindList getRequestObjectValues() {
      return this.m;
   }

   public int getErrorStatus() {
      return this.j;
   }

   public int getErrorIndex() {
      return this.k;
   }

   public SnmpVarBindList getResponseVarBindList() {
      return this.l;
   }

   public void setError(int var1, int var2) {
      this.j = var1;
      this.k = var2;
   }

   void a(SnmpVarBind var1, int var2) {
      if (!this.done()) {
         SnmpValue var3 = var1.getValue();
         if (var3 == null) {
            var1.setToNoSuchInstance();
         }

         if (this.l == null) {
            this.l = new SnmpVarBindList();
         }

         this.l.set(var2, var1);
      }
   }

   public void setResponseVarBindList(SnmpVarBindList var1) {
      this.l = var1;
   }

   protected boolean done() {
      return this.j != 0;
   }

   public void sendResponse() {
      boolean var8 = SnmpSession.B;
      if (this.j != 0) {
         this.l = this.h.cloneWithValue();
      }

      if (this.l == null) {
         this.l = this.h.cloneWithValue();
      }

      SnmpMessage var2;
      SnmpMessageProfile var3;
      label44: {
         SnmpRequestPDU var1 = new SnmpRequestPDU();
         var1.setType(162);
         var1.setRequestId(this.d.getData().getRequestId());
         var1.setCommunity(this.d.getData().getCommunity());
         var1.setVarBindList(this.l);
         var1.setErrorStatus(this.j);
         var1.setErrorIndex(this.k);
         var2 = new SnmpMessage(this.d);
         var2.setData(var1);
         int var4 = this.d.getVersion();
         String var5;
         if (var4 == 0) {
            var5 = new String(this.d.getData().getCommunity());
            var3 = new SnmpMessageProfile(0, 1, 0, var5);
            if (!var8) {
               break label44;
            }
         }

         if (var4 == 1) {
            var5 = new String(this.d.getData().getCommunity());
            var3 = new SnmpMessageProfile(1, 2, 0, var5);
            if (!var8) {
               break label44;
            }
         }

         if (var4 == 3) {
            var2.setSnmpEngineID(this.d.getSnmpEngineID());
            var2.setContext(this.d.getContext());
            V3SnmpMessageParameters var11 = (V3SnmpMessageParameters)this.d.getMessageParameters();
            byte var6 = var11.getFlags();
            byte[] var7 = this.d.getSecurityParameters().getSecurityName();
            var3 = new SnmpMessageProfile(3, 3, var6, new String(var7));
            if (!var8) {
               break label44;
            }
         }

         return;
      }

      var2.setMessageProfile(var3);
      var2.setOidMap(this.d.getOidMap());

      try {
         this.b.send(var2, this.c);
      } catch (SnmpCoderException var9) {
         this.o.error(a("Jc6(E\u000fb!)SF\u007f#gEJb4(Y\\t"), var9);
      } catch (SnmpTransportException var10) {
         this.o.error(a("[c%)D_~63\u0017Ji'\"G[x+)\u0017\\t*#^Avd5R\\a+)DJ"), var10);
      }

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
               var4 = SnmpSession.B;
               var1 = new StringBuffer();
               var1.append(a("_xy"));
               var1.append('{');
               var1.append(a("[h4\"\n"));
               var1.append(SnmpPDU.typeToString(this.f));
               var1.append(',');
               var1.append(a("Yt64^@\u007fy"));
               var1.append(this.a(this.g));
               var1.append(',');
               var1.append(a("]t5\u0011uc,"));
               if (this.h == null) {
                  var1.append(a("Ad(+"));
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
                        var1.append(a("Ad(+"));
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
            var1.append(a("]t7\u0011uc,"));
            if (this.l == null) {
               var1.append(a("Ad(+"));
               if (!var4) {
                  break label73;
               }
            }

            var10000 = 0;
         }

         var2 = var10000;

         while(var2 < this.l.size()) {
            if (var4) {
               return var1.toString();
            }

            if (var2 > 0) {
               var1.append(',');
            }

            label41: {
               var3 = this.l.get(var2);
               if (var3 == null) {
                  var1.append(a("Ad(+"));
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
      var1.append(a("Jc6(E|e%3B\\,"));
      var1.append(Snmp.errorStatusToString(this.j));
      var1.append(',');
      var1.append(a("Jc6(Ef\u007f \"O\u0012"));
      var1.append(this.k);
      var1.append('}');
      return var1.toString();
   }

   private String a(int var1) {
      switch (var1) {
         case 0:
            return "1";
         case 1:
            return a("\u001dr");
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
               var10003 = 47;
               break;
            case 1:
               var10003 = 17;
               break;
            case 2:
               var10003 = 68;
               break;
            case 3:
               var10003 = 71;
               break;
            default:
               var10003 = 55;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
