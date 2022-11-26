package com.netscape.sasl;

import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.security.auth.callback.CallbackHandler;

public class Sasl {
   private static SaslClientFactory clientFactory = null;
   static final boolean debug = false;
   public static final String CLIENTPKGS = "javax.security.sasl.client.pkgs";

   private Sasl() {
   }

   public static SaslClient createSaslClient(String[] var0, String var1, String var2, String var3, Hashtable var4, CallbackHandler var5) throws SaslException {
      SaslClient var6 = null;
      if (clientFactory != null) {
         var6 = clientFactory.createSaslClient(var0, var1, var2, var3, var4, var5);
      }

      if (var6 == null) {
         String var7 = var4 == null ? null : (String)var4.get("javax.security.sasl.client.pkgs");
         if (var7 != null) {
            var6 = loadFromPkgList(var7, var0, var1, var2, var3, var4, var5);
         }

         if (var6 == null && (var7 = System.getProperty("javax.security.sasl.client.pkgs")) != null) {
            var6 = loadFromPkgList(var7, var0, var1, var2, var3, var4, var5);
         }
      }

      return var6;
   }

   private static SaslClient loadFromPkgList(String var0, String[] var1, String var2, String var3, String var4, Hashtable var5, CallbackHandler var6) throws SaslException {
      StringTokenizer var7 = new StringTokenizer(var0, "|");
      SaslClient var8 = null;
      SaslClientFactory var9 = null;

      while(var8 == null && var7.hasMoreTokens()) {
         String var10 = var7.nextToken().trim();
         String var11 = var10 + ".ClientFactory";
         Class var12 = null;

         try {
            var12 = Class.forName(var11);
         } catch (Exception var16) {
            System.err.println("Sasl.loadFromPkgList: " + var16);
         }

         if (var12 != null) {
            try {
               var9 = (SaslClientFactory)var12.newInstance();
            } catch (InstantiationException var14) {
               throw new SaslException("Cannot instantiate " + var11);
            } catch (IllegalAccessException var15) {
               throw new SaslException("Cannot access constructor of " + var11);
            }

            var8 = var9.createSaslClient(var1, var2, var3, var4, var5, var6);
         }
      }

      return var8;
   }

   public static void setSaslClientFactory(SaslClientFactory var0) {
      if (clientFactory != null) {
         throw new IllegalStateException("SaslClientFactory already defined");
      } else {
         SecurityManager var1 = System.getSecurityManager();
         if (var1 != null) {
            var1.checkSetFactory();
         }

         clientFactory = var0;
      }
   }
}
