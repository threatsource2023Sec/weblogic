package monfox.toolkit.snmp.util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class BeanLoader {
   private static final String a = "$Id: BeanLoader.java,v 1.4 2003/02/25 22:17:14 sking Exp $";

   public static void main(String[] var0) {
      int var1 = 0;

      while(var1 < var0.length) {
         try {
            FileInputStream var2 = new FileInputStream(var0[var1]);
            ObjectInputStream var3 = new ObjectInputStream(var2);
            long var4 = System.currentTimeMillis();
            Object var6 = var3.readObject();
            long var7 = System.currentTimeMillis();
            System.out.println(a("\bi9v-q%") + var6.getClass().getName());
            System.out.println(var6);
            System.out.println(a("\u0007j9a~\u001fl5`dk") + (var7 - var4) + a("kh1i2\"vv"));
         } catch (Exception var9) {
            System.out.println(var9);
         }

         ++var1;
         if (WorkItem.d != 0) {
            break;
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
               var10003 = 75;
               break;
            case 1:
               var10003 = 5;
               break;
            case 2:
               var10003 = 88;
               break;
            case 3:
               var10003 = 5;
               break;
            default:
               var10003 = 94;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
