package weblogic.nodemanager.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import weblogic.nodemanager.util.ConcurrentFile;

public class StateInfoWriter {
   protected static final String EOL = System.getProperty("line.separator");
   protected static final String SEPARATOR = ":";

   public static synchronized void writeStateInfo(ConcurrentFile file, String state, boolean started, boolean failed) throws IOException {
      String line = state + ":" + (started ? "Y" : "N") + ":" + (failed ? "Y" : "N") + EOL;
      file.write(ByteBuffer.wrap(line.getBytes()));
   }
}
