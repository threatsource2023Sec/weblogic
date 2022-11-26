package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpNotificationGroupInfo extends SnmpMibInfo {
   static final long serialVersionUID = 5661514001023705608L;
   private SnmpNotificationInfo[] _notifications = null;
   private static final String _ident = "$Id: SnmpNotificationGroupInfo.java,v 1.9 2001/10/29 15:44:29 sking Exp $";

   public SnmpNotificationGroupInfo(SnmpNotificationInfo[] var1) {
      this._notifications = var1;
   }

   public SnmpNotificationInfo[] getNotifications() {
      return this._notifications;
   }

   public void toString(StringBuffer var1) {
      var1.append(a("`=}\u001a#g1h\u0007,a<N\u0001*{\"4\b+o?lN"));
      var1.append(this.getName());
      var1.append(a("\"<f\u0007,h!4"));
      a(var1, this._notifications);
      var1.append('}');
   }

   public void toString(TextBuffer var1) {
      var1.append((Object)this.getName()).append((Object)a(".\u001cF'\fH\u001bJ2\u0011G\u001dG^\u0002\\\u001d\\#e3r"));
      var1.append((Object)this.getOid().toNumericString());
      var1.pushIndent();
      a(var1, this._notifications);
      var1.popIndent();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 14;
               break;
            case 1:
               var10003 = 82;
               break;
            case 2:
               var10003 = 9;
               break;
            case 3:
               var10003 = 115;
               break;
            default:
               var10003 = 69;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
