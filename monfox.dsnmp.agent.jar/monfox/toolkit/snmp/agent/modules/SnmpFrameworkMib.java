package monfox.toolkit.snmp.agent.modules;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.engine.SnmpEngine;

public class SnmpFrameworkMib {
   private SnmpEngine a;
   private Logger b = Logger.getInstance(a("NKdle_Vwj"));
   private static final SnmpOid c = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 2L, 1L, 1L});
   private static final SnmpOid d = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 2L, 1L, 2L});
   private static final SnmpOid e = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 2L, 1L, 3L});
   private static final SnmpOid f = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 2L, 1L, 4L});
   public static int g;

   public SnmpFrameworkMib(SnmpAgent var1) {
      this.a = var1.getEngine();

      try {
         SnmpMib var2 = var1.getMib();
         var2.add(new SnmpEngineID());
         var2.add(new SnmpEngineBoots());
         var2.add(new SnmpEngineTime());
         var2.add(new SnmpEngineMaxMessageSize());
      } catch (SnmpException var3) {
         this.b.error(a("mkWNR({PHLlpKF\u0000[Whq\rNKdle_Vwj\rEPg"), var3);
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
               var10003 = 8;
               break;
            case 1:
               var10003 = 25;
               break;
            case 2:
               var10003 = 37;
               break;
            case 3:
               var10003 = 33;
               break;
            default:
               var10003 = 32;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class SnmpEngineMaxMessageSize extends SnmpMibLeaf {
      public SnmpEngineMaxMessageSize() throws SnmpMibException {
         super(SnmpFrameworkMib.f);
      }

      public SnmpValue getValue() {
         return new SnmpInt(SnmpFrameworkMib.this.a.getMaxSize());
      }
   }

   class SnmpEngineTime extends SnmpMibLeaf {
      public SnmpEngineTime() throws SnmpMibException {
         super(SnmpFrameworkMib.e);
      }

      public SnmpValue getValue() {
         return new SnmpInt(SnmpFrameworkMib.this.a.getEngineTime());
      }
   }

   class SnmpEngineBoots extends SnmpMibLeaf {
      public SnmpEngineBoots() throws SnmpMibException {
         super(SnmpFrameworkMib.d);
      }

      public SnmpValue getValue() {
         return new SnmpInt(SnmpFrameworkMib.this.a.getEngineBoots());
      }
   }

   class SnmpEngineID extends SnmpMibLeaf {
      public SnmpEngineID() throws SnmpMibException {
         super(SnmpFrameworkMib.c);
      }

      public SnmpValue getValue() {
         return new SnmpString(SnmpFrameworkMib.this.a.getEngineID().toByteArray());
      }
   }
}
