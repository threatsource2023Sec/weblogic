package weblogic.common;

public interface ProxyAuthenticator {
   String AUTHENTICATOR_PROPERTY = "weblogic.net.proxyAuthenticatorClassName";

   void init(String var1, int var2, String var3, String var4);

   String[] getLoginAndPassword();
}
