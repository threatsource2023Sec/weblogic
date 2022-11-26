package org.glassfish.grizzly.ssl;

import java.io.IOException;
import java.util.concurrent.Future;
import javax.net.ssl.SSLContext;
import org.glassfish.grizzly.Codec;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Transformer;

public class SSLCodec implements Codec {
   private final SSLEngineConfigurator serverSSLEngineConfig;
   private final SSLEngineConfigurator clientSSLEngineConfig;
   private final Transformer decoder;
   private final Transformer encoder;

   public SSLCodec(SSLContextConfigurator config) {
      this(config.createSSLContext(true));
   }

   public SSLCodec(SSLContext sslContext) {
      this.decoder = new SSLDecoderTransformer();
      this.encoder = new SSLEncoderTransformer();
      this.serverSSLEngineConfig = new SSLEngineConfigurator(sslContext, false, false, false);
      this.clientSSLEngineConfig = new SSLEngineConfigurator(sslContext, true, false, false);
   }

   public Transformer getDecoder() {
      return this.decoder;
   }

   public Transformer getEncoder() {
      return this.encoder;
   }

   public SSLEngineConfigurator getClientSSLEngineConfig() {
      return this.clientSSLEngineConfig;
   }

   public SSLEngineConfigurator getServerSSLEngineConfig() {
      return this.serverSSLEngineConfig;
   }

   public Future handshake(Connection connection) throws IOException {
      return this.handshake(connection, this.clientSSLEngineConfig);
   }

   public Future handshake(Connection connection, SSLEngineConfigurator configurator) throws IOException {
      return null;
   }
}
