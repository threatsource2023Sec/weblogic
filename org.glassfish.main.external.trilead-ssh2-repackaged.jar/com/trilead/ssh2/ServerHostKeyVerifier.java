package com.trilead.ssh2;

public interface ServerHostKeyVerifier {
   boolean verifyServerHostKey(String var1, int var2, String var3, byte[] var4) throws Exception;
}
