package com.netscape.sasl;

import java.util.Hashtable;
import javax.security.auth.callback.CallbackHandler;

public class ClientFactory implements SaslClientFactory {
   private final String PACKAGENAME = "com.netscape.sasl.mechanisms";
   private final String[] _mechanismNames = new String[]{"EXTERNAL"};
   private final String[] _mechanismClasses = new String[]{"SaslExternal"};
   private Hashtable _mechanismTable = new Hashtable();

   public ClientFactory() {
      for(int var1 = 0; var1 < this._mechanismNames.length; ++var1) {
         this._mechanismTable.put(this._mechanismNames[var1].toLowerCase(), "com.netscape.sasl.mechanisms." + this._mechanismClasses[var1]);
      }

   }

   public SaslClient createSaslClient(String[] var1, String var2, String var3, String var4, Hashtable var5, CallbackHandler var6) throws SaslException {
      String var7 = null;

      for(int var8 = 0; var7 == null && var8 < var1.length; ++var8) {
         var7 = (String)this._mechanismTable.get(var1[var8].toLowerCase());
      }

      if (var7 != null) {
         try {
            Class var11 = Class.forName(var7);
            SaslClient var9 = (SaslClient)var11.newInstance();
            return var9;
         } catch (Exception var10) {
            System.err.println("ClientFactory.createSaslClient: " + var10);
         }
      }

      return null;
   }

   public String[] getMechanismNames() {
      return this._mechanismNames;
   }
}
