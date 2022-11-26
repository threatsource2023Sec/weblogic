package weblogic.socket;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.utils.io.Chunk;

public interface AsyncOutputStream {
   OutputStream getOutputStream();

   Chunk getOutputBuffer();

   void handleWrite(Chunk var1);

   void handleException(IOException var1);

   boolean supportsGatheringWrite();

   long write(NIOConnection var1) throws IOException;
}
