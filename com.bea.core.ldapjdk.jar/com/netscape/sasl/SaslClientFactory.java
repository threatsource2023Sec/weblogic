package com.netscape.sasl;

import java.util.Hashtable;
import javax.security.auth.callback.CallbackHandler;

public interface SaslClientFactory {
   SaslClient createSaslClient(String[] var1, String var2, String var3, String var4, Hashtable var5, CallbackHandler var6) throws SaslException;

   String[] getMechanismNames();
}
