package weblogic.security.providers.authentication;

import java.security.interfaces.RSAPublicKey;

public interface IDCSKeyManager {
   long getTimestamp();

   RSAPublicKey getRSAPublicKey(String var1) throws IllegalArgumentException;
}
