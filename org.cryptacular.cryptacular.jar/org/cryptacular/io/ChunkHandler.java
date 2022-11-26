package org.cryptacular.io;

import java.io.IOException;
import java.io.OutputStream;

public interface ChunkHandler {
   void handle(byte[] var1, int var2, int var3, OutputStream var4) throws IOException;
}
