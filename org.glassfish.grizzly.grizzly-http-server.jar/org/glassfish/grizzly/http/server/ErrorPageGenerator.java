package org.glassfish.grizzly.http.server;

public interface ErrorPageGenerator {
   String generate(Request var1, int var2, String var3, String var4, Throwable var5);
}
