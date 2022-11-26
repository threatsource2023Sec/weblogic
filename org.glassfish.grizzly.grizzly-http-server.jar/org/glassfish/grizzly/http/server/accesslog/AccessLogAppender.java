package org.glassfish.grizzly.http.server.accesslog;

import java.io.Closeable;
import java.io.IOException;

public interface AccessLogAppender extends Closeable {
   void append(String var1) throws IOException;

   void close() throws IOException;
}
