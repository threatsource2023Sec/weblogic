package monfox.toolkit.snmp.agent;

import java.io.BufferedInputStream;
import java.io.IOException;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpMibCommand extends SnmpMibLeaf {
   private String a;

   public SnmpMibCommand(SnmpMetadata var1, String var2, String var3) throws SnmpValueException, SnmpMibException {
      super(var1, var2);
      this.a = var3;
   }

   public SnmpMibCommand(String var1, String var2) throws SnmpValueException, SnmpMibException {
      super(var1);
      this.a = var2;
   }

   public SnmpMibCommand(SnmpOid var1, String var2) throws SnmpMibException {
      super(var1);
      this.a = var2;
   }

   public SnmpValue getValue() {
      Runtime var1 = Runtime.getRuntime();

      Process var2;
      try {
         var2 = var1.exec(this.a);
      } catch (IOException var7) {
         return null;
      }

      BufferedInputStream var3 = new BufferedInputStream(var2.getInputStream());
      StringBuffer var5 = new StringBuffer();

      do {
         int var4;
         try {
            var4 = var3.read();
         } catch (IOException var8) {
            break;
         }

         if (var4 == -1) {
            break;
         }

         var5.append((char)var4);
      } while(!SnmpMibNode.b);

      return this.parse(var5.toString());
   }

   public SnmpValue parse(String var1) {
      try {
         int var2 = this.getType();
         return (SnmpValue)(var2 == -1 ? new SnmpString(var1) : SnmpValue.getInstance(var2, var1));
      } catch (SnmpValueException var3) {
         return SnmpNull.noSuchInstance;
      }
   }

   public int getAccess() {
      return 1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a("I\u0012\u0013=gD\u0019C"));
      var1.append('{');
      var1.append(a("E\u0014\u001am"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("I\u0011\u001f#ue\u0014\u001am"));
      var1.append(this.getClassOid());
      var1.append(',');
      var1.append(a("M\u0018\n\u0013iG\u0010\u001f>b\u0017"));
      var1.append(this.a);
      var1.append('}');
      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 42;
               break;
            case 1:
               var10003 = 125;
               break;
            case 2:
               var10003 = 126;
               break;
            case 3:
               var10003 = 80;
               break;
            default:
               var10003 = 6;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
