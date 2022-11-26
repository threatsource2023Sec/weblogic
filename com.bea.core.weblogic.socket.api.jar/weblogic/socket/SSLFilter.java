package weblogic.socket;

import java.io.IOException;
import weblogic.utils.io.ChunkedInputStream;

public interface SSLFilter extends MuxableSocket {
   MuxableSocket getDelegate();

   void setDelegate(MuxableSocket var1);

   void asyncOn();

   void asyncOff();

   boolean isActivated();

   ChunkedInputStream getInputStream() throws IOException;

   int available() throws IOException;

   void activate() throws IOException;

   void activateNoRegister() throws IOException;

   void ensureForceClose();
}
