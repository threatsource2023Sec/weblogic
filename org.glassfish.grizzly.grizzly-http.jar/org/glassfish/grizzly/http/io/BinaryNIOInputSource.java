package org.glassfish.grizzly.http.io;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.InputSource;

public interface BinaryNIOInputSource extends InputSource {
   Buffer getBuffer();

   Buffer readBuffer();

   Buffer readBuffer(int var1);
}
