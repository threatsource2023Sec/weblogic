package monfox.toolkit.snmp.ext;

import java.io.Serializable;
import monfox.toolkit.snmp.mgr.SnmpSession;

public class SnmpPollGroup implements SnmpPollable, Serializable {
   private String a = a("Q-\u000b\u0003xz");
   private SnmpPollable b = null;
   private static final String c = "$Id: SnmpPollGroup.java,v 1.5 2002/02/04 17:13:42 sking Exp $";

   public SnmpPollGroup(String var1) {
      this.a = var1;
   }

   public String getName() {
      return this.a;
   }

   public void add(SnmpPollable var1) {
      if (var1 != this) {
         this.b = d.add(this.b, var1);
      }
   }

   public void remove(SnmpPollable var1) {
      this.b = d.remove(this.b, var1);
   }

   public boolean contains(SnmpPollable var1) {
      return d.contains(this.b, var1);
   }

   public void perform(SnmpSession var1) {
      if (this.b != null) {
         this.b.perform(var1);
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
               var10003 = 31;
               break;
            case 1:
               var10003 = 66;
               break;
            case 2:
               var10003 = 69;
               break;
            case 3:
               var10003 = 98;
               break;
            default:
               var10003 = 21;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
