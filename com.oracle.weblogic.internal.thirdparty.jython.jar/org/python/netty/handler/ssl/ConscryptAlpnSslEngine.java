package org.python.netty.handler.ssl;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import org.conscrypt.HandshakeListener;
import org.conscrypt.Conscrypt.Engines;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

abstract class ConscryptAlpnSslEngine extends JdkSslEngine {
   private static final Class ENGINES_CLASS = getEnginesClass();

   static boolean isAvailable() {
      return ENGINES_CLASS != null && PlatformDependent.javaVersion() >= 8;
   }

   static boolean isEngineSupported(SSLEngine engine) {
      return isAvailable() && isConscryptEngine(engine, ENGINES_CLASS);
   }

   static ConscryptAlpnSslEngine newClientEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
      return new ClientEngine(engine, applicationNegotiator);
   }

   static ConscryptAlpnSslEngine newServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
      return new ServerEngine(engine, applicationNegotiator);
   }

   private ConscryptAlpnSslEngine(SSLEngine engine, List protocols) {
      super(engine);
      Engines.setAlpnProtocols(engine, (String[])protocols.toArray(new String[protocols.size()]));
   }

   final int calculateOutNetBufSize(int plaintextBytes, int numBuffers) {
      long maxOverhead = (long)Engines.maxSealOverhead(this.getWrappedEngine()) * (long)numBuffers;
      return (int)Math.min(2147483647L, (long)plaintextBytes + maxOverhead);
   }

   final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dests) throws SSLException {
      return Engines.unwrap(this.getWrappedEngine(), srcs, dests);
   }

   private static Class getEnginesClass() {
      try {
         Class engineClass = Class.forName("org.conscrypt.Conscrypt$Engines", true, ConscryptAlpnSslEngine.class.getClassLoader());
         getIsConscryptMethod(engineClass);
         return engineClass;
      } catch (Throwable var1) {
         return null;
      }
   }

   private static boolean isConscryptEngine(SSLEngine engine, Class enginesClass) {
      try {
         Method method = getIsConscryptMethod(enginesClass);
         return (Boolean)method.invoke((Object)null, engine);
      } catch (Throwable var3) {
         return false;
      }
   }

   private static Method getIsConscryptMethod(Class enginesClass) throws NoSuchMethodException {
      return enginesClass.getMethod("isConscrypt", SSLEngine.class);
   }

   // $FF: synthetic method
   ConscryptAlpnSslEngine(SSLEngine x0, List x1, Object x2) {
      this(x0, x1);
   }

   private static final class ServerEngine extends ConscryptAlpnSslEngine {
      private final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector;

      ServerEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
         super(engine, applicationNegotiator.protocols(), null);
         Engines.setHandshakeListener(engine, new HandshakeListener() {
            public void onHandshakeFinished() throws SSLException {
               ServerEngine.this.selectProtocol();
            }
         });
         this.protocolSelector = (JdkApplicationProtocolNegotiator.ProtocolSelector)ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector");
      }

      private void selectProtocol() throws SSLException {
         try {
            String protocol = Engines.getAlpnSelectedProtocol(this.getWrappedEngine());
            this.protocolSelector.select(protocol != null ? Collections.singletonList(protocol) : Collections.emptyList());
         } catch (Throwable var2) {
            throw SslUtils.toSSLHandshakeException(var2);
         }
      }
   }

   private static final class ClientEngine extends ConscryptAlpnSslEngine {
      private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener;

      ClientEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator) {
         super(engine, applicationNegotiator.protocols(), null);
         Engines.setHandshakeListener(engine, new HandshakeListener() {
            public void onHandshakeFinished() throws SSLException {
               ClientEngine.this.selectProtocol();
            }
         });
         this.protocolListener = (JdkApplicationProtocolNegotiator.ProtocolSelectionListener)ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
      }

      private void selectProtocol() throws SSLException {
         String protocol = Engines.getAlpnSelectedProtocol(this.getWrappedEngine());

         try {
            this.protocolListener.selected(protocol);
         } catch (Throwable var3) {
            throw SslUtils.toSSLHandshakeException(var3);
         }
      }
   }
}
