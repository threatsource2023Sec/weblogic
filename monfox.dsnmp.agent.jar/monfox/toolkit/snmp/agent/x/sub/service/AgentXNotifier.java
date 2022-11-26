package monfox.toolkit.snmp.agent.x.sub.service;

import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.notify.SnmpNotifier;
import monfox.toolkit.snmp.util.Queue;
import monfox.toolkit.snmp.util.WorkItem;

public class AgentXNotifier {
   private Queue a;
   private SubAgentXApi b;
   private SnmpAgent c;
   private SnmpNotifier.Listener d;
   private Logger e = Logger.getInstance(a("\u0013Q}%F"), a("\u0016Ev&BzZ"), a("\u0016eV\u0006b\u000fL\\\u001c\u007f1kV\u001a"));

   public AgentXNotifier(SubAgentXApi var1, SnmpAgent var2) {
      this.b = var1;
      this.c = var2;
      this.d = new NotifyListener();
      this.c.getNotifier().addListener(this.d);
      this.a = new Queue(a("\u0016eV\u0006b\u000fL\\\u001c\u007f1{b\u001ds\"g"), 1, 5);
   }

   public void shutdown() {
      if (this.a != null) {
         this.a.shutdown();
         this.a = null;
         this.c = null;
         this.b = null;
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
               var10003 = 87;
               break;
            case 1:
               var10003 = 2;
               break;
            case 2:
               var10003 = 51;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 22;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class NotifyWorker extends WorkItem {
      String a;
      String b;
      SnmpVarBindList c;

      NotifyWorker(String var2, String var3, SnmpVarBindList var4) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }

      public void perform() {
         AgentXNotifier.this.e.debug(a("\bb\r s?Z\u0016;~#\u007fY9g)n\u001c:f/c\u001ei[)y\u0010/|%l\r z(7Yar4b\f9(") + this.a + a("jn\u0016'a#u\rt") + this.b + a("o7s") + this.c);
         AgentXNotifier.this.b.sendNotification(this.a, this.b, this.c);
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 70;
                  break;
               case 1:
                  var10003 = 13;
                  break;
               case 2:
                  var10003 = 121;
                  break;
               case 3:
                  var10003 = 73;
                  break;
               default:
                  var10003 = 21;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   private class NotifyListener implements SnmpNotifier.Listener {
      private NotifyListener() {
      }

      public void handleNotification(String var1, SnmpOid var2, SnmpVarBindList var3, String var4) {
         AgentXNotifier.this.a.put(AgentXNotifier.this.new NotifyWorker(var1, var4, var3));
      }

      // $FF: synthetic method
      NotifyListener(Object var2) {
         this();
      }
   }
}
