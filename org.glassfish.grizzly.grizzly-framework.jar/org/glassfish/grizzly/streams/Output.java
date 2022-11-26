package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;

public interface Output {
   void write(byte var1) throws IOException;

   void write(Buffer var1) throws IOException;

   boolean isBuffered();

   void ensureBufferCapacity(int var1) throws IOException;

   Buffer getBuffer();

   GrizzlyFuture flush(CompletionHandler var1) throws IOException;

   GrizzlyFuture close(CompletionHandler var1) throws IOException;
}
