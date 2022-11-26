package weblogic.rjvm.http;

import java.io.IOException;
import weblogic.protocol.ServerChannel;

public class HTTPSThinClientJVMConnection extends HTTPClientJVMConnection {
   public HTTPSThinClientJVMConnection(ServerChannel networkChannel, String partitionName) throws IOException {
      super(networkChannel, partitionName);
   }
}
