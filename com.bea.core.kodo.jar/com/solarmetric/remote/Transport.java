package com.solarmetric.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.openjpa.lib.util.Closeable;

public interface Transport extends Closeable {
   Server getServer() throws Exception;

   Channel getClientChannel() throws Exception;

   public interface Channel extends Closeable {
      OutputStream getOutput() throws Exception;

      InputStream getInput() throws Exception;

      void error(IOException var1);
   }

   public interface Server extends Closeable {
      Channel accept() throws Exception;

      boolean isClosed() throws Exception;
   }
}
