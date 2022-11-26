package monfox.toolkit.snmp.v3.usm;

public abstract class USMAuthProtocolSpec {
   private static USMAuthProtocolSpec[] a = new USMAuthProtocolSpec[0];

   public abstract int getDigestLength();

   public abstract int getAuthProtocol();

   public abstract USMAuthModule newModule();

   public static synchronized void addSpec(USMAuthProtocolSpec var0) {
      boolean var3 = USMLocalizedUserData.k;
      USMAuthProtocolSpec[] var1 = new USMAuthProtocolSpec[a.length + 1];
      int var2 = 0;

      while(true) {
         if (var2 < a.length) {
            var1[var2] = a[var2];
            ++var2;
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         var1[var1.length - 1] = var0;
         a = var1;
         break;
      }

   }

   public static synchronized USMAuthProtocolSpec getSpec(int var0) {
      int var1 = 0;

      while(var1 < a.length) {
         if (a[var1].getAuthProtocol() == var0) {
            return a[var1];
         }

         ++var1;
         if (USMLocalizedUserData.k) {
            break;
         }
      }

      return null;
   }

   public static USMAuthProtocolSpec[] getSpecs() {
      return a;
   }
}
