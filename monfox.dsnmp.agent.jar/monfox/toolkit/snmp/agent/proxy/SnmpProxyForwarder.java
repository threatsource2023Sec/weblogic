package monfox.toolkit.snmp.agent.proxy;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;

public class SnmpProxyForwarder {
   private SnmpAgent a;
   private SnmpProxyTable b;
   private SnmpProxySubtreeTable c;
   private boolean d = false;
   private boolean e = false;
   private Logger f = Logger.getInstance(a("Al/jZ"));
   public static int g;

   public SnmpProxyForwarder(SnmpAgent var1) throws SnmpException {
      if (var1.getProxyForwarder() != null) {
         throw new SnmpException(a("aL\u000fJz1X\u000f@tpL\u0004Wq1_\f@fpZ\u0019\u0012beJ\u0001QktZ@Fl1J\b[p1_\u0007Wme"));
      } else {
         this.a = var1;
         SnmpMib var2 = var1.getMib();
         this.b = new SnmpProxyTable(var1, var2.getMetadata());
         var2.add(this.b, true);
         this.c = new SnmpProxySubtreeTable(var1, var2.getMetadata(), this);
         var2.add(this.c, true);
      }
   }

   public SnmpProxyTable getProxyTable() {
      return this.b;
   }

   public SnmpProxySubtreeTable getProxySubtreeTable() {
      return this.c;
   }

   public boolean isCommunityBasedForwardingEnabled() {
      return this.d;
   }

   public void isCommunityBasedForwardingEnabled(boolean var1) {
      this.d = var1;
   }

   public boolean isSubtreeProxyBypassModeEnabled() {
      return this.e;
   }

   public void isSubtreeProxyBypassModeEnabled(boolean var1) {
      this.e = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 17;
               break;
            case 1:
               var10003 = 62;
               break;
            case 2:
               var10003 = 96;
               break;
            case 3:
               var10003 = 50;
               break;
            default:
               var10003 = 3;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
