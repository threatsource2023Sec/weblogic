package weblogic.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.utils.Debug;

public class ProtocolManager {
   private static Protocol[] configuredProtocols = new Protocol[256];
   private static HashMap namedProtocols = new HashMap();

   private static ServerChannel[] createDefaultChannels() {
      ArrayList channels = new ArrayList();

      for(int i = 0; i < 256; ++i) {
         Protocol p = configuredProtocols[i];
         if (p != null && !p.isUnknown() && p.isEnabled() && p != ProtocolHandlerAdmin.PROTOCOL_ADMIN && p.getHandler().getPriority() >= 0 && p.getHandler().getDefaultServerChannel() != null) {
            channels.add(p.getHandler().getDefaultServerChannel());
         }
      }

      return (ServerChannel[])channels.toArray(new ServerChannel[channels.size()]);
   }

   public static synchronized Iterator iterator() {
      return namedProtocols.values().iterator();
   }

   public static synchronized String[] getProtocols() {
      Set s = ((HashMap)namedProtocols.clone()).keySet();
      s.remove("unknown");
      return (String[])s.toArray(new String[s.size()]);
   }

   public static Protocol createProtocol(String name, String urlPrefix, boolean secure, ProtocolHandler handler) {
      return new ProtocolImpl(name, urlPrefix, secure, handler);
   }

   public static Protocol createProtocol(byte pn, String name, String urlPrefix, boolean secure, ProtocolHandler handler) {
      return new ProtocolImpl(pn, name, urlPrefix, secure, handler);
   }

   public static synchronized void registerProtocol(Protocol protocol) {
      Object obj = namedProtocols.get(protocol.getProtocolName().toLowerCase());
      if (obj != null) {
         throw new AssertionError("Tried to register protocol " + protocol + " but " + obj + "is already registered");
      } else {
         namedProtocols.put(protocol.getProtocolName().toLowerCase(), protocol);
         configuredProtocols[protocol.toByte()] = protocol;
      }
   }

   public static ServerChannel[] getDefaultServerChannels() {
      return ProtocolManager.SingletonChannelMaker.DEFAULT_CHANNELS;
   }

   public static Protocol getProtocolByName(String protocolName) {
      Debug.assertion(protocolName != null);
      Protocol p = (Protocol)namedProtocols.get(protocolName.toLowerCase());
      return p == null ? ProtocolImpl.PROTOCOL_UNKNOWN : p;
   }

   public static Protocol getRealProtocol(Protocol protocol) {
      Debug.assertion(protocol != null);
      return protocol.getQOS() == 103 ? getDefaultAdminProtocol() : protocol;
   }

   public static Protocol findProtocol(String protocolName) throws UnknownProtocolException {
      Protocol p = (Protocol)namedProtocols.get(protocolName.toLowerCase());
      if (p == null) {
         throw new UnknownProtocolException(protocolName);
      } else {
         return p;
      }
   }

   public static Protocol getProtocolByIndex(int i) {
      return configuredProtocols[i] == null ? ProtocolImpl.PROTOCOL_UNKNOWN : configuredProtocols[i];
   }

   public static Protocol getDefaultProtocol() {
      return ProtocolManager.DefaultProtocolMaker.PROTOCOL;
   }

   public static Protocol getDefaultSecureProtocol() {
      return ProtocolManager.DefaultSecureProtocolMaker.PROTOCOL;
   }

   public static Protocol getDefaultAdminProtocol() {
      return ProtocolManager.DefaultAdminProtocolMaker.PROTOCOL;
   }

   public static Protocol getProtocol(byte QOS) {
      switch (QOS) {
         case 101:
            return getDefaultProtocol();
         case 102:
            return getDefaultSecureProtocol();
         case 103:
            return getDefaultAdminProtocol();
         default:
            throw new IllegalArgumentException("Unknown QOS: '" + QOS + "'");
      }
   }

   private static final class DefaultAdminProtocolMaker {
      private static final Protocol PROTOCOL;

      static {
         RJVMEnvironment.getEnvironment().ensureInitialized();
         PROTOCOL = ProtocolManager.getProtocolByName(RJVMEnvironment.getEnvironment().getAdminProtocolName());
      }
   }

   private static final class DefaultSecureProtocolMaker {
      private static final Protocol PROTOCOL;

      static {
         RJVMEnvironment.getEnvironment().ensureInitialized();
         PROTOCOL = ProtocolManager.getProtocolByName(RJVMEnvironment.getEnvironment().getDefaultSecureProtocolName());
      }
   }

   private static final class DefaultProtocolMaker {
      private static final Protocol PROTOCOL;

      static {
         RJVMEnvironment.getEnvironment().ensureInitialized();
         PROTOCOL = ProtocolManager.getProtocolByName(RJVMEnvironment.getEnvironment().getDefaultProtocolName());
      }
   }

   private static final class SingletonChannelMaker {
      private static final ServerChannel[] DEFAULT_CHANNELS;

      static {
         RJVMEnvironment.getEnvironment().ensureInitialized();
         DEFAULT_CHANNELS = ProtocolManager.createDefaultChannels();
      }
   }
}
