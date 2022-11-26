package monfox.toolkit.snmp.v3.usm;

import javax.crypto.Cipher;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;

public abstract class USMPrivModule {
   private int a = (int)System.currentTimeMillis();
   private long b = System.currentTimeMillis();
   private Logger c = Logger.getInstance(b(">\u001drei"), b(",}\u0011}j7"), b("/\u001dqxK\u00138qG]\u000f\"Y"));

   public abstract void encryptData(byte[] var1, Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException;

   public abstract void decryptData(byte[] var1, Context var2, SnmpBuffer var3, SnmpBuffer var4, SnmpBuffer var5) throws SnmpSecurityCoderException;

   Cipher a(String var1) {
      if (this.c.isDebugEnabled()) {
         this.c.detailed(b("\u001d+HkP\n&YZ\u0003") + var1);
      }

      String var2 = SnmpFramework.getSecurityProviderName();

      try {
         Object var3 = null;
         if (var2 == null) {
            return Cipher.getInstance(var1);
         } else {
            if (this.c.isDebugEnabled()) {
               this.c.debug(b("Wc\u001c]J\u0013 [\bI\b!JA]\u001f<\u0006\b") + var2);
            }

            try {
               return Cipher.getInstance(var1, var2);
            } catch (Exception var5) {
               if (this.c.isDebugEnabled()) {
                  this.c.debug(b("9/RFV\u000enPGX\u001en\u007fAI\u0012+N\b_\b!Q\bI\b!J\u0006\u0019@n") + var2);
                  this.c.debug(b("\u000f=UF^Z*YNX\u000f\"H\bs9\u000b\u001cXK\u00158UL\\\b"));
               }

               return Cipher.getInstance(var1);
            }
         }
      } catch (Exception var6) {
         this.c.error(b("\u0019/RFV\u000en_Z\\\u001b:Y\bz\u0013>TMKZ(SZ\u0019\u000e<]FJ\u001c!NE\u0019]") + var1 + b("]nI[P\u0014)\u001cXK\u00158UL\\\bn\u001b") + var2 + "'");
         return null;
      }
   }

   byte[] a(int var1) {
      byte[] var2 = new byte[8];
      int var3 = this.a();
      int var4 = 0;
      var2[var4++] = (byte)(var1 >> 24 & 255);
      var2[var4++] = (byte)(var1 >> 16 & 255);
      var2[var4++] = (byte)(var1 >> 8 & 255);
      var2[var4++] = (byte)(var1 & 255);
      var2[var4++] = (byte)(var3 >> 24 & 255);
      var2[var4++] = (byte)(var3 >> 16 & 255);
      var2[var4++] = (byte)(var3 >> 8 & 255);
      var2[var4++] = (byte)(var3 & 255);
      return var2;
   }

   int a() {
      return this.a++;
   }

   long b() {
      return (long)(this.b++);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 122;
               break;
            case 1:
               var10003 = 78;
               break;
            case 2:
               var10003 = 60;
               break;
            case 3:
               var10003 = 40;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public static class Context {
      private long a = 0L;
      private long b = 0L;

      public Context(long var1, long var3) {
         this.a = var1;
         this.b = var3;
      }

      public void setEngineBoots(long var1) {
         this.a = var1;
      }

      public long getEngineBoots() {
         return this.a;
      }

      public long getEngineTime() {
         return this.b;
      }

      public void setEngineTime(long var1) {
         this.b = var1;
      }
   }
}
