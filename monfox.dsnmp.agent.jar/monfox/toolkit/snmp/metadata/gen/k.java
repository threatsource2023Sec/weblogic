package monfox.toolkit.snmp.metadata.gen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import monfox.jdom.Element;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataLoader;

class k {
   static void a(Element var0, String var1, boolean var2) throws IOException, Exception {
      SnmpMetadata var3 = new SnmpMetadata();
      SnmpMetadataLoader var4 = new SnmpMetadataLoader();

      try {
         var4.process(var0, var3, var2);
      } catch (Exception var7) {
         System.out.println(a("GC\u007fz\u0011\u001f<\r") + var7.getMessage());
      }

      var3.getOidTree().optimize(1);
      if (var1 == null) {
         System.out.println(a("G+\u0000\u0005s`+\u0000\u0005s`+\rl\u000b\u0000Vdf\u0019mKh|\u001f\tGyi~`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005T"));
         System.out.println(var3);
         System.out.println(a("`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005s`+\u0000\u0005s`+\u0000\""));
         if (Message.d == 0) {
            return;
         }
      }

      FileOutputStream var5 = new FileOutputStream(var1);
      ObjectOutputStream var6 = new ObjectOutputStream(var5);
      var6.writeObject(var3);
      var5.close();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 77;
               break;
            case 1:
               var10003 = 6;
               break;
            case 2:
               var10003 = 45;
               break;
            case 3:
               var10003 = 40;
               break;
            default:
               var10003 = 94;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
