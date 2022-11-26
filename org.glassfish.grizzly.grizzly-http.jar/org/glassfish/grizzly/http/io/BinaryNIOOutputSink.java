package org.glassfish.grizzly.http.io;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.OutputSink;

public interface BinaryNIOOutputSink extends OutputSink {
   void write(Buffer var1) throws IOException;
}
