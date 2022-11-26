package weblogic.nodemanager.server;

import java.io.IOException;

public interface Server {
   void init(NMServer var1) throws IOException;

   void start(NMServer var1) throws IOException;

   String supportedMode();
}
