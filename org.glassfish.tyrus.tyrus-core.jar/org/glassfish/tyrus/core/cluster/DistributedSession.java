package org.glassfish.tyrus.core.cluster;

import java.util.Map;
import javax.websocket.Session;

public interface DistributedSession extends Session {
   Map getDistributedProperties();
}
