package monfox.toolkit.snmp.agent.sim;

import java.util.StringTokenizer;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;

public class SnmpPropSimLeaf extends SnmpSimLeaf {
   private Object a = new Object();
   private Logger b = null;
   private String c = "";
   private String d = "";

   public SnmpPropSimLeaf(SnmpOid var1) throws SnmpMibException, SnmpValueException {
      super(var1);
      this.b = Logger.getInstance(a("\u0016!\u007f\td7 b*](\u0003w\u0018R"));
   }

   public SnmpPropSimLeaf(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
      this.b = Logger.getInstance(a("\u0016!\u007f\td7 b*](\u0003w\u0018R"));
   }

   public void initializeFunction(String var1) throws SnmpValueException {
      boolean var5 = SnmpSimLeaf.c;
      StringTokenizer var2 = new StringTokenizer(var1, a("mf(U\u0014"), false);
      String[] var3 = new String[]{a("5=}\t"), "", ""};
      int var4 = 0;

      int var10000;
      while(true) {
         if (var4 < var3.length) {
            var10000 = var2.hasMoreTokens();
            if (var5) {
               break;
            }

            if (var10000 != 0) {
               var3[var4] = var2.nextToken().toLowerCase().trim();
            }

            ++var4;
            if (!var5) {
               continue;
            }
         }

         this.c = var3[1];
         this.d = var3[2];
         var10000 = this.c.length();
         break;
      }

      if (var10000 == 0) {
         throw new SnmpValueException(a(",!d\u0018X,+2\tF*??\u0017U(*2Q") + this.c + ")");
      }
   }

   public SnmpValue getValue() {
      String var1;
      Exception var9;
      label44: {
         int var10000;
         boolean var10001;
         label43: {
            boolean var5 = SnmpSimLeaf.c;
            var1 = System.getProperty(this.c, this.d);
            this.b.debug(a("hb2\u000bQ6:~\ro") + this.c + a("\u0018u") + var1);
            StringBuffer var2 = new StringBuffer();
            switch (this.getType()) {
               case 4:
               case 5:
               case 6:
               case 64:
               case 68:
                  if (!var5) {
                     break;
                  }
               default:
                  int var3 = 0;

                  while(var3 < var1.length()) {
                     char var4 = var1.charAt(var3);
                     var10000 = Character.isDigit(var4);
                     if (var5) {
                        break label43;
                     }

                     if (var10000 == 0 && var4 != '-' && var4 != '+') {
                        break;
                     }

                     var2.append(var4);
                     ++var3;
                     if (var5) {
                        break;
                     }
                  }

                  var1 = var2.toString();
            }

            try {
               var10000 = this.getType();
            } catch (Exception var7) {
               var9 = var7;
               var10001 = false;
               break label44;
            }
         }

         try {
            return SnmpValue.getInstance(var10000, var1);
         } catch (Exception var6) {
            var9 = var6;
            var10001 = false;
         }
      }

      Exception var8 = var9;
      this.b.debug(a(" 7q\u001cD1&}\u0017\u0014*!2\u000fU):wY[#ob\u000b[5*`\rMeh") + this.c + a("ba2Q[0;b\f@\u007fo") + var1 + ")", var8);
      return null;
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
               var10003 = 69;
               break;
            case 1:
               var10003 = 79;
               break;
            case 2:
               var10003 = 18;
               break;
            case 3:
               var10003 = 121;
               break;
            default:
               var10003 = 52;
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
         SnmpPropSimLeaf var4 = new SnmpPropSimLeaf(var2, var3);
         if (this.a != null) {
            var4.initializeFunction(this.a);
         }

         return var4;
      }
   }
}
