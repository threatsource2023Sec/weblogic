package org.glassfish.grizzly;

import java.io.IOException;

public interface CloseListener {
   void onClosed(Closeable var1, ICloseType var2) throws IOException;
}
