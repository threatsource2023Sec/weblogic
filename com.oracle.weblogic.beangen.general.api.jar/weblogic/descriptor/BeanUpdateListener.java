package weblogic.descriptor;

import java.util.EventListener;

public interface BeanUpdateListener extends EventListener {
   void prepareUpdate(BeanUpdateEvent var1) throws BeanUpdateRejectedException;

   void activateUpdate(BeanUpdateEvent var1) throws BeanUpdateFailedException;

   void rollbackUpdate(BeanUpdateEvent var1);
}
