package netscape.ldap;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Hashtable;
import netscape.ldap.client.opers.JDAPBindRequest;
import netscape.ldap.client.opers.JDAPBindResponse;
import netscape.ldap.client.opers.JDAPProtocolOp;

public class LDAPSaslBind implements LDAPBind, Serializable {
   static final long serialVersionUID = -7615315715163655443L;
   private static final String CALLBACK_HANDLER = "javax.security.auth.callback.CallbackHandler";
   private static final String CLIENTPKGS = "javax.security.sasl.client.pkgs";
   private String _dn;
   private String[] _mechanisms;
   private String _packageName;
   private Hashtable _props;
   private Object _cbh;
   private Object _saslClient = null;

   public LDAPSaslBind(String var1, String[] var2, String var3, Hashtable var4, Object var5) {
      this._dn = var1;
      this._mechanisms = var2;
      this._packageName = var3;
      this._props = var4;
      this._cbh = var5;
   }

   public void bind(LDAPConnection var1) throws LDAPException {
      if (this._props == null) {
         this._props = new Hashtable();
      }

      if (!this._props.containsKey("javax.security.sasl.client.pkgs") && System.getProperty("javax.security.sasl.client.pkgs") == null) {
         this._props.put("javax.security.sasl.client.pkgs", "com.netscape.sasl");
      }

      this._saslClient = this.getClient(var1, this._packageName);
      if (this._saslClient != null) {
         this.bind(var1, true);
      } else {
         LDAPConnection.printDebug("LDAPSaslBind.bind: getClient returned null");
      }
   }

   private Object getClient(LDAPConnection var1, String var2) throws LDAPException {
      try {
         Object[] var3 = new Object[]{this._mechanisms, this._dn, "ldap", var1.getHost(), this._props, this._cbh};
         String[] var4 = new String[]{"[Ljava.lang.String;", "java.lang.String", "java.lang.String", "java.lang.String", "java.util.Hashtable", "javax.security.auth.callback.CallbackHandler"};
         return DynamicInvoker.invokeMethod((Object)null, var2 + ".Sasl", "createSaslClient", var3, var4);
      } catch (Exception var5) {
         LDAPConnection.printDebug("LDAPSaslBind.getClient: " + var2 + ".Sasl.createSaslClient: " + var5);
         throw new LDAPException(var5.toString(), 80);
      }
   }

   void bind(LDAPConnection var1, boolean var2) throws LDAPException {
      if (var1.isConnected() && var2 || !var1.isConnected()) {
         try {
            String var3 = this._saslClient.getClass().getName();
            LDAPConnection.printDebug("LDAPSaslBind.bind: calling " + var3 + ".createInitialResponse");
            byte[] var4 = (byte[])((byte[])DynamicInvoker.invokeMethod(this._saslClient, var3, "createInitialResponse", (Object[])null, (String[])null));
            String var5 = (String)DynamicInvoker.invokeMethod(this._saslClient, var3, "getMechanismName", (Object[])null, (String[])null);
            LDAPConnection.printDebug("LDAPSaslBind.bind: mechanism name is " + var5);
            boolean var6 = this.isExternalMechanism(var5);
            int var7 = 14;
            JDAPBindResponse var8 = null;

            Object[] var10;
            String[] var11;
            while(!this.checkForSASLBindCompletion(var7)) {
               LDAPConnection.printDebug("LDAPSaslBind.bind: calling saslBind");
               var8 = this.saslBind(var1, var5, var4);
               var7 = var8.getResultCode();
               LDAPConnection.printDebug("LDAPSaslBind.bind: saslBind returned " + var7);
               if (!var6) {
                  byte[] var9 = var8.getCredentials();
                  var10 = new Object[]{var9};
                  var11 = new String[]{"[B"};
                  var4 = (byte[])((byte[])DynamicInvoker.invokeMethod(this._saslClient, var3, "evaluateChallenge", var10, var11));
               }
            }

            Boolean var16 = (Boolean)DynamicInvoker.invokeMethod(this._saslClient, var3, "isComplete", (Object[])null, (String[])null);
            if (!var16) {
               throw new LDAPException("The server indicates that authentication is successful, but the SASL driver indicates that authentication is not yet done.", 80);
            }

            var10 = new Object[]{var1.getInputStream()};
            var11 = new String[]{"java.io.InputStream"};
            InputStream var12 = (InputStream)DynamicInvoker.invokeMethod(this._saslClient, var3, "getInputStream", var10, var11);
            var1.setInputStream(var12);
            var10[0] = var1.getOutputStream();
            var11[0] = "java.io.OutputStream";
            OutputStream var13 = (OutputStream)DynamicInvoker.invokeMethod(this._saslClient, var3, "getOutputStream", var10, var11);
            var1.setOutputStream(var13);
            var1.setBound(true);
         } catch (LDAPException var14) {
            throw var14;
         } catch (Exception var15) {
            throw new LDAPException(var15.toString(), 80);
         }
      }

   }

   boolean isExternalMechanism(String var1) {
      return var1.equalsIgnoreCase("external");
   }

   private boolean checkForSASLBindCompletion(int var1) throws LDAPException {
      if (var1 == 0) {
         return true;
      } else if (var1 == 14) {
         return false;
      } else {
         throw new LDAPException("Authentication failed", var1);
      }
   }

   private JDAPBindResponse saslBind(LDAPConnection var1, String var2, byte[] var3) throws LDAPException {
      LDAPResponseListener var4 = var1.getResponseListener();

      JDAPBindResponse var7;
      try {
         var1.sendRequest(new JDAPBindRequest(3, this._dn, var2, var3), var4, var1.getConstraints());
         LDAPResponse var5 = var4.getResponse();
         JDAPProtocolOp var6 = var5.getProtocolOp();
         if (!(var6 instanceof JDAPBindResponse)) {
            throw new LDAPException("Unknown response from the server during SASL bind", 80);
         }

         var7 = (JDAPBindResponse)var6;
      } finally {
         var1.releaseResponseListener(var4);
      }

      return var7;
   }
}
