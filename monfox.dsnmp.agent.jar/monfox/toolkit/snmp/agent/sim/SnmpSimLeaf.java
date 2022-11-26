package monfox.toolkit.snmp.agent.sim;

import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;

public abstract class SnmpSimLeaf extends SnmpMibLeaf {
   private static Map a = new Hashtable();
   private Logger b = null;
   public static boolean c;

   public SnmpSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.b = Logger.getInstance(a("K9`Q{q:ADI~"));
   }

   public SnmpSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.b = Logger.getInstance(a("K9`Q{q:ADI~"));
   }

   public static void addFactory(String var0, SimFactory var1) {
      a.put(var0, var1);
   }

   public static SnmpMibLeafFactory getFactory(String var0) throws SnmpValueException {
      StringTokenizer var1 = new StringTokenizer(var0, a("0~!\u0001"), false);
      if (var1.countTokens() == 0) {
         throw new SnmpValueException(a("u>~RAv0-G]v4yHGv"));
      } else {
         String var2 = var1.nextToken().toLowerCase();
         SimFactory var3 = (SimFactory)a.get(var2);
         if (var3 != null) {
            return var3.getFactory(var0);
         } else if (var2.equals(a("q'lELj2~R"))) {
            return SnmpIpAddressSimLeaf.getFactory(var0);
         } else if (var2.equals(a("u6n"))) {
            return SnmpMacAddressSimLeaf.getFactory(var0);
         } else if (var2.equals(a("j6cEGu"))) {
            return SnmpRandomIntSimLeaf.getFactory(var0);
         } else if (var2.equals(a("{8xO\\}%"))) {
            return SnmpCounterSimLeaf.getFactory(var0);
         } else if (var2.equals(a("l>`DZ"))) {
            return SnmpTimerSimLeaf.getFactory(var0);
         } else if (var2.equals(a("}/hB"))) {
            return SnmpExecSimLeaf.getFactory(var0);
         } else if (var2.equals(a("h%bQ"))) {
            return SnmpPropSimLeaf.getFactory(var0);
         } else {
            throw new SnmpValueException(a("m9fOGo9-G]v4yHGvm-") + var0);
         }
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
               var10003 = 24;
               break;
            case 1:
               var10003 = 87;
               break;
            case 2:
               var10003 = 13;
               break;
            case 3:
               var10003 = 33;
               break;
            default:
               var10003 = 40;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public interface SimFactory {
      SnmpMibLeafFactory getFactory(String var1) throws SnmpValueException;
   }
}
