package weblogic.cluster.messaging.internal;

import java.io.Serializable;

public interface ServerInformation extends Serializable, Comparable {
   String getServerName();

   long getStartupTime();
}
