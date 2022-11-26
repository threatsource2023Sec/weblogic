package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.Future;

public interface SocketAcceptor {
   Future accept() throws IOException;
}
