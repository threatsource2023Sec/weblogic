package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.Future;

public interface SocketConnectorHandler extends ConnectorHandler {
   int DEFAULT_CONNECTION_TIMEOUT = 30000;

   Future connect(String var1, int var2) throws IOException;
}
