package weblogic.iiop.ior;

enum CipherSuites {
   NONE {
      short getTlsSupports() {
         return 2;
      }

      CipherSuites transitionOnPlain() {
         return PLAIN_ONLY;
      }

      CipherSuites transitionOnSecure() {
         return SECURE_ONLY;
      }
   },
   PLAIN_ONLY {
      short getTlsSupports() {
         return 2;
      }

      CipherSuites transitionOnSecure() {
         return BOTH;
      }
   },
   SECURE_ONLY {
      short getTlsRequires() {
         return 6;
      }

      CipherSuites transitionOnPlain() {
         return BOTH;
      }
   },
   BOTH;

   private CipherSuites() {
   }

   static CipherSuites getFromSuiteNames(String... suiteNames) {
      if (suiteNames != null && suiteNames.length != 0) {
         CipherSuites result = NONE;
         String[] var2 = suiteNames;
         int var3 = suiteNames.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String suiteName = var2[var4];
            if (suiteName != null) {
               if (isSecure(suiteName)) {
                  result = result.transitionOnSecure();
               } else {
                  result = result.transitionOnPlain();
               }
            }
         }

         return result;
      } else {
         return SECURE_ONLY;
      }
   }

   private static boolean isSecure(String suiteName) {
      return !suiteName.contains("WITH_NULL");
   }

   short getTlsSupports() {
      return 38;
   }

   short getTlsRequires() {
      return 2;
   }

   CipherSuites transitionOnSecure() {
      return this;
   }

   CipherSuites transitionOnPlain() {
      return this;
   }

   // $FF: synthetic method
   CipherSuites(Object x2) {
      this();
   }
}
