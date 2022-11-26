package weblogic.management.runtime;

import java.io.Serializable;

public interface Socket extends Serializable {
   String getProtocol();

   String getRemoteAddress();
}
