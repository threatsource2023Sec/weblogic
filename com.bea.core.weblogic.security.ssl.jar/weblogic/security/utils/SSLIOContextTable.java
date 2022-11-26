package weblogic.security.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import javax.net.ssl.SSLSocket;
import weblogic.security.SecurityEnvironment;

public final class SSLIOContextTable {
   private static HashMap registeredContextsStream = new HashMap();
   private static HashMap registeredContextsSocket = new HashMap();
   private static HashSet throttledSockets = new HashSet();

   public static synchronized void addContext(SSLIOContext ctx) {
      SSLSetupLogging.debug(3, "SSLIOContextTable.addContext(ctx): " + ctx.hashCode());
      registeredContextsStream.put(ctx.getRawInputStream(), ctx);
      registeredContextsSocket.put(ctx.getSSLSocket(), ctx);
   }

   public static synchronized void removeContext(SSLIOContext ctx) {
      SSLSetupLogging.debug(3, "SSLIOContextTable.removeContext(ctx): " + ctx.hashCode());
      registeredContextsStream.remove(ctx.getRawInputStream());
      registeredContextsSocket.remove(ctx.getSSLSocket());
      unregisterThrottled(ctx.getSSLSocket());
   }

   public static synchronized void removeContext(InputStream is) {
      SSLSetupLogging.debug(3, "SSLIOContextTable.removeContext(is): " + is.hashCode());
      SSLIOContext ctx = (SSLIOContext)registeredContextsStream.remove(is);
      if (ctx != null) {
         registeredContextsSocket.remove(ctx.getSSLSocket());
         unregisterThrottled(ctx.getSSLSocket());
      }

   }

   public static synchronized void removeContext(SSLSocket sock) {
      SSLSetupLogging.debug(3, "SSLIOContextTable.removeContext(sock): " + sock.hashCode());
      SSLIOContext ctx = (SSLIOContext)registeredContextsSocket.remove(sock);
      if (ctx != null) {
         registeredContextsStream.remove(ctx.getRawInputStream());
         unregisterThrottled(sock);
      }

   }

   public static synchronized SSLIOContext findContext(InputStream is) {
      SSLSetupLogging.debug(3, "SSLIOContextTable.findContext(is): " + is.hashCode());
      return (SSLIOContext)registeredContextsStream.get(is);
   }

   public static synchronized SSLIOContext findContext(SSLSocket sock) {
      SSLSetupLogging.debug(3, "SSLIOContextTable.findContext(sock): " + sock.hashCode());
      return (SSLIOContext)registeredContextsSocket.get(sock);
   }

   public static synchronized void registerForThrottling(SSLSocket sock) {
      if (!throttledSockets.contains(sock) && registeredContextsSocket.containsKey(sock)) {
         throttledSockets.add(sock);
      }

   }

   private static void unregisterThrottled(SSLSocket sock) {
      if (throttledSockets.remove(sock)) {
         SecurityEnvironment.getSecurityEnvironment().decrementOpenSocketCount(sock);
      }

   }
}
