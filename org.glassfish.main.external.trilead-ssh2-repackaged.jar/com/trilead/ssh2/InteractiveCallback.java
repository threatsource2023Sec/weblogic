package com.trilead.ssh2;

public interface InteractiveCallback {
   String[] replyToChallenge(String var1, String var2, int var3, String[] var4, boolean[] var5) throws Exception;
}
