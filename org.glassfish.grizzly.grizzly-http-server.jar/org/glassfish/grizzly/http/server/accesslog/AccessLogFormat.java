package org.glassfish.grizzly.http.server.accesslog;

import java.util.Date;
import org.glassfish.grizzly.http.server.Response;

public interface AccessLogFormat {
   String format(Response var1, Date var2, long var3);
}
