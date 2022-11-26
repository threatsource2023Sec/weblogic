package monfox.toolkit.snmp.agent.sim;

import java.net.InetAddress;
import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
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
import monfox.toolkit.snmp.util.ByteFormatter;

public class SnmpMacAddressSimLeaf extends SnmpSimLeaf {
   public static final int AGENT = 1;
   public static final int LOCAL = 2;
   public static final int FIXED = 3;
   private int _access = 1;
   private boolean a = false;
   private byte[] b = new byte[]{0, 10};
   private long c = 0L;
   private static int LEAF = 0;
   private Logger d = null;

   public SnmpMacAddressSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.d = Logger.getInstance(a("{4\u0018klI94\u007fEZ?\u0006hrA79~@N"));
   }

   public SnmpMacAddressSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.d = Logger.getInstance(a("{4\u0018klI94\u007fEZ?\u0006hrA79~@N"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      boolean var12 = SnmpSimLeaf.c;
      StringTokenizer var2 = new StringTokenizer(var1, a("\u0000sO7\u0001"), false);
      String[] var3 = new String[]{a("E;\u0016"), a("I=\u0010uU"), a("@?\r"), a("\u0018jEz")};
      int var4 = 0;

      boolean var10000;
      while(true) {
         if (var4 < var3.length) {
            var10000 = var2.hasMoreTokens();
            if (var12) {
               break;
            }

            if (var10000) {
               var3[var4] = var2.nextToken().trim();
            }

            ++var4;
            if (!var12) {
               continue;
            }
         }

         if (var3[2] != null && var3[2].length() > 0 && var3[2].equalsIgnoreCase(a("I)\u0016rH"))) {
            this.a = true;
         }

         if (var3[3] != null && var3[3].length() > 0) {
            try {
               SnmpString var16 = new SnmpString();
               var16.fromHexString(var3[3]);
               byte[] var5 = var16.getByteArray();
               if (var5.length >= 2) {
                  this.b = var5;
               }
            } catch (Exception var14) {
            }

            if (var3[3].length() == 12) {
               try {
                  this.c = Long.decode(a("\u0018\"") + var3[3]);
               } catch (Exception var13) {
               }
            }
         }

         var10000 = var3[1].equalsIgnoreCase(a("I=\u0010uU"));
         break;
      }

      if (var10000) {
         this._access = 1;
         if (!var12) {
            return;
         }
      }

      if (var3[1].equalsIgnoreCase(a("D5\u0016zM"))) {
         this._access = 2;
         if (!var12) {
            return;
         }
      }

      if (var3[1].equalsIgnoreCase(a("N3\r~E"))) {
         this._access = 1;
         if (var3[3] == null) {
            return;
         }

         this.setValue(var3[2]);
         if (!var12) {
            return;
         }
      }

      if (!var3[1].equalsIgnoreCase(a("]4\u001cjTM"))) {
         throw new SnmpValueException(a("A4\u0003zMA>Uk@Z;\u0018~UM(U<") + var3[1] + a("\u000ftU") + a("])\u0014|D\u0012zRrQI>\u0011iD[)]zFM4\u0001gGA\"\u0010\u007f\b\u000f"));
      } else {
         this._access = 3;
         long var17 = this.c + (long)(LEAF++);
         String var6 = Long.toHexString(var17);

         try {
            SnmpString var7 = new SnmpString();
            var7.fromHexString(var6);
            byte[] var8 = new byte[]{this.b[0], this.b[1], 0, 0, 0, 0};
            byte[] var9 = var7.getByteArray();
            int var10 = 4;
            if (var9.length < 4) {
               var10 = var9.length;
            }

            int var11 = 0;

            SnmpString var18;
            while(true) {
               if (var11 < var10) {
                  var8[5 - var11] = var9[var9.length - var11 - 1];
                  ++var11;
                  if (!var12 || !var12) {
                     continue;
                  }
               } else if (this.a) {
                  var18 = new SnmpString(ByteFormatter.toHexString(var8, true));
                  break;
               }

               var18 = new SnmpString(var8);
               break;
            }

            this.setValue(var18);
         } catch (Exception var15) {
         }

         if (var12) {
            throw new SnmpValueException(a("A4\u0003zMA>Uk@Z;\u0018~UM(U<") + var3[1] + a("\u000ftU") + a("])\u0014|D\u0012zRrQI>\u0011iD[)]zFM4\u0001gGA\"\u0010\u007f\b\u000f"));
         }
      }
   }

   public void setIpSource(int var1) {
      this._access = var1;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      boolean var5 = SnmpSimLeaf.c;
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("O?\u0001IDY/\u0010hU\u0000*\u001c7") + var2 + "," + var3 + ")");
      }

      SnmpValue var4;
      if (this._access == 1) {
         label39: {
            var4 = this.getProviderMac(var1);
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
            var4 = this.getLocalMac(var1);
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
      if (this.d.isDebugEnabled()) {
         this.d.debug(a("O?\u0001UDP.'~P]?\u0006o\tX3Y") + var2 + "," + var3 + ")");
      }

      SnmpValue var4;
      if (this._access == 1) {
         label39: {
            var4 = this.getProviderMac(var1);
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
            var4 = this.getLocalMac(var1);
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

   public SnmpValue getLocalMac(SnmpPendingIndication var1) {
      try {
         InetAddress var2 = InetAddress.getLocalHost();
         byte[] var3 = new byte[]{this.b[0], this.b[1], var2.getAddress()[0], var2.getAddress()[1], var2.getAddress()[2], var2.getAddress()[3]};
         return this.a ? new SnmpString(ByteFormatter.toHexString(var3, true)) : new SnmpString(var3);
      } catch (Exception var4) {
         this.d.debug(a("\bwX;DZ(\u001ai\u001b\b9\u0014uOG.U|DF?\u0007zUMz\u0019tBI6URq"), var4);
         return null;
      }
   }

   public SnmpValue getProviderMac(SnmpPendingIndication var1) {
      try {
         TransportEntity var2 = var1.getSource();
         if (var2 != null) {
            TransportProvider var3 = var2.getProvider();
            if (var3 instanceof UdpTransportProvider) {
               UdpTransportProvider var4 = (UdpTransportProvider)var3;
               InetAddress var5 = var4.getAddress();
               if (var5 != null) {
                  byte[] var6 = var5.getAddress();
                  byte[] var7 = new byte[]{this.b[0], this.b[1], var5.getAddress()[0], var5.getAddress()[1], var5.getAddress()[2], var5.getAddress()[3]};
                  if (this.a) {
                     return new SnmpString(ByteFormatter.toHexString(var7, true));
                  }

                  return new SnmpString(var7);
               }
            }
         }

         this.d.debug(a("\bwX;DZ(\u001ai\u001b\b9\u0014uOG.U|D\\z\u0019tBI6URq\b;\u0011\u007fSM)\u0006"));
         return new SnmpIpAddress(InetAddress.getLocalHost());
      } catch (Exception var8) {
         this.d.debug(a("\bwX;DZ(\u001ai\u001b\b9\u0014uOG.U|DF?\u0007zUMz\u0019tBI6URq"), var8);
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
               var10003 = 40;
               break;
            case 1:
               var10003 = 90;
               break;
            case 2:
               var10003 = 117;
               break;
            case 3:
               var10003 = 27;
               break;
            default:
               var10003 = 33;
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
         SnmpMacAddressSimLeaf var4 = new SnmpMacAddressSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
