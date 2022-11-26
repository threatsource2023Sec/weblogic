package weblogic.cluster;

import java.io.IOException;

public interface MulticastSession {
   void send(GroupMessage var1) throws IOException;

   MulticastSessionId getSessionID();
}
