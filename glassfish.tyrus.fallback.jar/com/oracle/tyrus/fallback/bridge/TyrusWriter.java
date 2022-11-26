package com.oracle.tyrus.fallback.bridge;

import com.oracle.tyrus.fallback.Connection;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.Writer;

public class TyrusWriter extends Writer {
   private static final Logger LOGGER = Logger.getLogger(TyrusWriter.class.getName());
   private final Connection connection;

   public TyrusWriter(Connection connection) {
      this.connection = connection;
   }

   public void write(ByteBuffer buffer, CompletionHandler completionHandler) {
      int remaining = buffer.remaining();
      byte[] bytes = new byte[remaining];
      buffer.get(bytes);
      this.connection.write(bytes, 0, bytes.length, completionHandler);
      completionHandler.completed(buffer);
   }

   public void close() throws IOException {
      try {
         this.connection.close();
      } catch (IOException var2) {
         LOGGER.log(Level.WARNING, "Exception closing fallback connection", var2);
      }

   }
}
