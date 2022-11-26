package weblogic.protocol;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.utils.io.Chunk;

public interface OutgoingMessage {
   void writeTo(OutputStream var1) throws IOException;

   int getLength() throws IOException;

   Chunk getChunks();
}
