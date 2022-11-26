package weblogic.connector.security.layer;

import javax.resource.spi.work.WorkContext;
import javax.resource.spi.work.WorkContextLifecycleListener;

public interface WorkContextWrapper extends WorkContext, WorkContextLifecycleListener {
   WorkContext getOriginalWorkContext();

   boolean supportWorkContextLifecycleListener();

   WorkContextLifecycleListener getOriginalWorkContextLifecycleListener();

   Class getOriginalClass();
}
