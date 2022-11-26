package monfox.toolkit.snmp.agent.sim;

import java.net.InetAddress;
import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.engine.TransportProvider;
import monfox.toolkit.snmp.engine.UdpTransportProvider;

public class SnmpIpAddressSimLeaf extends SnmpSimLeaf {
   public static final int AGENT = 1;
   public static final int LOCAL = 2;
   public static final int FIXED = 3;
   private Logger a = null;
   private int _access = 1;

   public SnmpIpAddressSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.a = Logger.getInstance(a("X\u000ft\u0019+{ }\r\u0010n\u0012j:\u000bf-|\b\u0004"));
   }

   public SnmpIpAddressSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.a = Logger.getInstance(a("X\u000ft\u0019+{ }\r\u0010n\u0012j:\u000bf-|\b\u0004"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      boolean var5 = SnmpSimLeaf.c;
      StringTokenizer var2 = new StringTokenizer(var1, a("#H#EB"), false);
      String[] var3 = new String[]{a("b\u0011x\r\u0006y\u0004j\u001a"), a("j\u0006|\u0007\u0016"), null};
      int var4 = 0;

      boolean var10000;
      while(true) {
         if (var4 < var3.length) {
            var10000 = var2.hasMoreTokens();
            if (var5) {
               break;
            }

            if (var10000) {
               var3[var4] = var2.nextToken();
            }

            ++var4;
            if (!var5) {
               continue;
            }
         }

         var10000 = var3[1].equalsIgnoreCase(a("j\u0006|\u0007\u0016"));
         break;
      }

      if (var10000) {
         this._access = 1;
         if (!var5) {
            return;
         }
      }

      if (var3[1].equalsIgnoreCase(a("g\u000ez\b\u000e"))) {
         this._access = 2;
         if (!var5) {
            return;
         }
      }

      if (var3[1].equalsIgnoreCase(a("m\ba\f\u0006"))) {
         this._access = 1;
         if (var3[2] == null) {
            return;
         }

         this.setValue(var3[2]);
         if (!var5) {
            return;
         }
      }

      throw new SnmpValueException(a("b\u000fo\b\u000eb\u00059\u0019\u0003y\u0000t\f\u0016n\u00139N") + var3[1] + a(",O9") + a("~\u0012x\u000e\u00071A>\u0000\u0012j\u0005}\u001b\u0007x\u00121\b\u0005n\u000fm\u0015\u0004b\u0019|\rK,"));
   }

   public void setIpSource(int var1) {
      this._access = var1;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      boolean var5 = SnmpSimLeaf.c;
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("l\u0004m;\u0007z\u0014|\u001a\u0016#\u0011pE") + var2 + "," + var3 + ")");
      }

      SnmpValue var4;
      if (this._access == 1) {
         label39: {
            var4 = this.getProviderIp(var1);
            if (var4 != null) {
               var3.setValue(var4);
               if (!var5) {
                  break label39;
               }
            }

            var3.setToNoSuchObject();
         }

         if (!var5) {
            return;
         }
      }

      if (this._access == 2) {
         label30: {
            var4 = this.getLocalIp(var1);
            if (var4 != null) {
               var3.setValue(var4);
               if (!var5) {
                  break label30;
               }
            }

            var3.setToNoSuchObject();
         }

         if (!var5) {
            return;
         }
      }

      if (this._access == 3) {
         super.getRequest(var1, var2, var3);
         if (!var5) {
            return;
         }
      }

      var3.setToNoSuchObject();
   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      boolean var5 = SnmpSimLeaf.c;
      var3.setOid(this.getInstanceOid());
      if (this.a.isDebugEnabled()) {
         this.a.debug(a("l\u0004m'\u0007s\u0015K\f\u0013~\u0004j\u001dJ{\b5") + var2 + "," + var3 + ")");
      }

      SnmpValue var4;
      if (this._access == 1) {
         label39: {
            var4 = this.getProviderIp(var1);
            if (var4 != null) {
               var3.setValue(var4);
               if (!var5) {
                  break label39;
               }
            }

            var3.setToNoSuchObject();
         }

         if (!var5) {
            return;
         }
      }

      if (this._access == 2) {
         label30: {
            var4 = this.getLocalIp(var1);
            if (var4 != null) {
               var3.setValue(var4);
               if (!var5) {
                  break label30;
               }
            }

            var3.setToNoSuchObject();
         }

         if (!var5) {
            return;
         }
      }

      if (this._access == 3) {
         super.getNextRequest(var1, var2, var3);
         if (!var5) {
            return;
         }
      }

      var3.setToNoSuchObject();
   }

   public SnmpValue getLocalIp(SnmpPendingIndication var1) {
      try {
         return new SnmpIpAddress(InetAddress.getLocalHost());
      } catch (Exception var3) {
         this.a.debug(a("+L4I\u0007y\u0013v\u001bX+\u0002x\u0007\fd\u00159\u000e\u0007e\u0004k\b\u0016nAu\u0006\u0001j\r9 2"), var3);
         return null;
      }
   }

   public SnmpValue getProviderIp(SnmpPendingIndication var1) {
      try {
         TransportEntity var2 = var1.getSource();
         if (var2 != null) {
            TransportProvider var3 = var2.getProvider();
            if (var3 instanceof UdpTransportProvider) {
               UdpTransportProvider var4 = (UdpTransportProvider)var3;
               InetAddress var5 = var4.getAddress();
               if (var5 != null) {
                  byte[] var6 = var5.getAddress();
                  SnmpIpAddress var7 = new SnmpIpAddress(var6);
                  return var7;
               }
            }
         }

         this.a.debug(a("+L4I\u0007y\u0013v\u001bX+\u0002x\u0007\fd\u00159\u000e\u0007\u007fAu\u0006\u0001j\r9 2+\u0000}\r\u0010n\u0012j"));
         return new SnmpIpAddress(InetAddress.getLocalHost());
      } catch (Exception var8) {
         this.a.debug(a("+L4I\u0007y\u0013v\u001bX+\u0002x\u0007\fd\u00159\u000e\u0007e\u0004k\b\u0016nAu\u0006\u0001j\r9 2"), var8);
         return null;
      }
   }

   public static SnmpMibLeafFactory getFactory(String var0) {
      return new Factory(var0);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 11;
               break;
            case 1:
               var10003 = 97;
               break;
            case 2:
               var10003 = 25;
               break;
            case 3:
               var10003 = 105;
               break;
            default:
               var10003 = 98;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private static class Factory implements SnmpMibLeafFactory {
      private String a = null;

      public Factory(String var1) {
         this.a = var1;
      }

      public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpValueException, SnmpMibException {
         SnmpIpAddressSimLeaf var4 = new SnmpIpAddressSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
